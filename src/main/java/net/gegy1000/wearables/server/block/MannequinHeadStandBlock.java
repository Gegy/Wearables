package net.gegy1000.wearables.server.block;

import net.gegy1000.wearables.server.api.item.RegisterBlockEntity;
import net.gegy1000.wearables.server.api.item.RegisterItemModel;
import net.gegy1000.wearables.server.block.entity.MannequinHeadStandEntity;
import net.gegy1000.wearables.server.item.WearableItem;
import net.gegy1000.wearables.server.tab.TabRegistry;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class MannequinHeadStandBlock extends BlockContainer implements RegisterItemModel, RegisterBlockEntity {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public MannequinHeadStandBlock() {
        super(Material.ROCK);
        this.setHardness(0.5F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setCreativeTab(TabRegistry.GENERAL);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(pos);
        if (player.canPlayerEdit(pos, facing, player.getHeldItem(hand))) {
            if (tile instanceof MannequinHeadStandEntity) {
                MannequinHeadStandEntity entity = (MannequinHeadStandEntity) tile;
                ItemStack heldItem = player.getHeldItem(hand);
                if (player.inventory.getFirstEmptyStack() >= 0) {
                    if (!(heldItem.getItem() instanceof WearableItem) || ((WearableItem) heldItem.getItem()).armorType != EntityEquipmentSlot.HEAD) {
                        heldItem = ItemStack.EMPTY;
                    }
                    ItemStack result = entity.swapItem(heldItem);
                    heldItem.shrink(1);
                    player.inventory.addItemStackToInventory(result);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof MannequinHeadStandEntity) {
            MannequinHeadStandEntity entity = (MannequinHeadStandEntity) tile;
            IItemHandler inventory = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            WearableUtils.dropInventory(world, pos, inventory);
            world.updateComparatorOutputLevel(pos, this);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirror) {
        return state.withProperty(FACING, mirror.mirror(state.getValue(FACING)));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getFront(meta);
        if (facing.getAxis() == Axis.Y) {
            facing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new MannequinHeadStandEntity();
    }

    @Override
    public Class<? extends TileEntity> getEntity() {
        return MannequinHeadStandEntity.class;
    }
}
