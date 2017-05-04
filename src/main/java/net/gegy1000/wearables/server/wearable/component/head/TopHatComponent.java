package net.gegy1000.wearables.server.wearable.component.head;

import net.gegy1000.wearables.server.wearable.WearableCategory;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TopHatComponent extends WearableComponentType {
    private static final ItemStack[] INGREDIENTS = new ItemStack[] { new ItemStack(Items.LEATHER, 4), new ItemStack(Items.STRING, 2) };

    @Override
    public String getIdentifier() {
        return "top_hat";
    }

    @Override
    public WearableCategory getCategory() {
        return WearableCategory.HEAD_TOP;
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
