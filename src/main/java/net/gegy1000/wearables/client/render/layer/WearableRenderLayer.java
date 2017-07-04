package net.gegy1000.wearables.client.render.layer;

import net.gegy1000.wearables.client.model.component.ComponentModelRegistry;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.gegy1000.wearables.server.item.WearableItem;
import net.gegy1000.wearables.server.util.WearableColourUtils;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.gegy1000.wearables.server.wearable.Wearable;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.ilexiconn.llibrary.client.event.PlayerModelEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class WearableRenderLayer implements LayerRenderer<EntityLivingBase> {
    private static final Minecraft MC = Minecraft.getMinecraft();

    private RenderLivingBase renderer;

    public WearableRenderLayer(RenderLivingBase renderer) {
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float age, float yaw, float pitch, float scale) {
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.enableCull();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.renderPiece(EntityEquipmentSlot.HEAD, entity, limbSwing, limbSwingAmount, partialTicks, age, yaw, pitch, scale);
        this.renderPiece(EntityEquipmentSlot.CHEST, entity, limbSwing, limbSwingAmount, partialTicks, age, yaw, pitch, scale);
        this.renderPiece(EntityEquipmentSlot.LEGS, entity, limbSwing, limbSwingAmount, partialTicks, age, yaw, pitch, scale);
        this.renderPiece(EntityEquipmentSlot.FEET, entity, limbSwing, limbSwingAmount, partialTicks, age, yaw, pitch, scale);
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
    }

    private void renderPiece(EntityEquipmentSlot slot, EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float age, float yaw, float pitch, float scale) {
        ItemStack stack = entity.getItemStackFromSlot(slot);
        if (!WearableUtils.isStackEmpty(stack) && stack.getItem() instanceof WearableItem) {
            WearableItem item = (WearableItem) stack.getItem();
            if (item.armorType == slot) {
                Wearable wearable = WearableItem.getWearable(stack);
                for (WearableComponent component : wearable.getComponents()) {
                    WearableComponentType componentType = component.getType();
                    boolean smallArms = WearableUtils.hasSlimArms(entity);
                    WearableComponentType.Layer[] layers = componentType.getLayers(smallArms);
                    if (entity instanceof EntityPlayer && this.renderer.getMainModel() instanceof ModelPlayer) {
                        MinecraftForge.EVENT_BUS.post(new PlayerModelEvent.SetRotationAngles((ModelPlayer) this.renderer.getMainModel(), (EntityPlayer) entity, limbSwing, limbSwingAmount, age, yaw, pitch, scale));
                    }
                    for (int layerIndex = 0; layerIndex < layers.length; layerIndex++) {
                        WearableComponentType.Layer layer = layers[layerIndex];
                        WearableComponentModel model = ComponentModelRegistry.getRegistry().getValue(layer.getModel());
                        if (model != null) {
                            model.setModelAttributes(this.renderer.getMainModel());
                            model.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
                            model.setOffsets(component.getOffsetY(), component.getOffsetZ());
                            ResourceLocation texture = layer.getTexture();
                            if (texture == null) {
                                GlStateManager.disableTexture2D();
                            } else {
                                GlStateManager.enableTexture2D();
                                MC.getTextureManager().bindTexture(texture);
                            }
                            GlStateManager.pushMatrix();
                            float[] colour = WearableColourUtils.toRGBFloatArray(component.getColour(layerIndex));
                            GlStateManager.color(colour[0], colour[1], colour[2], 1.0F);
                            model.render(entity, limbSwing, limbSwingAmount, age, yaw, pitch, scale);
                            GlStateManager.popMatrix();
                        }
                    }
                }
                GlStateManager.enableTexture2D();
            }
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
