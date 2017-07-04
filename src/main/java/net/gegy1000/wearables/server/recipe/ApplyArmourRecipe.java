package net.gegy1000.wearables.server.recipe;

import net.gegy1000.wearables.server.item.ItemRegistry;
import net.gegy1000.wearables.server.item.WearableItem;
import net.gegy1000.wearables.server.wearable.Wearable;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class ApplyArmourRecipe implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        boolean hasWearable = false;
        boolean hasArmour = false;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                ItemStack stack = inv.getStackInRowAndColumn(row, column);
                if (stack.getItem() instanceof WearableItem && WearableItem.getWearable(stack).getAppliedArmour().isEmpty()) {
                    if (hasWearable) {
                        return false;
                    }
                    hasWearable = true;
                } else if (stack.getItem() instanceof ItemArmor) {
                    if (hasArmour) {
                        return false;
                    }
                    hasArmour = true;
                } else if (!stack.isEmpty()) {
                    return false;
                }
            }
        }
        return hasWearable && hasArmour;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack wearableStack = ItemStack.EMPTY;
        Wearable wearable = new Wearable();
        ItemStack armour = ItemStack.EMPTY;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                ItemStack stack = inv.getStackInRowAndColumn(row, column);
                if (stack.getItem() instanceof WearableItem) {
                    wearable = WearableItem.getWearable(stack);
                    wearableStack = stack.copy();
                } else if (stack.getItem() instanceof ItemArmor) {
                    armour = stack;
                }
            }
        }
        wearable.setAppliedArmour(armour);
        wearableStack.setTagCompound(wearable.serializeNBT());
        return wearableStack;
    }

    @Override
    public int getRecipeSize() {
        return 2;
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
            remaining.set(i, ForgeHooks.getContainerItem(stack));
        }
        return remaining;
    }
}
