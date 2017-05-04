package net.gegy1000.wearables.server.container.slot;

import net.gegy1000.wearables.server.item.WearableItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class WearableSlot extends SlotItemHandler {
    private EntityEquipmentSlot slot;

    public WearableSlot(IItemHandler inventory, EntityEquipmentSlot slot, int index, int x, int y) {
        super(inventory, index, x, y);
        this.slot = slot;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof WearableItem && ((WearableItem) stack.getItem()).armorType == this.slot;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getSlotTexture() {
        return ItemArmor.EMPTY_SLOT_NAMES[this.slot.getIndex()];
    }
}
