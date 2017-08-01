package net.gegy1000.wearables.server.util;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class CachedTagCompound<T> extends NBTTagCompound {
    @Nullable
    private T value;

    @Override
    public void setTag(String key, NBTBase value) {
        super.setTag(key, value);
        this.markDirty();
    }

    @Override
    public void setByte(String key, byte value) {
        super.setByte(key, value);
        this.markDirty();
    }

    @Override
    public void setShort(String key, short value) {
        super.setShort(key, value);
        this.markDirty();
    }

    @Override
    public void setInteger(String key, int value) {
        super.setInteger(key, value);
        this.markDirty();
    }

    @Override
    public void setLong(String key, long value) {
        super.setLong(key, value);
        this.markDirty();
    }

    @Override
    public void setFloat(String key, float value) {
        super.setFloat(key, value);
        this.markDirty();
    }

    @Override
    public void setDouble(String key, double value) {
        super.setDouble(key, value);
        this.markDirty();
    }

    @Override
    public void setString(String key, String value) {
        super.setString(key, value);
        this.markDirty();
    }

    @Override
    public void setByteArray(String key, byte[] value) {
        super.setByteArray(key, value);
        this.markDirty();
    }

    @Override
    public void setIntArray(String key, int[] value) {
        super.setIntArray(key, value);
        this.markDirty();
    }

    private void markDirty() {
        this.value = null;
    }

    public void load(NBTTagCompound compound) {
        this.merge(compound);
        this.markDirty();
    }

    @Nonnull
    public T getValue() {
        if (this.value == null) {
            this.value = this.parse(this);
        }
        return this.value;
    }

    @Nonnull
    protected abstract T parse(NBTTagCompound compound);
}
