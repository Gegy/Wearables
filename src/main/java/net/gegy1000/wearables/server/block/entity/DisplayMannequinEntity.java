package net.gegy1000.wearables.server.block.entity;

import net.gegy1000.wearables.server.block.entity.machine.BroadcastItemStackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class DisplayMannequinEntity extends TileEntity {
    private ItemStackHandler inventory = new BroadcastItemStackHandler(this, 4);

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setTag("inventory", this.inventory.serializeNBT());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.inventory.deserializeNBT(compound.getCompoundTag("inventory"));
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.inventory);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packet) {
        this.handleUpdateTag(packet.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation("tile.display_mannequin.name");
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        int x = this.pos.getX();
        int y = this.pos.getY();
        int z = this.pos.getZ();
        return new AxisAlignedBB(x, y, z, x + 1, y + 2.3, z + 1);
    }

    public boolean isUsableByPlayer(EntityPlayer player) {
        return player.getDistanceSqToCenter(this.pos) <= 64.0;
    }

    public ItemStack swapItem(EntityEquipmentSlot slot, ItemStack heldItem) {
        int index = 3 - slot.getIndex();
        ItemStack previous = this.inventory.getStackInSlot(index);
        this.inventory.setStackInSlot(index, heldItem.copy());
        return previous;
    }

    public ItemStack getStack(EntityEquipmentSlot slot) {
        return this.inventory.getStackInSlot(3 - slot.getIndex());
    }
}
