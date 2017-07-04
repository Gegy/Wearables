package net.gegy1000.wearables.server.wearable.component;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ComponentTypes {
    @GameRegistry.ObjectHolder("wearables:night_vision_goggles")
    public static final WearableComponentType NIGHT_VISION_GOGGLES = ComponentRegistry.BLANK;

    @GameRegistry.ObjectHolder("wearables:jetpack")
    public static final WearableComponentType JETPACK = ComponentRegistry.BLANK;

    @GameRegistry.ObjectHolder("wearables:wings")
    public static final WearableComponentType WINGS = ComponentRegistry.BLANK;

    @GameRegistry.ObjectHolder("wearables:flippers")
    public static final WearableComponentType FLIPPERS = ComponentRegistry.BLANK;
}
