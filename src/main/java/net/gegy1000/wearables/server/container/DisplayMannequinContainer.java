package net.gegy1000.wearables.server.container;

import net.gegy1000.wearables.server.block.entity.DisplayMannequinEntity;
import net.gegy1000.wearables.server.container.slot.ArmourSlot;
import net.gegy1000.wearables.server.container.slot.WearableSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class DisplayMannequinContainer extends AutoTransferContainer {
    private static final EntityEquipmentSlot[] EQUIPMENT_SLOTS = new EntityEquipmentSlot[] { EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET };

    private final DisplayMannequinEntity entity;

    public DisplayMannequinContainer(InventoryPlayer playerInventory, DisplayMannequinEntity entity) {
        this.entity = entity;

        IItemHandler inventory = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        for (int row = 0; row < EQUIPMENT_SLOTS.length; row++) {
            EntityEquipmentSlot slot = EQUIPMENT_SLOTS[row];
            this.addSlotToContainer(new WearableSlot(inventory, slot, row, 8, row * 18 + 8));
            this.addSlotToContainer(new ArmourSlot(playerInventory, slot, 36 + (3 - row), 152, 8 + row * 18));
        }

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
        return this.entity.isUsableByPlayer(player);
    }
}
