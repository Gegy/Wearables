package net.gegy1000.wearables.server.container.slot;

import net.gegy1000.wearables.server.item.WearableComponentItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ColouredComponentSlot extends SlotItemHandler {
    private boolean enabled = false;

    public ColouredComponentSlot(IItemHandler inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof WearableComponentItem;
    }

    @Override
    public boolean canBeHovered() {
        return this.enabled || !this.getHasStack();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
