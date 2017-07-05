package net.gegy1000.wearables.server.wearable.component;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.util.WearableColourUtils;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Arrays;

public class WearableComponent implements INBTSerializable<NBTTagCompound> {
    private WearableComponentType type;
    private int[] colours;
    private float offsetY;
    private float offsetZ;

    public WearableComponent(WearableComponentType type) {
        this.type = type;
        this.colours = new int[type.getLayers(false).length];
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

    public void setProperty(ComponentProperty property, float value) {
        switch (property) {
            case OFFSET_Y:
                this.offsetY = value;
                break;
            case OFFSET_Z:
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

    public float getProperty(ComponentProperty property) {
        switch (property) {
            case OFFSET_Y:
                return this.offsetY;
            case OFFSET_Z:
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
        compound.setString("identifier", this.type.getRegistryName().toString());
        compound.setIntArray("colour_layers", this.colours);
        compound.setFloat("offset_y", this.offsetY);
        compound.setFloat("offset_z", this.offsetZ);
        compound.setByte("version", (byte) 1);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        int version = compound.getByte("version");
        if (compound.hasKey("identifier")) {
            if (version > 0) {
                this.type =  ComponentRegistry.getRegistry().getValue(new ResourceLocation(compound.getString("identifier")));
            } else {
                this.type = ComponentRegistry.getRegistry().getValue(new ResourceLocation(Wearables.MODID, compound.getString("identifier")));
            }
        }
        if (this.type == null) {
            this.type = ComponentRegistry.BLANK;
        }
        int layerCount = this.type.getLayers(false).length;
        if (compound.hasKey("colour_layers")) {
            this.colours = compound.getIntArray("colour_layers");
            if (this.colours.length != layerCount) {
                int[] loadedColours = this.colours;
                this.colours = new int[layerCount];
                System.arraycopy(loadedColours, 0, this.colours, 0, Math.min(loadedColours.length, this.colours.length));
            }
        } else {
            this.colours = new int[layerCount];
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

    public Data toData() {
        return new Data(this.type, this.colours);
    }

    public static final class Data {
        public final int type;
        public final int[] colours;

        private Data(WearableComponentType type, int[] colours) {
            this.type = ComponentRegistry.getRegistry().getValues().indexOf(type);
            this.colours = colours;
        }

        @Override
        public int hashCode() {
            return this.type << 16 | Arrays.hashCode(this.colours);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Data) {
                Data data = (Data) obj;
                return data.type == this.type && Arrays.equals(data.colours, this.colours);
            }
            return false;
        }
    }
}
