package net.gegy1000.wearables.client.render;

import com.google.common.collect.ImmutableList;
import net.gegy1000.wearables.client.model.component.ComponentModelRegistry;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.gegy1000.wearables.server.util.WearableColourUtils;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.ilexiconn.llibrary.client.util.Matrix;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Point3f;
import java.util.function.Function;

@SideOnly(Side.CLIENT)
public class ComponentRenderHandler {
    private static final Minecraft MC = Minecraft.getMinecraft();
    private static final Point3f INVENTORY_ORIGIN = new Point3f(0, 0, 0);

    private static final ModelBiped STATIC_MODEL = new ModelBiped();

    static {
        Matrix inventoryTransformMatrix = new Matrix();

        inventoryTransformMatrix.translate(0.0, -0.15, 0.0);
        inventoryTransformMatrix.rotate(30.0F, 1.0F, 0.0F, 0.0F);
        inventoryTransformMatrix.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        inventoryTransformMatrix.scale(0.625F, 0.625F, 0.625F);

        inventoryTransformMatrix.transform(INVENTORY_ORIGIN);
    }

    public static void buildComponentQuads(WearableComponentType type, Matrix matrix, ImmutableList.Builder<BakedQuad> componentBuilder, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> textures, int startLayer) {
        matrix.push();

        matrix.scale(-1.0F, -1.0F, 1.0F);

        ComponentRenderHandler.fitSlot(type.getBounds(), matrix);

        matrix.scale(0.0625F, 0.0625F, 0.0625F);

        matrix.rotate(180.0F, 0.0F, 1.0F, 0.0F);

        float[] rotation = type.getInventoryRotation();
        if (rotation != null) {
            matrix.rotate(rotation[2], 0.0F, 0.0F, 1.0F);
            matrix.rotate(rotation[1], 0.0F, 1.0F, 0.0F);
            matrix.rotate(rotation[0], 1.0F, 0.0F, 0.0F);
        }

        WearableComponentType.Layer[] layers = type.getLayers(false);

        for (int layerIndex = 0; layerIndex < layers.length; layerIndex++) {
            WearableComponentType.Layer layer = layers[layerIndex];

            WearableComponentModel model = ComponentModelRegistry.getRegistry().getValue(layer.getModel());
            if (model == null) {
                throw new RuntimeException("Received null model for component " + type.getRegistryName());
            }

            TextureAtlasSprite sprite = textures.apply(layer.getTexture() != null ? layer.getTexture() : new ResourceLocation("missingno"));

            model.buildQuads(matrix, componentBuilder, format, sprite, startLayer | layerIndex);
        }

        matrix.pop();
    }

    public static void fitSlot(AxisAlignedBB bounds, Matrix matrix) {
        ComponentRenderHandler.fitSlot(bounds, matrix, 1.4);
    }

    public static void fitSlot(AxisAlignedBB bounds, Matrix matrix, double fitSize) {
        matrix.translate(0.0, 0.45, 0.0);

        double max = Math.max(bounds.maxX - bounds.minX, Math.max(bounds.maxY - bounds.minY, bounds.maxZ - bounds.minZ));
        matrix.scale(fitSize / max, fitSize / max, fitSize / max);

        Vec3d untransformedCenter = bounds.getCenter();
        matrix.translate(INVENTORY_ORIGIN.x - untransformedCenter.x, INVENTORY_ORIGIN.y - untransformedCenter.y, INVENTORY_ORIGIN.z - untransformedCenter.z);
    }

    public static void fitSlot(AxisAlignedBB bounds) {
        ComponentRenderHandler.fitSlot(bounds, 1.4);
    }

    public static void fitSlot(AxisAlignedBB bounds, double fitSize) {
        GlStateManager.translate(0.0, 0.45, 0.0);

        double max = Math.max(bounds.maxX - bounds.minX, Math.max(bounds.maxY - bounds.minY, bounds.maxZ - bounds.minZ));
        GlStateManager.scale(fitSize / max, fitSize / max, fitSize / max);

        Vec3d untransformedCenter = bounds.getCenter();
        GlStateManager.translate(INVENTORY_ORIGIN.x - untransformedCenter.x, INVENTORY_ORIGIN.y - untransformedCenter.y, INVENTORY_ORIGIN.z - untransformedCenter.z);
    }

