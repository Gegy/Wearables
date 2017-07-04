package net.gegy1000.wearables.server.block.entity.machine;

import net.gegy1000.wearables.server.item.ItemRegistry;
import net.gegy1000.wearables.server.wearable.component.ComponentRegistry;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WearableFabricatorEntity extends InventoryBlockEntity implements ITickable {
    private WearableComponentType selectedComponent;

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation("tile.wearable_fabricator.name");
    }

    @Override
    public int getSlotCount() {
        return 5;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        if (this.selectedComponent != null) {
            compound.setString("selected_component", this.selectedComponent.getIdentifier());
        }
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("selected_component")) {
            this.selectedComponent = ComponentRegistry.get(compound.getString("selected_component"));
        } else {
            this.selectedComponent = null;
        }
    }

    public void setSelectedComponent(WearableComponentType selectedComponent) {
        this.selectedComponent = selectedComponent;
    }

    public WearableComponentType getSelectedComponent() {
        return this.selectedComponent;
    }

    @Override
    public void update() {
        if (this.hasIngredients()) {
            ItemStack stack = new ItemStack(ItemRegistry.WEARABLE_COMPONENT);
            stack.setTagCompound(new WearableComponent(this.selectedComponent).serializeNBT());
            this.inventory.setStackInSlot(4, stack);
        } else {
            this.inventory.setStackInSlot(4, ItemStack.EMPTY);
        }
    }

    public boolean hasIngredients() {
        if (this.selectedComponent != null) {
            List<ItemStack> required = new ArrayList<>();
            Collections.addAll(required, this.selectedComponent.getIngredients());
            for (int i = 0; i < 4; i++) {
                ItemStack inventoryStack = this.inventory.getStackInSlot(i);
                if (!inventoryStack.isEmpty()) {
                    for (ItemStack stack : this.selectedComponent.getIngredients()) {
                        if (stack.getItem() == inventoryStack.getItem() && stack.getItemDamage() == inventoryStack.getItemDamage() && inventoryStack.getCount() >= stack.getCount()) {
                            required.remove(stack);
                            break;
                        }
                    }
                }
            }
            if (required.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public void consumeIngredients() {
        if (this.selectedComponent != null) {
            for (int i = 0; i < 4; i++) {
                ItemStack inventoryStack = this.inventory.getStackInSlot(i);
                if (!inventoryStack.isEmpty()) {
                    for (ItemStack stack : this.selectedComponent.getIngredients()) {
                        if (stack.getItem() == inventoryStack.getItem() && stack.getItemDamage() == inventoryStack.getItemDamage() && inventoryStack.getCount() >= stack.getCount()) {
                            this.inventory.extractItem(i, stack.getCount(), false);
                            break;
                        }
                    }
                }
            }
        }
    }
}
