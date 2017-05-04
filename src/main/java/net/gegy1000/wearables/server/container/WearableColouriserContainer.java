package net.gegy1000.wearables.server.container;

import net.gegy1000.wearables.server.block.entity.machine.WearableColouriserEntity;
import net.gegy1000.wearables.server.container.slot.ColouredComponentSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class WearableColouriserContainer extends AutoTransferContainer {
    private final WearableColouriserEntity entity;

    public WearableColouriserContainer(InventoryPlayer playerInventory, WearableColouriserEntity entity) {
        this.entity = entity;

        IItemHandler inventory = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        this.addSlotToContainer(new ColouredComponentSlot(inventory, 0, 69, 35));

        for (int column = 0; column < 9; column++) {
            this.addSlotToContainer(new Slot(playerInventory, column, 8 + column * 18, 142));
        }

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlotToContainer(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.entity.canInteractWith(player);
    }
}
