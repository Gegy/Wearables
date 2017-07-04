package net.gegy1000.wearables.server.wearable.component.chest;

import net.gegy1000.wearables.server.wearable.component.ComponentProperty;
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
        return ComponentProperty.OFFSET_Y | ComponentProperty.OFFSET_Z;
    }

    @Override
    public float getMinimum(int property) {
        if (property == ComponentProperty.OFFSET_Y) {
            return -0.3F;
        } else if (property == ComponentProperty.OFFSET_Z) {
            return -0.1F;
        }
        return super.getMinimum(property);
    }

    @Override
    public float getMaximum(int property) {
        if (property == ComponentProperty.OFFSET_Y) {
            return 0.1F;
        } else if (property == ComponentProperty.OFFSET_Z) {
            return 0.1F;
        }
        return super.getMaximum(property);
    }
}
