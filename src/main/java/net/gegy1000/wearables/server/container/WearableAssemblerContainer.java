package net.gegy1000.wearables.server.container;

import net.gegy1000.wearables.server.block.entity.machine.WearableAssemblerEntity;
import net.gegy1000.wearables.server.container.slot.ArmourApplySlot;
import net.gegy1000.wearables.server.container.slot.AssemblerInputSlot;
import net.gegy1000.wearables.server.container.slot.AssemblerOutputSlot;
import net.gegy1000.wearables.server.item.ItemRegistry;
import net.gegy1000.wearables.server.item.WearableComponentItem;
import net.gegy1000.wearables.server.item.WearableItem;
import net.gegy1000.wearables.server.wearable.Wearable;
import net.gegy1000.wearables.server.wearable.WearableCategory;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WearableAssemblerContainer extends AutoTransferContainer {
    private final WearableAssemblerEntity entity;
    private final ItemStackHandler components = new ItemStackHandler(6) {
        @Override
        protected void onContentsChanged(int slot) {
            WearableAssemblerContainer.this.onContentsChanged();
        }
    };
    private final ItemStackHandler armour = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            WearableAssemblerContainer.this.onContentsChanged();
        }
    };
    private final ItemStackHandler result = new ItemStackHandler(1);

    public WearableAssemblerContainer(InventoryPlayer playerInventory, WearableAssemblerEntity entity) {
        this.entity = entity;

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 2; column++) {
                this.addSlotToContainer(new AssemblerInputSlot(this, this.components, column + row * 2, 8 + column * 18, 18 + row * 18));
            }
        }

        this.addSlotToContainer(new ArmourApplySlot(this.armour, 0, 102, 54));
        this.addSlotToContainer(new AssemblerOutputSlot(this, this.result, 0, 144, 30));

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
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        if (!player.world.isRemote) {
            this.dropInventory(player, this.components);
            this.dropInventory(player, this.armour);
        }
    }

    private void dropInventory(EntityPlayer player, ItemStackHandler inventory) {
        for (int slot = 0; slot < inventory.getSlots(); slot++) {
            ItemStack stack = inventory.getStackInSlot(slot);
            if (stack != null) {
                if (stack.getItem() instanceof WearableComponentItem) {
                    WearableComponent component = WearableComponentItem.getComponent(stack);
                    component.clearProperties();
                    stack.setTagCompound(component.serializeNBT());
                }
                inventory.extractItem(slot, stack.stackSize, false);
                player.dropItem(stack, false);
            }
        }
    }

    public void onContentsChanged() {
        this.result.setStackInSlot(0, this.buildResult());
    }

    protected ItemStack buildResult() {
        EntityEquipmentSlot slotType = this.getSlotType();
        if (slotType != null) {
            Set<WearableCategory> usedCategories = new HashSet<>();
            Wearable wearable = new Wearable();
            for (int i = 0; i < this.components.getSlots(); i++) {
                ItemStack stack = this.components.getStackInSlot(i);
                if (stack != null && stack.getItem() instanceof WearableComponentItem) {
                    WearableComponent component = WearableComponentItem.getComponent(stack);
                    WearableCategory category = component.getType().getCategory();
                    if (category.getSlot() == slotType && !usedCategories.contains(category)) {
                        usedCategories.add(category);
                        wearable.addComponent(component);
                    }
                }
            }
            if (wearable.getComponents().size() > 0) {
                ItemStack armour = this.armour.getStackInSlot(0);
                if (armour != null && armour.getItem() instanceof ItemArmor && ((ItemArmor) armour.getItem()).getEquipmentSlot() == slotType) {
                    wearable.setAppliedArmour(armour);
                }
                ItemStack stack = new ItemStack(WearableItem.getItem(slotType));
                stack.setTagCompound(wearable.serializeNBT());
                return stack;
            }
        }
        return null;
    }

    public void consumeComponents() {
        EntityEquipmentSlot slotType = this.getSlotType();
        Set<WearableCategory> usedCategories = new HashSet<>();
        for (int i = 0; i < this.components.getSlots(); i++) {
            ItemStack stack = this.components.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof WearableComponentItem && stack.stackSize > 0) {
                WearableComponent component = WearableComponentItem.getComponent(stack);
                WearableCategory category = component.getType().getCategory();
                if (category.getSlot() == slotType && !usedCategories.contains(category)) {
                    stack.stackSize--;
                    if (stack.stackSize <= 0) {
                        this.components.setStackInSlot(i, null);
                    }
                    usedCategories.add(category);
                }
            }
        }
        ItemStack armour = this.armour.getStackInSlot(0);
        if (armour != null && armour.getItem() instanceof ItemArmor && ((ItemArmor) armour.getItem()).getEquipmentSlot() == slotType) {
            this.armour.extractItem(0, 1, false);
        }
        this.onContentsChanged();
    }

    public boolean canBuild() {
        return this.getSlotType() != null;
    }

    public boolean canAddComponent(WearableComponent checkComponent) {
        WearableCategory checkCategory = checkComponent.getType().getCategory();
        for (int i = 0; i < this.components.getSlots(); i++) {
            ItemStack stack = this.components.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof WearableComponentItem && stack.stackSize > 0) {
                WearableComponent component = WearableComponentItem.getComponent(stack);
                WearableCategory category = component.getType().getCategory();
                if (checkCategory == category || checkCategory.getSlot() != category.getSlot()) {
                    return false;
                }
            }
        }
        return true;
    }

    private EntityEquipmentSlot getSlotType() {
        EntityEquipmentSlot slotType = null;
        for (int i = 0; i < this.components.getSlots(); i++) {
            ItemStack stack = this.components.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof WearableComponentItem && stack.stackSize > 0) {
                WearableComponent component = WearableComponentItem.getComponent(stack);
                EntityEquipmentSlot slot = component.getType().getCategory().getSlot();
                if (slotType == null) {
                    slotType = slot;
                } else if (slotType != slot) {
                    return null;
                }
            }
        }
        return slotType;
    }

    public ItemStack getResult() {
        return this.result.getStackInSlot(0);
    }

    public boolean hasInput() {
        for (int i = 0; i < this.components.getSlots(); i++) {
            if (this.components.getStackInSlot(i) != null) {
                return true;
            }
        }
        for (int i = 0; i < this.armour.getSlots(); i++) {
            if (this.armour.getStackInSlot(i) != null) {
                return true;
            }
        }
        return false;
    }

    public void disassemble(Wearable wearable) {
        List<WearableComponent> components = wearable.getComponents();
        for (int i = 0; i < components.size(); i++) {
            ItemStack stack = new ItemStack(ItemRegistry.WEARABLE_COMPONENT);
            stack.setTagCompound(components.get(i).serializeNBT());
            this.components.setStackInSlot(i, stack);
        }
        this.armour.setStackInSlot(0, wearable.getAppliedArmour());
    }

    @Override
    public void putStackInSlot(int slotID, ItemStack stack) {
        Slot slot = this.getSlot(slotID);
        if (!(slot instanceof AssemblerOutputSlot) || !this.entity.getWorld().isRemote) {
            slot.putStack(stack);
        }
    }
}
