package net.gegy1000.wearables.server.block.entity.machine;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

public class BroadcastItemStackHandler extends ItemStackHandler {
    private TileEntity entity;

    public BroadcastItemStackHandler(TileEntity entity, int size) {
        super(size);
        this.entity = entity;
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        this.entity.markDirty();
        IBlockState state = this.entity.getWorld().getBlockState(this.entity.getPos());
        this.entity.getWorld().notifyBlockUpdate(this.entity.getPos(), state, state, 3);
    }
}
