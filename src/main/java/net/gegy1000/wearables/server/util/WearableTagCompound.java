package net.gegy1000.wearables.server.util;

import net.gegy1000.wearables.server.wearable.Wearable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class WearableTagCompound extends CachedTagCompound<Wearable> {
    @Override
    protected Wearable parse(NBTTagCompound compound) {
        return Wearable.deserialize(compound);
    }

    public static Wearable get(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        WearableTagCompound wearableCompound;
        if (compound == null) {
            wearableCompound = new WearableTagCompound();
            stack.setTagCompound(wearableCompound);
        } else if (!(compound instanceof WearableTagCompound)) {
            wearableCompound = new WearableTagCompound();
            wearableCompound.load(compound);
            stack.setTagCompound(wearableCompound);
        } else {
            wearableCompound = (WearableTagCompound) compound;
        }
        return wearableCompound.getValue();
    }
}
