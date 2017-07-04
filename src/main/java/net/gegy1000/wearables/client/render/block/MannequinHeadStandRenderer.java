package net.gegy1000.wearables.client.render.block;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.model.block.HeadDisplayStandModel;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.gegy1000.wearables.client.render.RenderRegistry;
import net.gegy1000.wearables.client.render.component.ComponentRenderer;
import net.gegy1000.wearables.server.block.MannequinHeadStandBlock;
import net.gegy1000.wearables.server.block.entity.MannequinHeadStandEntity;
import net.gegy1000.wearables.server.item.WearableItem;
import net.gegy1000.wearables.server.util.WearableColourUtils;
import net.gegy1000.wearables.server.wearable.Wearable;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class MannequinHeadStandRenderer extends TileEntitySpecialRenderer<MannequinHeadStandEntity> {
    private static final Minecraft MC = Minecraft.getMinecraft();

    private static final HeadDisplayStandModel MODEL = new HeadDisplayStandModel();
    private static final ResourceLocation TEXTURE = new ResourceLocation(Wearables.MODID, "textures/blocks/head_display_stand.png");

    @Override
    public void renderTileEntityAt(MannequinHeadStandEntity entity, double x, double y, double z, float partialTicks, int destroyStage) {
        if (entity != null) {
            BlockPos pos = entity.getPos();
            IBlockState state = entity.getWorld().getBlockState(pos);
            EnumFacing facing = EnumFacing.SOUTH;
            if (state.getBlock() instanceof MannequinHeadStandBlock) {
                facing = state.getValue(MannequinHeadStandBlock.FACING);
            }
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5, y + 1.5F, z + 0.5);
            GlStateManager.scale(-1.0F, -1.0F, 1.0F);
            GlStateManager.rotate(facing.getHorizontalAngle() - 180.0F, 0.0F, 1.0F, 0.0F);

            GlStateManager.enableRescaleNormal();

            MC.getTextureManager().bindTexture(TEXTURE);
            MODEL.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

            GlStateManager.pushMatrix();
            ItemStack stack = entity.getStack();
            if (!stack.isEmpty() && stack.getItem() instanceof WearableItem) {
                Wearable wearable = WearableItem.getWearable(stack);
                for (WearableComponent component : wearable.getComponents()) {
                    WearableComponentType componentType = component.getType();
                    ComponentRenderer renderer = RenderRegistry.getRenderer(componentType.getIdentifier());
                    WearableComponentModel model = renderer.getModel(false);
                    model.setLivingAnimations(null, 0.0F, 0.0F, partialTicks);
                    model.setModelAttributes(MODEL);
                    model.setOffsets(component.getOffsetY(), component.getOffsetZ());
                    for (int layer = 0; layer < componentType.getLayerCount(); layer++) {
                        ResourceLocation texture = renderer.getTexture(false, layer);
                        if (texture == null) {
                            GlStateManager.disableTexture2D();
                        } else {
                            GlStateManager.enableTexture2D();
                            MC.getTextureManager().bindTexture(texture);
                        }
                        float[] colour = renderer.adjustColour(WearableColourUtils.toRGBFloatArray(component.getColour(layer)), layer);
                        GlStateManager.pushMatrix();
                        GlStateManager.color(colour[0], colour[1], colour[2], 1.0F);
                        model.renderMannequin(0.0625F);
                        GlStateManager.popMatrix();
                    }
                }
                GlStateManager.enableTexture2D();
            }
            GlStateManager.popMatrix();

            GlStateManager.disableBlend();

            GlStateManager.popMatrix();
        }
    }
}
