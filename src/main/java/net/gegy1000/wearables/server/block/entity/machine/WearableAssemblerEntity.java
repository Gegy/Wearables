package net.gegy1000.wearables.server.block.entity.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class WearableAssemblerEntity extends TileEntity {
    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation("tile.wearable_assembler.name");
    }

    public boolean canInteractWith(EntityPlayer player) {
        return player.getDistanceSqToCenter(this.pos) <= 64.0;
    }
}
