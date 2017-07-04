package net.gegy1000.wearables.server.wearable.component.feet;

import net.gegy1000.wearables.server.wearable.WearableCategory;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Shoes1Component extends WearableComponentType {
    private static final ItemStack[] INGREDIENTS = new ItemStack[] { new ItemStack(Items.LEATHER, 4), new ItemStack(Items.STRING, 2) };

    @Override
    public String getIdentifier() {
        return "shoes_generic_1";
    }

    @Override
    public WearableCategory getCategory() {
        return WearableCategory.FEET_GENERIC;
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
