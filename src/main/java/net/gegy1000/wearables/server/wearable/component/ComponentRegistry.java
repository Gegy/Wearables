package net.gegy1000.wearables.server.wearable.component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.wearable.WearableCategory;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Wearables.MODID)
public class ComponentRegistry {
    public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(Wearables.MODID, "components");

    private static final Map<WearableCategory, Set<WearableComponentType>> CATEGORIES = new HashMap<>();

    public static final WearableComponentType BLANK = new BlankComponent();

    private static IForgeRegistry<WearableComponentType> registry;

    @SubscribeEvent
    public static void onNewRegistry(RegistryEvent.NewRegistry event) {
        registry = new RegistryBuilder<WearableComponentType>()
                .setType(WearableComponentType.class)
                .setName(REGISTRY_NAME)
                .setDefaultKey(BLANK.getRegistryName())
                .add((owner, stage, id, obj, oldObj) -> CATEGORIES.computeIfAbsent(obj.getCategory(), c -> new HashSet<>()).add(obj))
                .disableSaving()
                .create();
    }

    @SubscribeEvent
    public static void onRegister(RegistryEvent.Register<WearableComponentType> event) {
        Gson gson = new Gson();

        for (ModContainer mod : Loader.instance().getActiveModList()) {
            CraftingHelper.findFiles(mod, "assets/" + mod.getModId() + "/wearable_components", root -> true, (root, file) -> {
                String relative = root.relativize(file).toString();
                if (!"json".equals(FilenameUtils.getExtension(file.toString()))) {
                    return true;
                }

                String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
                ResourceLocation key = new ResourceLocation(mod.getModId(), name);

                try (BufferedReader reader = Files.newBufferedReader(file)) {
                    JsonObject json = JsonUtils.fromJson(gson, reader, JsonObject.class);
                    event.getRegistry().register(WearableComponentType.deserialize(json));
                    return true;
                } catch (JsonParseException e) {
                    Wearables.LOGGER.error("Parsing error loading wearable component {}", key, e);
                } catch (IOException e) {
                    Wearables.LOGGER.error("Couldn't read wearable component {} from {}", key, file, e);
                }

                return false;
            });
        }
    }

    public static Set<WearableComponentType> get(WearableCategory category) {
        return CATEGORIES.get(category);
    }

    public static IForgeRegistry<WearableComponentType> getRegistry() {
        return registry;
    }
}
