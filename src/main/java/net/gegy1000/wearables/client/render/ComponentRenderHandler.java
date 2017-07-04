package net.gegy1000.wearables.client.render;

import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.gegy1000.wearables.client.render.component.ComponentRenderer;
import net.gegy1000.wearables.server.core.WearablesClientHooks;
import net.gegy1000.wearables.server.util.WearableColourUtils;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.ilexiconn.llibrary.client.util.Matrix;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Point3f;

@SideOnly(Side.CLIENT)
public class ComponentRenderHandler {
    private static final Minecraft MC = Minecraft.getMinecraft();
    private static final Matrix INVENTORY_TRANSFORM_MATRIX = new Matrix();
    private static final Matrix INVENTORY_UNTRANSFORM_MATRIX = new Matrix();

    private static final ModelBiped STATIC_MODEL = new ModelBiped();

    static {
        INVENTORY_TRANSFORM_MATRIX.scale(1.0, -1.0, 1.0);
        INVENTORY_TRANSFORM_MATRIX.translate(0.0, 0.15, 0.0);
        INVENTORY_TRANSFORM_MATRIX.rotate(30.0F, 1.0F, 0.0F, 0.0F);
        INVENTORY_TRANSFORM_MATRIX.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        INVENTORY_TRANSFORM_MATRIX.scale(0.625F, 0.625F, 0.625F);

        INVENTORY_UNTRANSFORM_MATRIX.multiply(INVENTORY_TRANSFORM_MATRIX);
        INVENTORY_UNTRANSFORM_MATRIX.matrixStack.peek().invert();
    }

    public static void fitSlot(AxisAlignedBB bounds) {
        ComponentRenderHandler.fitSlot(bounds, 1.4);
    }

    public static void fitSlot(AxisAlignedBB bounds, double fitSize) {
        GlStateManager.translate(0.0, 0.45, 0.0);

        Vec3d untransformedCenter = bounds.getCenter();
        Point3f centerPoint = new Point3f((float) untransformedCenter.xCoord, (float) untransformedCenter.yCoord, (float) untransformedCenter.zCoord);
        INVENTORY_TRANSFORM_MATRIX.transform(centerPoint);

        Point3f origin = new Point3f(0, 0, 0);
        INVENTORY_TRANSFORM_MATRIX.transform(origin);

        double max = Math.max(bounds.maxX - bounds.minX, Math.max(bounds.maxY - bounds.minY, bounds.maxZ - bounds.minZ));
        GlStateManager.scale(fitSize / max, fitSize / max, fitSize / max);

        GlStateManager.translate(origin.x - untransformedCenter.xCoord, origin.y - untransformedCenter.yCoord, origin.z - untransformedCenter.zCoord);
    }

    public static void renderComponent(WearableComponent component, boolean smallArms) {
        ItemCameraTransforms.TransformType cameraTransform = WearablesClientHooks.getCameraTransform();
        WearableComponentType type = component.getType();
        ComponentRenderer renderer = RenderRegistry.getRenderer(type.getIdentifier());
        for (int layer = 0; layer < type.getLayerCount(); layer++) {
            ResourceLocation texture = renderer.getTexture(smallArms, layer);
            if (texture == null) {
                GlStateManager.disableTexture2D();
            } else {
                GlStateManager.enableTexture2D();
                MC.getTextureManager().bindTexture(texture);
            }
            WearableComponentModel model = renderer.getModel(smallArms);
            model.setModelAttributes(STATIC_MODEL);
            model.setOffsets(0.0F, 0.0F);
            model.swingProgress = 0.0F;
            model.leftArmPose = ArmPose.EMPTY;
            model.rightArmPose = ArmPose.EMPTY;
            float[] colour = renderer.adjustColour(WearableColourUtils.toRGBFloatArray(component.getColour(layer)), layer);
            GlStateManager.pushMatrix();
            GlStateManager.color(colour[0], colour[1], colour[2], 1.0F);
            GlStateManager.rotate(renderer.getItemRotationY() + 180.0F, 0.0F, 1.0F, 0.0F);
            float scale = renderer.getInventoryScale(cameraTransform);
            GlStateManager.scale(scale, scale, scale);
            model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            GlStateManager.popMatrix();
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
    }

    public static void renderComponentLayerHighlighted(WearableComponent component, int highlightLayer, float highlight) {
        ItemCameraTransforms.TransformType cameraTransform = WearablesClientHooks.getCameraTransform();
        WearableComponentType type = component.getType();
        ComponentRenderer renderer = RenderRegistry.getRenderer(type.getIdentifier());
        for (int layer = 0; layer < type.getLayerCount(); layer++) {
            ResourceLocation texture = renderer.getTexture(false, layer);
            if (texture == null) {
                GlStateManager.disableTexture2D();
            } else {
                GlStateManager.enableTexture2D();
                MC.getTextureManager().bindTexture(texture);
            }
            WearableComponentModel model = renderer.getModel(false);
            model.setModelAttributes(STATIC_MODEL);
            model.setOffsets(0.0F, 0.0F);
            model.swingProgress = 0.0F;
            model.leftArmPose = ArmPose.EMPTY;
            model.rightArmPose = ArmPose.EMPTY;
            float[] colour = renderer.adjustColour(WearableColourUtils.toRGBFloatArray(component.getColour(layer)), layer);
            GlStateManager.pushMatrix();
            if (layer == highlightLayer) {
                float brightness = highlight + 1.0F;
                GlStateManager.color(brightness * colour[0], brightness * colour[1], brightness * colour[2], 1.0F);
            } else {
                GlStateManager.color(colour[0], colour[1], colour[2], 1.0F);
            }
            GlStateManager.rotate(renderer.getItemRotationY() + 180.0F, 0.0F, 1.0F, 0.0F);
            float scale = renderer.getInventoryScale(cameraTransform);
            GlStateManager.scale(scale, scale, scale);
            model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            GlStateManager.popMatrix();
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
    }

    public static void renderSingleComponent(WearableComponent component) {
        WearableComponentType type = component.getType();
        ComponentRenderer renderer = RenderRegistry.getRenderer(type.getIdentifier());
        for (int layer = 0; layer < type.getLayerCount(); layer++) {
            ResourceLocation texture = renderer.getTexture(false, layer);
            if (MC.getRenderManager().isDebugBoundingBox()) {
                GlStateManager.disableTexture2D();
                RenderGlobal.drawSelectionBoundingBox(renderer.getBounds(), 0.0F, 0.0F, 1.0F, 1.0F);
            }
            if (texture == null) {
                GlStateManager.disableTexture2D();
            } else {
                GlStateManager.enableTexture2D();
                MC.getTextureManager().bindTexture(texture);
            }
            WearableComponentModel model = renderer.getModel(false);
            model.setModelAttributes(STATIC_MODEL);
            model.setOffsets(0.0F, 0.0F);
            model.swingProgress = 0.0F;
            model.leftArmPose = ArmPose.EMPTY;
            model.rightArmPose = ArmPose.EMPTY;
            float[] colour = renderer.adjustColour(WearableColourUtils.toRGBFloatArray(component.getColour(layer)), layer);
            GlStateManager.pushMatrix();
            GlStateManager.color(colour[0], colour[1], colour[2], 1.0F);
            float scale = renderer.getInventoryScale(ItemCameraTransforms.TransformType.GROUND);
            GlStateManager.rotate(renderer.getItemRotationY() + 180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(scale, scale, scale);
            model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            GlStateManager.popMatrix();
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
    }
}
