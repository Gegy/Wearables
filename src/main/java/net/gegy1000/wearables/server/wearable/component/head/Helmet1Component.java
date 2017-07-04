package net.gegy1000.wearables.server.wearable.component.head;

import net.gegy1000.wearables.server.wearable.WearableCategory;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Helmet1Component extends WearableComponentType {
    private static final ItemStack[] INGREDIENTS = new ItemStack[] { new ItemStack(Items.IRON_INGOT, 6), new ItemStack(Items.LEATHER, 2) };

    @Override
    public String getIdentifier() {
        return "helmet_generic_1";
    }

    @Override
    public WearableCategory getCategory() {
        return WearableCategory.HEAD_TOP;
    }

    @Override
    public ItemStack[] getIngredients() {
        return INGREDIENTS;
    }
}
