package net.gegy1000.wearables.server.container;

import net.gegy1000.wearables.server.block.entity.machine.WearableFabricatorEntity;
import net.gegy1000.wearables.server.container.slot.FabricatorOutputSlot;
import net.gegy1000.wearables.server.wearable.component.ComponentRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class WearableFabricatorContainer extends SyncedContainer {
    private final WearableFabricatorEntity entity;

    public WearableFabricatorContainer(InventoryPlayer playerInventory, WearableFabricatorEntity entity) {
        super(1);
        this.entity = entity;

        IItemHandler inventory = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        for (int column = 0; column < 4; column++) {
            this.addSlotToContainer(new SlotItemHandler(inventory, column, 62 + column * 18, 62));
        }

        this.addSlotToContainer(new FabricatorOutputSlot(entity, inventory, 4, 145, 23));

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

    @Override
    public void setField(int id, int value) {
        if (id == 0) {
            if (value < 0 || value >= ComponentRegistry.COMPONENTS.size()) {
                this.entity.setSelectedComponent(null);
            } else {
                this.entity.setSelectedComponent(ComponentRegistry.COMPONENTS.get(value));
            }
        }
    }

    @Override
    public int getField(int id) {
        if (id == 0) {
            return ComponentRegistry.COMPONENTS.indexOf(this.entity.getSelectedComponent());
        }
        return 0;
    }
}
