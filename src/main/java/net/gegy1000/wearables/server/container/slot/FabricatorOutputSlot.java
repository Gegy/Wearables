package net.gegy1000.wearables.server.container.slot;

import net.gegy1000.wearables.server.block.entity.machine.WearableFabricatorEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class FabricatorOutputSlot extends SlotItemHandler {
    private final WearableFabricatorEntity entity;

    public FabricatorOutputSlot(WearableFabricatorEntity entity, IItemHandler inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.entity = entity;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canTakeStack(EntityPlayer player) {
        return this.entity.hasIngredients() && super.canTakeStack(player);
    }

    @Override
    public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
        this.entity.consumeIngredients();
        super.onPickupFromSlot(player, stack);
    }
}
