package net.gegy1000.wearables.server.network;

import io.netty.buffer.ByteBuf;
import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.block.entity.machine.WearableFabricatorEntity;
import net.gegy1000.wearables.server.wearable.component.ComponentRegistry;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SetSelectedComponentMessage implements IMessage {
    private BlockPos pos;
    private String identifier;

    public SetSelectedComponentMessage() {
    }

    public SetSelectedComponentMessage(BlockPos pos, String identifier) {
        this.pos = pos;
        this.identifier = identifier;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = BlockPos.fromLong(buf.readLong());
        this.identifier = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.pos.toLong());
        ByteBufUtils.writeUTF8String(buf, this.identifier);
    }

    public static class Handler implements IMessageHandler<SetSelectedComponentMessage, IMessage> {
        @Override
        public IMessage onMessage(SetSelectedComponentMessage message, MessageContext ctx) {
            if (ctx.side.isServer()) {
                EntityPlayer player = ctx.getServerHandler().player;
                Wearables.PROXY.schedule(() -> {
                    if (player.world.isBlockLoaded(message.pos)) {
                        WearableComponentType type = ComponentRegistry.get(message.identifier);
                        if (type != null) {
                            TileEntity tile = player.world.getTileEntity(message.pos);
                            if (tile instanceof WearableFabricatorEntity) {
                                WearableFabricatorEntity entity = (WearableFabricatorEntity) tile;
                                if (entity.canInteractWith(player)) {
                                    entity.setSelectedComponent(type);
                                    entity.markDirty();
                                }
                            }
                        }
                    }
                }, ctx);
            }
            return null;
        }
    }
}
