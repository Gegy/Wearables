package net.gegy1000.wearables.server.item;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.api.item.RegisterBlockEntity;
import net.gegy1000.wearables.server.block.entity.wearable.WearableChestItemEntity;
import net.gegy1000.wearables.server.block.entity.wearable.WearableFeetItemEntity;
import net.gegy1000.wearables.server.block.entity.wearable.WearableHeadItemEntity;
import net.gegy1000.wearables.server.block.entity.wearable.WearableLegsItemEntity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Wearables.MODID)
@GameRegistry.ObjectHolder(Wearables.MODID)
public class ItemRegistry {
    private static final Set<Item> ITEMS = new LinkedHashSet<>();
    private static final Set<Item> IMMUTABLE_ITEMS = Collections.unmodifiableSet(ITEMS);

    private static final Item EMPTY = new Item();

    public static final Item DISPLAY_MANNEQUIN = EMPTY;
    public static final Item JETPACK_FUEL = EMPTY;

    public static final Item WEARABLE_HEAD = EMPTY;
    public static final Item WEARABLE_CHEST = EMPTY;
    public static final Item WEARABLE_LEGS = EMPTY;
    public static final Item WEARABLE_FEET = EMPTY;
    public static final Item WEARABLE_COMPONENT = EMPTY;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        ItemRegistry.register(registry, new DisplayMannequinItem(), new ResourceLocation(Wearables.MODID, "display_mannequin_item"));
        ItemRegistry.register(registry, new JetpackFuelItem(), new ResourceLocation(Wearables.MODID, "jetpack_fuel"));

        ItemRegistry.register(registry, new WearableItem(WearableHeadItemEntity.class, EntityEquipmentSlot.HEAD), new ResourceLocation(Wearables.MODID, "wearable_head"));
        ItemRegistry.register(registry, new WearableItem(WearableChestItemEntity.class, EntityEquipmentSlot.CHEST), new ResourceLocation(Wearables.MODID, "wearable_chest"));
        ItemRegistry.register(registry, new WearableItem(WearableLegsItemEntity.class, EntityEquipmentSlot.LEGS), new ResourceLocation(Wearables.MODID, "wearable_legs"));
        ItemRegistry.register(registry, new WearableItem(WearableFeetItemEntity.class, EntityEquipmentSlot.FEET), new ResourceLocation(Wearables.MODID, "wearable_feet"));

        ItemRegistry.register(registry, new WearableComponentItem(), new ResourceLocation(Wearables.MODID, "wearable_component"));
    }

    private static void register(IForgeRegistry<Item> registry, Item item, ResourceLocation identifier) {
        ITEMS.add(item);

        item.setRegistryName(identifier);
        item.setUnlocalizedName(identifier.getResourcePath());

        registry.register(item);

        if (item instanceof RegisterBlockEntity) {
            GameRegistry.registerTileEntity(((RegisterBlockEntity) item).getEntity(), Wearables.MODID + ":" + identifier.getResourcePath());
        }
    }

    public static Set<? extends Item> getItems() {
        return IMMUTABLE_ITEMS;
    }
}
