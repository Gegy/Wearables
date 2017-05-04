package net.gegy1000.wearables.server.api.item;

import net.minecraft.tileentity.TileEntity;

public interface RegisterBlockEntity {
    Class<? extends TileEntity> getEntity();
}
