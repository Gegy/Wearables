package net.gegy1000.wearables.server.wearable.component;

import net.gegy1000.wearables.server.util.WearableColourUtils;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class WearableComponent implements INBTSerializable<NBTTagCompound> {
    private WearableComponentType type;
    private int[] colours;
    private float offsetY;
    private float offsetZ;

    public WearableComponent(WearableComponentType type) {
        this.type = type;
        this.colours = new int[type.getLayerCount()];
        int colour = WearableColourUtils.fromRGBFloatArray(EntitySheep.getDyeRgb(EnumDyeColor.WHITE));
        for (int layer = 0; layer < this.colours.length; layer++) {
            this.colours[layer] = colour;
        }
    }

    private WearableComponent() {
    }

    public void setColour(int layer, int colour) {
        this.colours[layer] = colour;
    }

    public void setProperty(int property, float value) {
        switch (property) {
            case ComponentProperty.OFFSET_Y:
                this.offsetY = value;
                break;
            case ComponentProperty.OFFSET_Z:
                this.offsetZ = value;
                break;
        }
    }

    public WearableComponentType getType() {
        return this.type;
    }

    public int[] getColours() {
        return this.colours;
    }

    public int getColour(int layer) {
        return this.colours[layer];
    }

    public float getProperty(int property) {
        switch (property) {
            case ComponentProperty.OFFSET_Y:
                return this.offsetY;
            case ComponentProperty.OFFSET_Z:
                return this.offsetZ;
        }
        return 0.0F;
    }

    public float getOffsetY() {
        return this.offsetY;
    }

    public float getOffsetZ() {
        return this.offsetZ;
    }

    public void clearProperties() {
        this.offsetY = 0.0F;
        this.offsetZ = 0.0F;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("identifier", this.type.getIdentifier());
        compound.setIntArray("colour_layers", this.colours);
        compound.setFloat("offset_y", this.offsetY);
        compound.setFloat("offset_z", this.offsetZ);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        if (compound.hasKey("identifier")) {
            this.type = ComponentRegistry.get(compound.getString("identifier"));
        }
        if (this.type == null) {
            this.type = ComponentRegistry.getDefault();
        }
        if (compound.hasKey("colour_layers")) {
            this.colours = compound.getIntArray("colour_layers");
            if (this.colours.length != this.type.getLayerCount()) {
                int[] loadedColours = this.colours;
                this.colours = new int[this.type.getLayerCount()];
                System.arraycopy(loadedColours, 0, this.colours, 0, Math.min(loadedColours.length, this.colours.length));
            }
        } else {
            this.colours = new int[this.type.getLayerCount()];
            int colour = WearableColourUtils.fromRGBFloatArray(EntitySheep.getDyeRgb(EnumDyeColor.WHITE));
            for (int layer = 0; layer < this.colours.length; layer++) {
                this.colours[layer] = colour;
            }
        }
        this.offsetY = compound.getFloat("offset_y");
        this.offsetZ = compound.getFloat("offset_z");
    }

    public static WearableComponent deserialize(NBTTagCompound compound) {
        WearableComponent component = new WearableComponent();
        component.deserializeNBT(compound);
        return component;
    }

    public WearableComponent copy() {
        return WearableComponent.deserialize(this.serializeNBT().copy());
    }
}
