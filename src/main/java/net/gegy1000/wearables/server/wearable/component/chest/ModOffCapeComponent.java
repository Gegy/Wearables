package net.gegy1000.wearables.server.wearable.component.chest;

import net.gegy1000.wearables.server.wearable.WearableCategory;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModOffCapeComponent extends WearableComponentType {
    private static final ItemStack[] INGREDIENTS = new ItemStack[] { new ItemStack(Blocks.WOOL, 6), new ItemStack(Items.STRING, 2) };

    @Override
    public String getIdentifier() {
        return "modoff_cape";
    }

    @Override
    public WearableCategory getCategory() {
        return WearableCategory.CHEST_BACK;
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
}
