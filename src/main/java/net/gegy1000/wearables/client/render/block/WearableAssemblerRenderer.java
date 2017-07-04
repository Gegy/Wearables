package net.gegy1000.wearables.client.render.block;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.model.block.AssemblerModel;
import net.gegy1000.wearables.server.block.WearableAssemblerBlock;
import net.gegy1000.wearables.server.block.entity.machine.WearableAssemblerEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class WearableAssemblerRenderer extends TileEntitySpecialRenderer<WearableAssemblerEntity> {
    private static final Minecraft MC = Minecraft.getMinecraft();

    private static final AssemblerModel MODEL = new AssemblerModel();
    private static final ResourceLocation TEXTURE = new ResourceLocation(Wearables.MODID, "textures/blocks/wearable_assembler.png");

    @Override
    public void render(WearableAssemblerEntity entity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        EnumFacing facing = EnumFacing.SOUTH;

        if (entity != null) {
            BlockPos pos = entity.getPos();
            IBlockState state = entity.getWorld().getBlockState(pos);
            if (state.getBlock() instanceof WearableAssemblerBlock) {
                facing = state.getValue(WearableAssemblerBlock.FACING);
            }
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5, y + 1.5F, z + 0.5);
        if (entity == null) {
            GlStateManager.translate(0.0, -0.6, 0.0);
        }
        float scale = entity == null ? 0.7F : 1.0F;
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.rotate(facing.getHorizontalAngle(), 0.0F, 1.0F, 0.0F);

        MC.getTextureManager().bindTexture(TEXTURE);
        MODEL.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

        GlStateManager.popMatrix();
    }
}
