package net.gegy1000.wearables.server.recipe;

import net.gegy1000.wearables.server.item.ItemRegistry;
import net.gegy1000.wearables.server.item.WearableItem;
import net.gegy1000.wearables.server.wearable.Wearable;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class ExtractArmourRecipe implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        boolean hasWearable = false;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                ItemStack stack = inv.getStackInRowAndColumn(row, column);
                if (!stack.isEmpty() && stack.getItem() instanceof WearableItem && !WearableItem.getWearable(stack).getAppliedArmour().isEmpty()) {
                    if (hasWearable) {
                        return false;
                    }
                    hasWearable = true;
                } else if (!stack.isEmpty()) {
                    return false;
                }
            }
        }
        return hasWearable;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                ItemStack stack = inv.getStackInRowAndColumn(row, column);
                if (!stack.isEmpty() && stack.getItem() instanceof WearableItem) {
                    Wearable wearable = WearableItem.getWearable(stack);
                    if (!wearable.getAppliedArmour().isEmpty()) {
                        return wearable.getAppliedArmour();
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getRecipeSize() {
        return 1;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(ItemRegistry.WEARABLE_CHEST);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < remaining.size(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack.getItem() instanceof WearableItem) {
                Wearable wearable = WearableItem.getWearable(stack);
                wearable.setAppliedArmour(ItemStack.EMPTY);
                stack.setTagCompound(wearable.serializeNBT());
                remaining.set(i, stack.copy());
            } else {
                remaining.set(i, ForgeHooks.getContainerItem(stack));
            }
        }
        return remaining;
    }
}
