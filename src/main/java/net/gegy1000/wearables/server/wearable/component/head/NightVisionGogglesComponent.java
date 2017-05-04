package net.gegy1000.wearables.server.wearable.component.head;

import net.gegy1000.wearables.client.render.ComponentProperty;
import net.gegy1000.wearables.server.wearable.WearableCategory;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class NightVisionGogglesComponent extends WearableComponentType {
    private static final ItemStack[] INGREDIENTS = new ItemStack[] { new ItemStack(Items.IRON_INGOT, 8), new ItemStack(Items.GLOWSTONE_DUST, 6), new ItemStack(Items.PRISMARINE_CRYSTALS, 2) };

    @Override
    public String getIdentifier() {
        return "night_vision_goggles";
    }

    @Override
    public WearableCategory getCategory() {
        return WearableCategory.HEAD_FACE;
    }

    @Override
    public ItemStack[] getIngredients() {
        return INGREDIENTS;
    }

    @Override
    public int getLayerCount() {
        return 2;
    }

    @Override
    public boolean canColour(int layer) {
        return layer == 0;
    }

    @Override
    public int getSupportedProperties() {
        return ComponentProperty.OFFSET_Y;
    }

    @Override
    public float getMinimum(int property) {
        if (property == ComponentProperty.OFFSET_Y) {
            return -0.2F;
        }
        return super.getMinimum(property);
    }

    @Override
    public float getMaximum(int property) {
        if (property == ComponentProperty.OFFSET_Y) {
            return 0.3F;
        }
        return super.getMaximum(property);
    }

    @Override
    public boolean hasTooltip() {
        return true;
    }
}
