package net.gegy1000.wearables.server.wearable.component.chest;

import net.gegy1000.wearables.client.render.ComponentProperty;
import net.gegy1000.wearables.server.wearable.WearableCategory;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TieComponent extends WearableComponentType {
    private static final ItemStack[] INGREDIENTS = new ItemStack[] { new ItemStack(Items.LEATHER, 2), new ItemStack(Items.STRING, 2) };

    @Override
    public String getIdentifier() {
        return "tie";
    }

    @Override
    public WearableCategory getCategory() {
        return WearableCategory.CHEST_NECK;
    }

    @Override
    public ItemStack[] getIngredients() {
        return INGREDIENTS;
    }

    @Override
    public int getSupportedProperties() {
        return ComponentProperty.OFFSET_Y;
    }

    @Override
    public float getMinimum(int property) {
        if (property == ComponentProperty.OFFSET_Y) {
            return -0.3F;
        }
        return super.getMinimum(property);
    }

    @Override
    public float getMaximum(int property) {
        if (property == ComponentProperty.OFFSET_Y) {
            return 0.1F;
        }
        return super.getMaximum(property);
    }
}
