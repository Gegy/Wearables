package net.gegy1000.wearables.server.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class AutoTransferContainer extends Container {
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack transferred = null;
        Slot slot = this.inventorySlots.get(slotIndex);
        int otherSlots = this.inventorySlots.size() - 36;
        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();
            ItemStack copy = current.copy();
            transferred = current.copy();
            if (slotIndex < otherSlots) {
                if (!this.mergeItemStack(copy, otherSlots, this.inventorySlots.size(), false)) {
                    return null;
                } else {
                    slot.onSlotChanged();
                }
            } else if (!this.mergeItemStack(copy, 0, otherSlots, false)) {
                return null;
            }
            if (copy.stackSize == 0) {
                slot.onPickupFromSlot(player, copy);
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }
        return transferred;
    }
}
