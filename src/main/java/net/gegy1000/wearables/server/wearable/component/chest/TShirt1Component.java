package net.gegy1000.wearables.server.wearable.component.chest;

import net.gegy1000.wearables.server.wearable.WearableCategory;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TShirt1Component extends WearableComponentType {
    private static final ItemStack[] INGREDIENTS = new ItemStack[] { new ItemStack(Blocks.WOOL, 1), new ItemStack(Items.LEATHER, 4), new ItemStack(Items.STRING, 2) };

    @Override
    public String getIdentifier() {
        return "t_shirt_generic_1";
    }

    @Override
    public WearableCategory getCategory() {
        return WearableCategory.CHEST_GENERIC;
    }

    @Override
    public ItemStack[] getIngredients() {
        return INGREDIENTS;
    }
}
