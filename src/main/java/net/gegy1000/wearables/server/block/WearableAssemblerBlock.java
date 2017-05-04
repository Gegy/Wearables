package net.gegy1000.wearables.server.block;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.ServerProxy;
import net.gegy1000.wearables.server.api.item.RegisterItemBlock;
import net.gegy1000.wearables.server.api.item.RegisterItemModel;
import net.gegy1000.wearables.server.block.entity.machine.WearableAssemblerEntity;
import net.gegy1000.wearables.server.item.WearableAssemblerItem;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WearableAssemblerBlock extends MachineBlock implements RegisterItemModel, RegisterItemBlock {
    public static final PropertyEnum<Half> HALF = PropertyEnum.create("half", Half.class);

    public WearableAssemblerBlock() {
        super(Material.WOOD);
        this.setSoundType(SoundType.WOOD);
        this.setUnlocalizedName("wearable_assembler");
        this.setHardness(0.5F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, Half.LOWER).withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public Class<? extends TileEntity> getEntity() {
        return WearableAssemblerEntity.class;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (state.getValue(HALF) == Half.UPPER) {
            pos = pos.down();
        }
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof WearableAssemblerEntity) {
            if (!world.isRemote) {
                player.openGui(Wearables.INSTANCE, ServerProxy.WEARABLE_ASSEMBLER_GUI, world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
        return false;
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        BlockPos down = pos.down();
        BlockPos up = pos.up();
        if (player.capabilities.isCreativeMode && state.getValue(HALF) == Half.UPPER && world.getBlockState(down).getBlock() == this) {
            world.setBlockToAir(down);
        }
        if (state.getValue(HALF) == Half.LOWER && world.getBlockState(up).getBlock() == this) {
            if (player.capabilities.isCreativeMode) {
                world.setBlockToAir(pos);
            }
            world.setBlockToAir(up);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block) {
        if (state.getValue(HALF) == Half.UPPER) {
            BlockPos down = pos.down();
            IBlockState downState = world.getBlockState(down);
            if (downState.getBlock() != this) {
                world.setBlockToAir(pos);
            } else if (block != this) {
                downState.neighborChanged(world, down, block);
            }
        } else {
            BlockPos up = pos.up();
            IBlockState upState = world.getBlockState(up);
            if (upState.getBlock() != this) {
                world.setBlockToAir(pos);
                if (!world.isRemote) {
                    this.dropBlockAsItem(world, pos, state, 0);
                }
            }
            if (!world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP)) {
                world.setBlockToAir(pos);
                if (!world.isRemote) {
                    this.dropBlockAsItem(world, pos, state, 0);
                }
                if (upState.getBlock() == this) {
                    world.setBlockToAir(up);
                }
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(HALF) == WearableAssemblerBlock.Half.UPPER ? null : super.getItemDropped(state, rand, fortune);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return pos.getY() < world.getHeight() - 1 && (world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && super.canPlaceBlockAt(world, pos) && super.canPlaceBlockAt(world, pos.up()));
    }

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState state) {
        return EnumPushReaction.DESTROY;
    }

    @Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(this);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        Half half = Half.fromMetadata(meta & 1);
        EnumFacing facing = EnumFacing.getHorizontal((meta >> 1) & 3);
        if (facing.getAxis() == EnumFacing.Axis.Y) {
            facing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(HALF, half).withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HALF).getMetadata() | (state.getValue(FACING).getIndex() << 1);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return state.getValue(HALF) == Half.LOWER;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new WearableAssemblerEntity();
    }

    @Override
    public ItemBlock createItemBlock() {
        return new WearableAssemblerItem();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, HALF);
    }

    public enum Half implements IStringSerializable {
        LOWER("lower", 0),
        UPPER("upper", 1);

        private String name;
        private int metadata;

        Half(String name, int metadata) {
            this.name = name;
            this.metadata = metadata;
        }

        public String toString() {
            return this.getName();
        }

        @Override
        public String getName() {
            return this.name;
        }

        public int getMetadata() {
            return this.metadata;
        }

        public static Half fromMetadata(int metadata) {
            if (metadata == 1) {
                return UPPER;
            }
            return LOWER;
        }
    }
}
