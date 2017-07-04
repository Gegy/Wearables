package net.gegy1000.wearables.server.container;

import net.gegy1000.wearables.server.util.WearableUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class AutoTransferContainer extends Container {
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack transferred = WearableUtils.emptyStack();
        Slot slot = this.inventorySlots.get(slotIndex);
        int otherSlots = this.inventorySlots.size() - 36;
        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();
            ItemStack copy = current.copy();
            transferred = current.copy();
            if (slotIndex < otherSlots) {
                if (!this.mergeItemStack(copy, otherSlots, this.inventorySlots.size(), false)) {
                    return WearableUtils.emptyStack();
                } else {
                    slot.onSlotChanged();
                }
            } else if (!this.mergeItemStack(copy, 0, otherSlots, false)) {
                return WearableUtils.emptyStack();
            }
            if (copy.getCount() == 0) {
                slot.onTake(player, copy);
                slot.putStack(WearableUtils.emptyStack());
            } else {
                slot.onSlotChanged();
            }
        }
        return transferred;
    }
}
