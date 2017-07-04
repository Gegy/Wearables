package net.gegy1000.wearables.server.wearable.component;

import net.gegy1000.wearables.Wearables;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public enum ComponentProperty {
    OFFSET_Y(new ResourceLocation(Wearables.MODID, "offset_y")),
    OFFSET_Z(new ResourceLocation(Wearables.MODID, "offset_z"));

    private static final Map<ResourceLocation, ComponentProperty> PROPERTIES = new HashMap<>();

    static {
        for (ComponentProperty property : ComponentProperty.values()) {
            PROPERTIES.put(property.getIdentifier(), property);
        }
    }

    private final ResourceLocation identifier;

    ComponentProperty(ResourceLocation identifier) {
        this.identifier = identifier;
    }

    public ResourceLocation getIdentifier() {
        return this.identifier;
    }

    public static ComponentProperty get(ResourceLocation identifier) {
        return PROPERTIES.get(identifier);
    }
}
