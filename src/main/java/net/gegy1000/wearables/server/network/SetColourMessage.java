package net.gegy1000.wearables.server.network;

import io.netty.buffer.ByteBuf;
import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.block.entity.machine.WearableColouriserEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SetColourMessage implements IMessage {
    private BlockPos pos;
    private int layer;
    private int colour;

    public SetColourMessage() {
    }

    public SetColourMessage(BlockPos pos, int layer, int colour) {
        this.pos = pos;
        this.layer = layer;
        this.colour = colour;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = BlockPos.fromLong(buf.readLong());
        this.layer = buf.readByte() & 0xFF;
        this.colour = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.pos.toLong());
        buf.writeByte(this.layer);
        buf.writeInt(this.colour);
    }

    public static class Handler implements IMessageHandler<SetColourMessage, IMessage> {
        @Override
        public IMessage onMessage(SetColourMessage message, MessageContext ctx) {
            if (ctx.side.isServer()) {
                EntityPlayer player = ctx.getServerHandler().player;
                Wearables.PROXY.schedule(() -> {
                    if (player.world.isBlockLoaded(message.pos)) {
                        TileEntity tile = player.world.getTileEntity(message.pos);
                        if (tile instanceof WearableColouriserEntity) {
                            WearableColouriserEntity entity = (WearableColouriserEntity) tile;
                            if (entity.canInteractWith(player)) {
                                entity.setColour(message.layer, message.colour);
                                entity.markDirty();
                            }
                        }
                    }
                }, ctx);
            }
            return null;
        }
    }
}
