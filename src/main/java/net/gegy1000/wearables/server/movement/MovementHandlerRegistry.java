package net.gegy1000.wearables.server.movement;

import net.gegy1000.wearables.Wearables;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = Wearables.MODID)
public class MovementHandlerRegistry {
    public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(Wearables.MODID, "component_events");

    private static IForgeRegistry<MovementHandler> registry;

    @SubscribeEvent
    public static void onNewRegistry(RegistryEvent.NewRegistry event) {
        registry = new RegistryBuilder<MovementHandler>()
                .setType(MovementHandler.class)
                .setName(REGISTRY_NAME)
                .disableSaving()
                .create();
    }

    @SubscribeEvent
    public static void onRegister(RegistryEvent.Register<MovementHandler> event) {
        IForgeRegistry<MovementHandler> registry = event.getRegistry();

        registry.register(new WingsMovementHandler().setRegistryName(new ResourceLocation(Wearables.MODID, "wing_movement")));
        registry.register(new JetpackMovementHandler().setRegistryName(new ResourceLocation(Wearables.MODID, "jetpack_movement")));
        registry.register(new SwimMovementHandler().setRegistryName(new ResourceLocation(Wearables.MODID, "swim_movement")));
    }

    public static IForgeRegistry<MovementHandler> getRegistry() {
        return registry;
    }
}
