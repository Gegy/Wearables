package net.gegy1000.wearables.server.item;

import net.gegy1000.wearables.server.api.item.RegisterItemModel;
import net.gegy1000.wearables.server.block.BlockRegistry;
import net.gegy1000.wearables.server.block.DisplayMannequinBlock;
import net.gegy1000.wearables.server.tab.TabRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DisplayMannequinItem extends Item implements RegisterItemModel {
    public DisplayMannequinItem() {
        super();
        this.setUnlocalizedName("display_mannequin_item");
        this.setCreativeTab(TabRegistry.GENERAL);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (facing != EnumFacing.UP) {
            return EnumActionResult.FAIL;
        } else {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if (!block.isReplaceable(world, pos)) {
                pos = pos.offset(facing);
            }
            ItemStack stack = player.getHeldItem(hand);
            if (player.canPlayerEdit(pos, facing, stack) && BlockRegistry.DISPLAY_MANNEQUIN.canPlaceBlockAt(world, pos)) {
                this.place(world, pos, EnumFacing.fromAngle(player.rotationYaw), BlockRegistry.DISPLAY_MANNEQUIN);
                SoundType soundType = world.getBlockState(pos).getBlock().getSoundType(world.getBlockState(pos), world, pos, player);
                world.playSound(player, pos, soundType.getPlaceSound(), SoundCategory.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
                stack.shrink(1);
                return EnumActionResult.SUCCESS;
            } else {
                return EnumActionResult.FAIL;
            }
        }
    }

    private void place(World world, BlockPos pos, EnumFacing facing, Block block) {
        BlockPos top = pos.up();
        IBlockState state = block.getDefaultState().withProperty(DisplayMannequinBlock.FACING, facing);
        world.setBlockState(pos, state.withProperty(DisplayMannequinBlock.HALF, DisplayMannequinBlock.Half.LOWER), 2);
        world.setBlockState(top, state.withProperty(DisplayMannequinBlock.HALF, DisplayMannequinBlock.Half.UPPER), 2);
        world.notifyNeighborsOfStateChange(pos, block, false);
        world.notifyNeighborsOfStateChange(top, block, false);
    }
}
