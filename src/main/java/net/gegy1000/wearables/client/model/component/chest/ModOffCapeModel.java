package net.gegy1000.wearables.client.model.component.chest;

import com.google.common.collect.ImmutableList;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.ilexiconn.llibrary.LLibrary;
import net.ilexiconn.llibrary.client.util.Matrix;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModOffCapeModel extends WearableComponentModel {
    private ModelRenderer cape;

    public ModOffCapeModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.cape = new ModelRenderer(this, 0, 0);
        this.cape.setTextureSize(32, 32);
        this.cape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
    }

    @Override
    public void renderComponent(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0625F, 0.125F);
        if (entity instanceof AbstractClientPlayer) {
            float partialTicks = LLibrary.PROXY.getPartialTicks();
            AbstractClientPlayer player = (AbstractClientPlayer) entity;
            double x = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
            double y = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
            double z = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
            double chaseX = player.prevChasingPosX + (player.chasingPosX - player.prevChasingPosX) * partialTicks;
            double chaseY = player.prevChasingPosY + (player.chasingPosY - player.prevChasingPosY) * partialTicks;
            double chaseZ = player.prevChasingPosZ + (player.chasingPosZ - player.prevChasingPosZ) * partialTicks;
            double deltaX = chaseX - x;
            double deltaY = chaseY - y;
            double deltaZ = chaseZ - z;
            float renderYawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
            double sin = MathHelper.sin(renderYawOffset * 0.017453292F);
            double cos = -MathHelper.cos(renderYawOffset * 0.017453292F);
            float lift = (float) MathHelper.clamp(deltaY * 10.0F, -6.0F, 32.0F);
            float forwardMovement = (float) (deltaX * sin + deltaZ * cos) * 100.0F;
            float sidewardMovement = (float) (deltaX * cos - deltaZ * sin) * 100.0F;
            forwardMovement = MathHelper.clamp(forwardMovement, 0.0F, 200.0F);
            float cameraYaw = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * partialTicks;
            lift = lift + MathHelper.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * cameraYaw;
            if (player.isSneaking()) {
                lift += 60.0F;
            }
            GlStateManager.rotate(6.0F + forwardMovement / 2.0F + lift, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(sidewardMovement / 2.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(-sidewardMovement / 2.0F, 0.0F, 1.0F, 0.0F);
        }
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        this.renderParented(this.bipedBody, this.cape, scale);
        GlStateManager.popMatrix();
    }

    @Override
    public void buildQuads(Matrix matrix, ImmutableList.Builder<BakedQuad> builder, VertexFormat format, TextureAtlasSprite sprite, int layer) {
        this.buildCuboidParented(this.bipedBody, this.cape, matrix, builder, format, sprite, layer);
    }
}
