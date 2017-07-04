package net.gegy1000.wearables.server.wearable.component;

import mcp.MethodsReturnNonnullByDefault;
import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.movement.MovementHandler;
import net.gegy1000.wearables.server.wearable.WearableCategory;
import net.gegy1000.wearables.server.wearable.event.ComponentEventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;

@MethodsReturnNonnullByDefault
public class BlankComponent implements WearableComponentType {
    @Override
    public WearableCategory getCategory() {
        return WearableCategory.HEAD_GENERIC;
    }

    @Override
    public ItemStack[] getIngredients() {
        return new ItemStack[0];
    }

    @Override
    public Layer[] getLayers(boolean thin) {
        return new Layer[0];
    }

    @Nullable
    @Override
    public float[] getInventoryRotation() {
        return null;
    }

    @Override
    public Property[] getProperties() {
        return new Property[0];
    }

    @Override
    public AxisAlignedBB getBounds() {
        return new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    @Nullable
    @Override
    public ComponentEventHandler getEventHandler() {
        return null;
    }

    @Nullable
    @Override
    public MovementHandler getMovementHandler() {
        return null;
    }

    @Override
    public boolean hasTooltip() {
        return false;
    }

    @Override
    @Nullable
    public Property getProperty(ComponentProperty type) {
        return null;
    }

    @Override
    public WearableComponentType setRegistryName(ResourceLocation name) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return new ResourceLocation(Wearables.MODID, "blank");
    }

    @Override
    public Class<WearableComponentType> getRegistryType() {
        return WearableComponentType.class;
    }
}
