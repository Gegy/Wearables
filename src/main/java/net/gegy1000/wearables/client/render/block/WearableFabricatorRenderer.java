package net.gegy1000.wearables.client.render.block;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.model.block.FabricatorModel;
import net.gegy1000.wearables.server.block.WearableFabricatorBlock;
import net.gegy1000.wearables.server.block.entity.machine.WearableFabricatorEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.Random;

public class WearableFabricatorRenderer extends TileEntitySpecialRenderer<WearableFabricatorEntity> {
    private static final Minecraft MC = Minecraft.getMinecraft();

    private static final FabricatorModel MODEL = new FabricatorModel();
    private static final ResourceLocation TEXTURE = new ResourceLocation(Wearables.MODID, "textures/blocks/wearable_fabricator.png");

    @Override
    public void render(WearableFabricatorEntity entity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        EnumFacing facing = EnumFacing.SOUTH;

        if (entity != null) {
            BlockPos pos = entity.getPos();
            IBlockState state = entity.getWorld().getBlockState(pos);
            if (state.getBlock() instanceof WearableFabricatorBlock) {
                facing = state.getValue(WearableFabricatorBlock.FACING);
            }
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5, y + 1.5F, z + 0.5);
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.rotate(facing.getHorizontalAngle() - 180.0F, 0.0F, 1.0F, 0.0F);

        MC.getTextureManager().bindTexture(TEXTURE);
        MODEL.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

        if (entity != null) {
            IItemHandler inventory = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.2F, 1.35F, 0.1F);
            GlStateManager.scale(-0.3F, -0.3F, 0.3F);
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
            MC.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            for (int i = 0; i < 4; i++) {
                ItemStack stack = inventory.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(0.0F, i % 2 * -1.2F, 0.0F);
                    GlStateManager.translate(i / 2 * 1.0F, 0.0F, 0.0F);
                    for (int size = 0; size < Math.min(stack.getCount(), 4); size++) {
                        Random random = new Random(size << 16);
                        GlStateManager.translate((random.nextDouble() - 0.5) * 0.4, (random.nextDouble() - 0.5) * 0.4, -0.06F);
                        MC.getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
                    }
                    GlStateManager.popMatrix();
                }
            }
            GlStateManager.popMatrix();
        }

        GlStateManager.popMatrix();
    }
}
