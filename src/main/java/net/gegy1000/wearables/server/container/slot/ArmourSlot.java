package net.gegy1000.wearables.server.container.slot;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArmourSlot extends Slot {
    private final EntityPlayer player;
    private final EntityEquipmentSlot slot;

    public ArmourSlot(InventoryPlayer inventory, EntityEquipmentSlot slot, int index, int x, int y) {
        super(inventory, index, x, y);
        this.player = inventory.player;
        this.slot = slot;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem().isValidArmor(stack, this.slot, this.player);
    }

    @Override
    public boolean canTakeStack(EntityPlayer player) {
        ItemStack stack = this.getStack();
        return !(!stack.isEmpty() && !player.isCreative() && EnchantmentHelper.hasBindingCurse(stack)) && super.canTakeStack(player);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getSlotTexture() {
        return ItemArmor.EMPTY_SLOT_NAMES[this.slot.getIndex()];
    }
}
