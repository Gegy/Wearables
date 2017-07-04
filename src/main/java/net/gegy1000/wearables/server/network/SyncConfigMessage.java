package net.gegy1000.wearables.server.network;

import io.netty.buffer.ByteBuf;
import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.config.WearablesConfig;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncConfigMessage implements IMessage {
    private WearablesConfig.Restrictions restrictions = new WearablesConfig.Restrictions();

    @Override
    public void fromBytes(ByteBuf buf) {
        this.restrictions.allowWingFlight = buf.readBoolean();
        this.restrictions.allowJetpackFlight = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        this.restrictions = WearablesConfig.restrictions;
        buf.writeBoolean(this.restrictions.allowWingFlight);
        buf.writeBoolean(this.restrictions.allowJetpackFlight);
    }

    public static class Handler implements IMessageHandler<SyncConfigMessage, IMessage> {
        @Override
        public IMessage onMessage(SyncConfigMessage message, MessageContext ctx) {
            if (ctx.side.isClient()) {
                Wearables.PROXY.schedule(() -> WearablesConfig.updateGlobal(message.restrictions), ctx);
            }
            return null;
        }
    }
}
