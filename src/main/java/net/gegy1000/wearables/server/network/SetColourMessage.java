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
    private int red;
    private int green;
    private int blue;

    public SetColourMessage() {
    }

    public SetColourMessage(BlockPos pos, int layer, int red, int green, int blue) {
        this.pos = pos;
        this.layer = layer;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = BlockPos.fromLong(buf.readLong());
        this.layer = buf.readByte() & 0xFF;
        this.red = buf.readByte() & 0xFF;
        this.green = buf.readByte() & 0xFF;
        this.blue = buf.readByte() & 0xFF;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.pos.toLong());
        buf.writeByte(this.layer);
        buf.writeByte(this.red);
        buf.writeByte(this.green);
        buf.writeByte(this.blue);
    }

    public static class Handler implements IMessageHandler<SetColourMessage, IMessage> {
        @Override
        public IMessage onMessage(SetColourMessage message, MessageContext ctx) {
            if (ctx.side.isServer()) {
                EntityPlayer player = ctx.getServerHandler().playerEntity;
                Wearables.PROXY.schedule(() -> {
                    if (player.world.isBlockLoaded(message.pos)) {
                        TileEntity tile = player.world.getTileEntity(message.pos);
                        if (tile instanceof WearableColouriserEntity) {
                            WearableColouriserEntity entity = (WearableColouriserEntity) tile;
                            if (entity.canInteractWith(player)) {
                                entity.setColour(message.layer, message.red, message.green, message.blue);
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
