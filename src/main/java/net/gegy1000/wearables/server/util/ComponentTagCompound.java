package net.gegy1000.wearables.server.util;

import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ComponentTagCompound extends CachedTagCompound<WearableComponent> {
    @Override
    protected WearableComponent parse(NBTTagCompound compound) {
        return WearableComponent.deserialize(compound);
    }

    public static WearableComponent get(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        ComponentTagCompound componentCompound;
        if (compound == null) {
            componentCompound = new ComponentTagCompound();
            stack.setTagCompound(componentCompound);
        } else if (!(compound instanceof ComponentTagCompound)) {
            componentCompound = new ComponentTagCompound();
            componentCompound.load(compound);
            stack.setTagCompound(componentCompound);
        } else {
            componentCompound = (ComponentTagCompound) compound;
        }
        return componentCompound.getValue();
    }
}
