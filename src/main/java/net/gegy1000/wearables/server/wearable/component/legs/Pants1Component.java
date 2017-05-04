package net.gegy1000.wearables.server.wearable.component.legs;

import net.gegy1000.wearables.server.wearable.WearableCategory;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Pants1Component extends WearableComponentType {
    private static final ItemStack[] INGREDIENTS = new ItemStack[] { new ItemStack(Items.LEATHER, 6), new ItemStack(Items.STRING, 2) };

    @Override
    public String getIdentifier() {
        return "pants_generic_1";
    }

    @Override
    public WearableCategory getCategory() {
        return WearableCategory.LEGS_GENERIC;
    }

    @Override
    public ItemStack[] getIngredients() {
        return INGREDIENTS;
    }

    @Override
    public int getLayerCount() {
        return 2;
    }
}
