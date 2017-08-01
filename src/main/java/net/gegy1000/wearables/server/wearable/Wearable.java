package net.gegy1000.wearables.server.wearable;

import net.gegy1000.wearables.server.util.WearableTagCompound;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Wearable implements INBTSerializable<NBTTagCompound> {
    private List<WearableComponent> components = new ArrayList<>();
    private ItemStack appliedArmour = ItemStack.EMPTY;

    private Data data;

    public void addComponent(WearableComponent component) {
        this.components.add(component);
    }

    public List<WearableComponent> getComponents() {
        return this.components;
    }

    public void setAppliedArmour(ItemStack appliedArmour) {
        this.appliedArmour = appliedArmour;
    }

    public ItemStack getAppliedArmour() {
        return this.appliedArmour;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new WearableTagCompound();
        NBTTagList componentList = new NBTTagList();
        for (WearableComponent component : this.components) {
            componentList.appendTag(component.serializeNBT());
        }
        compound.setTag("components", componentList);
        if (!this.appliedArmour.isEmpty()) {
            compound.setTag("applied_armour", this.appliedArmour.serializeNBT());
        }
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        if (compound.hasKey("components")) {
            NBTTagList componentList = compound.getTagList("components", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < componentList.tagCount(); i++) {
                this.components.add(WearableComponent.deserialize(componentList.getCompoundTagAt(i)));
            }
        }
        if (compound.hasKey("applied_armour")) {
            this.appliedArmour = new ItemStack(compound.getCompoundTag("applied_armour"));
        }
    }

    public static Wearable deserialize(NBTTagCompound compound) {
        Wearable wearable = new Wearable();
        wearable.deserializeNBT(compound);
        return wearable;
    }

    public Wearable copy() {
        return Wearable.deserialize(this.serializeNBT().copy());
    }

    public boolean has(WearableCategory category) {
        for (WearableComponent component : this.components) {
            if (component.getType().getCategory() == category) {
                return true;
            }
        }
        return false;
    }

    public Data toData() {
        if (this.data == null) {
            this.data = new Data(this.components);
        }
        return this.data;
    }

    public static final class Data {
        public final ResourceLocation[] types;
        private final int hashCode;

        private Data(List<WearableComponent> components) {
            this.types = new ResourceLocation[components.size()];
            for (int i = 0; i < components.size(); i++) {
                WearableComponent component = components.get(i);
                this.types[i] = component.getType().getRegistryName();
            }
            this.hashCode = Arrays.hashCode(this.types);
        }

        @Override
        public int hashCode() {
            return this.hashCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Data) {
                Data data = (Data) obj;
                return Arrays.equals(data.types, this.types);
            }
            return false;
        }
    }
}
