package net.gegy1000.wearables.server.wearable.component;

import mcp.MethodsReturnNonnullByDefault;
import net.gegy1000.wearables.server.movement.MovementHandler;
import net.gegy1000.wearables.server.wearable.WearableCategory;
import net.gegy1000.wearables.server.wearable.event.ComponentEventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;

@MethodsReturnNonnullByDefault
public class WearableJsonComponent implements WearableComponentType {
    private final ResourceLocation identifier;
    private final WearableCategory category;
    private final ItemStack[] ingredients;
    private final Layer[] layers;
    private final Layer[] thinLayers;
    private final float[] inventoryRotation;
    private final Property[] properties;
    private final AxisAlignedBB bounds;
    private final boolean hasTooltip;
    private final ComponentEventHandler eventHandler;
    private final MovementHandler movementHandler;

    WearableJsonComponent(ResourceLocation identifier, WearableCategory category, ItemStack[] ingredients, Layer[] layers, Layer[] thinLayers, float[] inventoryRotation, Property[] properties, AxisAlignedBB bounds, boolean hasTooltip, ComponentEventHandler eventHandler, MovementHandler movementHandler) {
        this.identifier = identifier;
        this.category = category;
        this.ingredients = ingredients;
        this.layers = layers;
        this.thinLayers = thinLayers;
        this.inventoryRotation = inventoryRotation;
        this.properties = properties;
        this.bounds = bounds;
        this.hasTooltip = hasTooltip;
        this.eventHandler = eventHandler;
        this.movementHandler = movementHandler;
    }

    @Override
    public WearableJsonComponent setRegistryName(ResourceLocation name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.identifier;
    }

    @Override
    public Class<WearableComponentType> getRegistryType() {
        return WearableComponentType.class;
    }

    @Override
    public WearableCategory getCategory() {
        return this.category;
    }

    @Override
    public ItemStack[] getIngredients() {
        return this.ingredients;
    }

    @Override
    public Layer[] getLayers(boolean thin) {
        return thin ? this.thinLayers : this.layers;
    }

    @Nullable
    @Override
    public float[] getInventoryRotation() {
        return this.inventoryRotation;
    }

    @Override
    public Property[] getProperties() {
        return this.properties;
    }

    @Override
    public AxisAlignedBB getBounds() {
        return this.bounds;
    }

    @Override
    @Nullable
    public ComponentEventHandler getEventHandler() {
        return this.eventHandler;
    }

    @Override
    @Nullable
    public MovementHandler getMovementHandler() {
        return this.movementHandler;
    }

    @Override
    public boolean hasTooltip() {
        return this.hasTooltip;
    }

    @Override
    @Nullable
    public Property getProperty(ComponentProperty type) {
        for (Property property : this.properties) {
            if (property.getIdentifier().equals(type.getIdentifier())) {
                return property;
            }
        }
        return null;
    }
}
