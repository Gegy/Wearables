package net.gegy1000.wearables.client.render.block;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.model.block.DisplayMannequinModel;
import net.gegy1000.wearables.client.model.component.ComponentModelRegistry;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.gegy1000.wearables.server.block.DisplayMannequinBlock;
import net.gegy1000.wearables.server.block.entity.DisplayMannequinEntity;
import net.gegy1000.wearables.server.item.WearableItem;
import net.gegy1000.wearables.server.util.WearableColourUtils;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.gegy1000.wearables.server.wearable.Wearable;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class DisplayMannequinRenderer extends TileEntitySpecialRenderer<DisplayMannequinEntity> {
    private static final Minecraft MC = Minecraft.getMinecraft();

    private static final DisplayMannequinModel MODEL = new DisplayMannequinModel();
    private static final ResourceLocation TEXTURE = new ResourceLocation(Wearables.MODID, "textures/blocks/display_mannequin.png");
    private static final ResourceLocation TEXTURE_LIGHT = new ResourceLocation(Wearables.MODID, "textures/blocks/display_mannequin_light.png");

    public void renderStatic(DisplayMannequinEntity entity, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.5, 1.5F, 0.5);
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);

        float lastBrightnessX = OpenGlHelper.lastBrightnessX;
        float lastBrightnessY = OpenGlHelper.lastBrightnessY;

        GlStateManager.enableRescaleNormal();

        MC.getTextureManager().bindTexture(TEXTURE);
        MODEL.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
        MC.getTextureManager().bindTexture(TEXTURE_LIGHT);
        MODEL.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.renderPiece(EntityEquipmentSlot.HEAD, entity, partialTicks, 0.0625F);
        this.renderPiece(EntityEquipmentSlot.CHEST, entity, partialTicks, 0.0625F);
        this.renderPiece(EntityEquipmentSlot.LEGS, entity, partialTicks, 0.0625F);
        this.renderPiece(EntityEquipmentSlot.FEET, entity, partialTicks, 0.0625F);
        GlStateManager.disableBlend();

        GlStateManager.popMatrix();
    }

    @Override
    public void render(DisplayMannequinEntity entity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (entity != null) {
            BlockPos pos = entity.getPos();
            IBlockState state = entity.getWorld().getBlockState(pos);
            EnumFacing facing = EnumFacing.SOUTH;
            if (state.getBlock() instanceof DisplayMannequinBlock) {
                facing = state.getValue(DisplayMannequinBlock.FACING);
            }
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5, y + 1.5F, z + 0.5);
            GlStateManager.scale(-1.0F, -1.0F, 1.0F);
            GlStateManager.rotate(facing.getHorizontalAngle(), 0.0F, 1.0F, 0.0F);

            float lastBrightnessX = OpenGlHelper.lastBrightnessX;
            float lastBrightnessY = OpenGlHelper.lastBrightnessY;

            GlStateManager.enableRescaleNormal();

            MC.getTextureManager().bindTexture(TEXTURE);
            MODEL.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
            MC.getTextureManager().bindTexture(TEXTURE_LIGHT);
            MODEL.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);

            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.renderPiece(EntityEquipmentSlot.HEAD, entity, partialTicks, 0.0625F);
            this.renderPiece(EntityEquipmentSlot.CHEST, entity, partialTicks, 0.0625F);
            this.renderPiece(EntityEquipmentSlot.LEGS, entity, partialTicks, 0.0625F);
            this.renderPiece(EntityEquipmentSlot.FEET, entity, partialTicks, 0.0625F);
            GlStateManager.disableBlend();

            GlStateManager.popMatrix();
        }
    }

    private void renderPiece(EntityEquipmentSlot slot, DisplayMannequinEntity entity, float partialTicks, float scale) {
        ItemStack stack = entity.getStack(slot);
        if (!WearableUtils.isStackEmpty(stack) && stack.getItem() instanceof WearableItem) {
            WearableItem item = (WearableItem) stack.getItem();
            if (item.armorType == slot) {
                Wearable wearable = WearableItem.getWearable(stack);
                for (WearableComponent component : wearable.getComponents()) {
                    WearableComponentType componentType = component.getType();
                    WearableComponentType.Layer[] layers = componentType.getLayers(false);
                    for (int layerIndex = 0; layerIndex < layers.length; layerIndex++) {
                        WearableComponentType.Layer layer = layers[layerIndex];
                        WearableComponentModel model = ComponentModelRegistry.getRegistry().getValue(layer.getModel());
                        if (model != null) {
                            model.setLivingAnimations(null, 0.0F, 0.0F, partialTicks);
                            model.setModelAttributes(MODEL);
                            model.setOffsets(component.getOffsetY(), component.getOffsetZ());
                            ResourceLocation texture = layer.getTexture();
                            if (texture == null) {
                                GlStateManager.disableTexture2D();
                            } else {
                                GlStateManager.enableTexture2D();
                                MC.getTextureManager().bindTexture(texture);
                            }
                            float[] colour = WearableColourUtils.toRGBFloatArray(component.getColour(layerIndex));
                            GlStateManager.pushMatrix();
                            GlStateManager.color(colour[0], colour[1], colour[2], 1.0F);
                            model.renderMannequin(scale);
                            GlStateManager.popMatrix();
                        }
                    }
                }
                GlStateManager.enableTexture2D();
            }
        }
    }
}
