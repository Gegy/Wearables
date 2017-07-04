package net.gegy1000.wearables.server.block;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.ServerProxy;
import net.gegy1000.wearables.server.api.item.RegisterItemModel;
import net.gegy1000.wearables.server.block.entity.machine.InventoryBlockEntity;
import net.gegy1000.wearables.server.block.entity.machine.WearableFabricatorEntity;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class WearableFabricatorBlock extends MachineBlock implements RegisterItemModel {
    public WearableFabricatorBlock() {
        super(Material.ROCK);
        this.setUnlocalizedName("wearable_fabricator");
        this.setHardness(0.5F);
    }

    @Override
    public Class<? extends TileEntity> getEntity() {
        return WearableFabricatorEntity.class;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new WearableFabricatorEntity();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof WearableFabricatorEntity) {
            if (!world.isRemote) {
                player.openGui(Wearables.INSTANCE, ServerProxy.WEARABLE_FABRICATOR_GUI, world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
        return false;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof InventoryBlockEntity) {
            InventoryBlockEntity entity = (InventoryBlockEntity) tile;
            IItemHandler inventory = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            WearableUtils.dropInventory(world, pos, inventory, 4);
            world.updateComparatorOutputLevel(pos, this);
        }
        world.removeTileEntity(pos);
    }
}
