package net.gegy1000.wearables.server.wearable.event;

import net.gegy1000.wearables.Wearables;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = Wearables.MODID)
public class ComponentEventRegistry {
    public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(Wearables.MODID, "component_movement");

    private static IForgeRegistry<ComponentEventHandler> registry;

    @SubscribeEvent
    public static void onNewRegistry(RegistryEvent.NewRegistry event) {
        registry = new RegistryBuilder<ComponentEventHandler>()
                .setType(ComponentEventHandler.class)
                .setName(REGISTRY_NAME)
                .disableSaving()
                .create();
    }

    @SubscribeEvent
    public static void onRegister(RegistryEvent.Register<ComponentEventHandler> event) {
        IForgeRegistry<ComponentEventHandler> registry = event.getRegistry();

        registry.register(new WingEventHandler().setRegistryName(new ResourceLocation(Wearables.MODID, "wing_events")));
        registry.register(new JetpackEventHandler().setRegistryName(new ResourceLocation(Wearables.MODID, "jetpack_events")));
        registry.register(new FlipperEventHandler().setRegistryName(new ResourceLocation(Wearables.MODID, "flipper_events")));
    }

    public static IForgeRegistry<ComponentEventHandler> getRegistry() {
        return registry;
    }
}
