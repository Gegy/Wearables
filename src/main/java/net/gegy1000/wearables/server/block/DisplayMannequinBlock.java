package net.gegy1000.wearables.server.block;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.ServerProxy;
import net.gegy1000.wearables.server.api.item.RegisterBlockEntity;
import net.gegy1000.wearables.server.api.item.RegisterItemModel;
import net.gegy1000.wearables.server.block.entity.DisplayMannequinEntity;
import net.gegy1000.wearables.server.item.ItemRegistry;
import net.gegy1000.wearables.server.item.WearableItem;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.Random;

public class DisplayMannequinBlock extends Block implements RegisterItemModel, RegisterBlockEntity {
    public static final PropertyEnum<Half> HALF = PropertyEnum.create("half", Half.class);
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public DisplayMannequinBlock() {
        super(Material.ROCK);
        this.setHardness(0.5F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, Half.LOWER).withProperty(FACING, EnumFacing.NORTH));
        this.setLightLevel(0.3F);
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
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if (state.getValue(HALF) == Half.UPPER) {
            BlockPos down = pos.down();
            IBlockState downState = world.getBlockState(down);
            if (downState.getBlock() != this) {
                world.setBlockToAir(pos);
            } else if (block != this) {
                downState.neighborChanged(world, down, block, fromPos);
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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (state.getValue(HALF) == Half.UPPER) {
            pos = pos.down();
        }
        state = world.getBlockState(pos);
        if (state.getBlock() instanceof DisplayMannequinBlock && state.getValue(HALF) == Half.LOWER) {
            if (player.canPlayerEdit(pos, facing, player.getHeldItem(hand))) {
                TileEntity tile = world.getTileEntity(pos);
                if (tile instanceof DisplayMannequinEntity) {
                    DisplayMannequinEntity entity = (DisplayMannequinEntity) tile;
                    ItemStack heldItem = player.getHeldItem(hand);
                    if (player.isSneaking() && heldItem.getItem() instanceof WearableItem && player.inventory.getFirstEmptyStack() >= 0) {
                        WearableItem wearableItem = (WearableItem) heldItem.getItem();
                        EntityEquipmentSlot slot = wearableItem.armorType;
                        ItemStack result = entity.swapItem(slot, heldItem);
                        heldItem.shrink(1);
                        player.inventory.addItemStackToInventory(result);
                        return true;
                    }
                    if (!world.isRemote) {
                        player.openGui(Wearables.INSTANCE, ServerProxy.DISPLAY_MANNEQUIN_GUI, world, pos.getX(), pos.getY(), pos.getZ());
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof DisplayMannequinEntity) {
            DisplayMannequinEntity entity = (DisplayMannequinEntity) tile;
            IItemHandler inventory = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            WearableUtils.dropInventory(world, pos, inventory);
            world.updateComparatorOutputLevel(pos, this);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(HALF) == Half.UPPER ? Items.AIR : this.getItem();
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
        return new ItemStack(this.getItem());
    }

    private Item getItem() {
        return ItemRegistry.DISPLAY_MANNEQUIN;
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
        return new BlockStateContainer(this, HALF, FACING);
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
        Half half = Half.fromMetadata(meta & 1);
        EnumFacing facing = EnumFacing.getHorizontal((meta >> 1) & 3);
        if (facing.getAxis() == Axis.Y) {
            facing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(HALF, half).withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HALF).getMetadata() | (state.getValue(FACING).getIndex() << 1);
    }

    @Override
    public Class<? extends TileEntity> getEntity() {
        return DisplayMannequinEntity.class;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return state.getValue(HALF) == Half.LOWER;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new DisplayMannequinEntity();
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
