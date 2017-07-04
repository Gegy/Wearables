package net.gegy1000.wearables.server.wearable.component.head;

import net.gegy1000.wearables.server.wearable.WearableCategory;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class DragonHornsComponent extends WearableComponentType {
    private static final ItemStack[] INGREDIENTS = new ItemStack[] { new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.BONE, 10) };

    @Override
    public String getIdentifier() {
        return "dragon_horns";
    }

    @Override
    public WearableCategory getCategory() {
        return WearableCategory.HEAD_HORNS;
    }

    @Override
    public ItemStack[] getIngredients() {
        return INGREDIENTS;
    }
}
