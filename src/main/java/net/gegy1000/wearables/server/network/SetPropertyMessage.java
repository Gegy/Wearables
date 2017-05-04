package net.gegy1000.wearables.server.network;

import io.netty.buffer.ByteBuf;
import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.block.entity.machine.WearableAssemblerEntity;
import net.gegy1000.wearables.server.container.WearableAssemblerContainer;
import net.gegy1000.wearables.server.item.WearableComponentItem;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SetPropertyMessage implements IMessage {
    private BlockPos pos;
    private int slot;
    private int property;
    private float value;

    public SetPropertyMessage() {
    }

    public SetPropertyMessage(BlockPos pos, int slot, int property, float value) {
        this.pos = pos;
        this.slot = slot;
        this.property = property;
        this.value = value;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = BlockPos.fromLong(buf.readLong());
        this.slot = buf.readByte() & 0xFF;
        this.property = buf.readByte() & 0xFF;
        this.value = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.pos.toLong());
        buf.writeByte(this.slot);
        buf.writeByte(this.property);
        buf.writeFloat(this.value);
    }

    public static class Handler implements IMessageHandler<SetPropertyMessage, IMessage> {
        @Override
        public IMessage onMessage(SetPropertyMessage message, MessageContext ctx) {
            if (ctx.side.isServer()) {
                EntityPlayer player = ctx.getServerHandler().playerEntity;
                Wearables.PROXY.schedule(() -> {
                    if (player.world.isBlockLoaded(message.pos)) {
                        TileEntity tile = player.world.getTileEntity(message.pos);
                        if (tile instanceof WearableAssemblerEntity) {
                            WearableAssemblerEntity entity = (WearableAssemblerEntity) tile;
                            if (entity.canInteractWith(player)) {
                                Container container = player.openContainer;
                                if (container instanceof WearableAssemblerContainer) {
                                    Slot slot = container.getSlot(message.slot);
                                    if (slot != null && slot.getStack().getItem() instanceof WearableComponentItem) {
                                        WearableComponent component = WearableComponentItem.getComponent(slot.getStack());
                                        WearableComponentType type = component.getType();
                                        if ((type.getSupportedProperties() & message.property) != 0) {
                                            component.setProperty(message.property, MathHelper.clamp(message.value, type.getMinimum(message.property), type.getMaximum(message.property)));
                                            slot.getStack().setTagCompound(component.serializeNBT());
                                            ((WearableAssemblerContainer) container).onContentsChanged();
                                        }
                                    }
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