    public static void renderComponent(WearableComponent component, boolean smallArms) {
        WearableComponentType type = component.getType();
        WearableComponentType.Layer[] layers = type.getLayers(smallArms);
        for (int layerIndex = 0; layerIndex < layers.length; layerIndex++) {
            WearableComponentType.Layer layer = layers[layerIndex];
            ResourceLocation texture = layer.getQualifiedTexture();
            if (texture == null) {
                GlStateManager.disableTexture2D();
            } else {
                GlStateManager.enableTexture2D();
                MC.getTextureManager().bindTexture(texture);
            }
            WearableComponentModel model = ComponentModelRegistry.getRegistry().getValue(layer.getModel());
            if (model != null) {
                model.setModelAttributes(STATIC_MODEL);
                model.setOffsets(0.0F, 0.0F);
                model.swingProgress = 0.0F;
                model.leftArmPose = ArmPose.EMPTY;
                model.rightArmPose = ArmPose.EMPTY;
                GlStateManager.pushMatrix();
                float[] colour = WearableColourUtils.toRGBFloatArray(component.getColour(layerIndex));
                float[] rotation = type.getInventoryRotation();
                GlStateManager.color(colour[0], colour[1], colour[2], 1.0F);
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                if (rotation != null) {
                    GlStateManager.rotate(rotation[2], 0.0F, 0.0F, 1.0F);
                    GlStateManager.rotate(rotation[1], 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(rotation[0], 1.0F, 0.0F, 0.0F);
                }
                model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                GlStateManager.popMatrix();
            }
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
    }

    public static void renderComponentLayerHighlighted(WearableComponent component, int highlightLayer, float highlight) {
        WearableComponentType type = component.getType();
        WearableComponentType.Layer[] layers = type.getLayers(false);
        for (int layerIndex = 0; layerIndex < layers.length; layerIndex++) {
            WearableComponentType.Layer layer = layers[layerIndex];
            ResourceLocation texture = layer.getQualifiedTexture();
            if (texture == null) {
                GlStateManager.disableTexture2D();
            } else {
                GlStateManager.enableTexture2D();
                MC.getTextureManager().bindTexture(texture);
            }
            WearableComponentModel model = ComponentModelRegistry.getRegistry().getValue(layer.getModel());
            if (model != null) {
                model.setModelAttributes(STATIC_MODEL);
                model.setOffsets(0.0F, 0.0F);
                model.swingProgress = 0.0F;
                model.leftArmPose = ArmPose.EMPTY;
                model.rightArmPose = ArmPose.EMPTY;
                GlStateManager.pushMatrix();
                float[] colour = WearableColourUtils.toRGBFloatArray(component.getColour(layerIndex));
                float[] rotation = type.getInventoryRotation();
                if (layerIndex == highlightLayer) {
                    float brightness = highlight + 1.0F;
                    GlStateManager.color(brightness * colour[0], brightness * colour[1], brightness * colour[2], 1.0F);
                } else {
                    GlStateManager.color(colour[0], colour[1], colour[2], 1.0F);
                }
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                if (rotation != null) {
                    GlStateManager.rotate(rotation[2], 0.0F, 0.0F, 1.0F);
                    GlStateManager.rotate(rotation[1], 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(rotation[0], 1.0F, 0.0F, 0.0F);
                }
                model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                GlStateManager.popMatrix();
            }
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
    }

    public static void renderSingleComponent(WearableComponent component) {
        WearableComponentType type = component.getType();
        WearableComponentType.Layer[] layers = type.getLayers(false);
        for (int layerIndex = 0; layerIndex < layers.length; layerIndex++) {
            WearableComponentType.Layer layer = layers[layerIndex];
            ResourceLocation texture = layer.getQualifiedTexture();
            if (MC.getRenderManager().isDebugBoundingBox()) {
                GlStateManager.disableTexture2D();
                RenderGlobal.drawSelectionBoundingBox(type.getBounds(), 0.0F, 0.0F, 1.0F, 1.0F);
            }
            if (texture == null) {
                GlStateManager.disableTexture2D();
            } else {
                GlStateManager.enableTexture2D();
                MC.getTextureManager().bindTexture(texture);
            }
            WearableComponentModel model = ComponentModelRegistry.getRegistry().getValue(layer.getModel());
            if (model != null) {
                model.setModelAttributes(STATIC_MODEL);
                model.setOffsets(0.0F, 0.0F);
                model.swingProgress = 0.0F;
                model.leftArmPose = ArmPose.EMPTY;
                model.rightArmPose = ArmPose.EMPTY;
                GlStateManager.pushMatrix();
                float[] rotation = type.getInventoryRotation();
                float[] colour = WearableColourUtils.toRGBFloatArray(component.getColour(layerIndex));
                GlStateManager.color(colour[0], colour[1], colour[2], 1.0F);
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                if (rotation != null) {
                    GlStateManager.rotate(rotation[2], 0.0F, 0.0F, 1.0F);
                    GlStateManager.rotate(rotation[1], 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(rotation[0], 1.0F, 0.0F, 0.0F);
                }
                model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                GlStateManager.popMatrix();
            }
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
    }
}
