package net.gegy1000.wearables.client.model.component;

import com.google.common.collect.ImmutableList;
import net.gegy1000.wearables.client.ClientProxy;
import net.gegy1000.wearables.client.model.block.DisplayMannequinModel;
import net.gegy1000.wearables.client.model.block.HeadDisplayStandModel;
import net.ilexiconn.llibrary.client.util.Matrix;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import javax.vecmath.Point2f;
import javax.vecmath.Point3f;

@SideOnly(Side.CLIENT)
public abstract class WearableComponentModel extends ModelBiped implements IForgeRegistryEntry<WearableComponentModel> {
    private ResourceLocation registryName;

    private float offsetY;
    private float offsetZ;

    public void renderParented(ModelRenderer parent, ModelRenderer cuboid, float scale) {
        GlStateManager.pushMatrix();
        parent.postRender(scale);
        GlStateManager.translate(0.0F, -this.offsetY, this.offsetZ);
        cuboid.render(scale);
        GlStateManager.popMatrix();
    }

    public void renderParented(ModelRenderer parent, ModelRenderer cuboid, float renderScale, float offsetX, float offsetY, float offsetZ, float scale) {
        GlStateManager.pushMatrix();
        parent.postRender(scale);
        GlStateManager.translate(offsetX, offsetY - this.offsetY, offsetZ + this.offsetZ);
        GlStateManager.scale(renderScale, renderScale, renderScale);
        cuboid.render(scale);
        GlStateManager.popMatrix();
    }

    @Override
    public void render(@Nullable Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        if (entity instanceof EntityArmorStand) {
            GlStateManager.rotate(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
        }

        GlStateManager.pushMatrix();

        if (entity != null) {
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }
        }

        this.renderComponent(entity, limbSwing, limbSwingAmount, age, yaw, pitch, scale);
        GlStateManager.popMatrix();
    }

    public void renderMannequin(float scale) {
        this.renderComponent(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale);
    }

    protected abstract void renderComponent(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale);

    public void setOffsets(float offsetY, float offsetZ) {
        this.offsetY = offsetY / 2.0F;
        this.offsetZ = offsetZ / 2.0F;
    }

    public abstract void buildQuads(Matrix matrix, ImmutableList.Builder<BakedQuad> builder, VertexFormat format, TextureAtlasSprite sprite, int layer);

    protected void buildCuboidParented(ModelRenderer parent, ModelRenderer cuboid, Matrix matrix, ImmutableList.Builder<BakedQuad> builder, VertexFormat format, TextureAtlasSprite sprite, int layer) {
        matrix.push();
        this.postRender(parent, matrix);
        this.buildCuboid(cuboid, matrix, builder, format, sprite, layer);
        matrix.pop();
    }

    protected void buildCuboidParented(ModelRenderer parent, ModelRenderer cuboid, float renderScale, float offsetX, float offsetY, float offsetZ, Matrix matrix, ImmutableList.Builder<BakedQuad> builder, VertexFormat format, TextureAtlasSprite sprite, int layer) {
        matrix.push();
        this.postRender(parent, matrix);
        matrix.translate(offsetX / 0.0625F, offsetY / 0.0625F, offsetZ / 0.0625F);
        matrix.scale(renderScale, renderScale, renderScale);
        this.buildCuboid(cuboid, matrix, builder, format, sprite, layer);
        matrix.pop();
    }

    private void postRender(ModelRenderer parent, Matrix matrix) {
        if (parent.rotateAngleX == 0.0F && parent.rotateAngleY == 0.0F && parent.rotateAngleZ == 0.0F) {
            if (parent.rotationPointX != 0.0F || parent.rotationPointY != 0.0F || parent.rotationPointZ != 0.0F) {
                matrix.translate(parent.rotationPointX, parent.rotationPointY, parent.rotationPointZ);
            }
        } else {
            matrix.translate(parent.rotationPointX, parent.rotationPointY, parent.rotationPointZ);
            if (parent.rotateAngleZ != 0.0F) {
                matrix.rotate(Math.toDegrees(parent.rotateAngleZ), 0.0F, 0.0F, 1.0F);
            }
            if (parent.rotateAngleY != 0.0F) {
                matrix.rotate(Math.toDegrees(parent.rotateAngleY), 0.0F, 1.0F, 0.0F);
            }
            if (parent.rotateAngleX != 0.0F) {
                matrix.rotate(Math.toDegrees(parent.rotateAngleX), 1.0F, 0.0F, 0.0F);
            }
        }
    }

    protected void buildCuboid(ModelRenderer cuboid, Matrix matrix, ImmutableList.Builder<BakedQuad> builder, VertexFormat format, TextureAtlasSprite sprite, int layer) {
        matrix.push();
        matrix.translate(cuboid.rotationPointX, cuboid.rotationPointY, cuboid.rotationPointZ);

        if (cuboid.rotateAngleZ != 0) {
            matrix.rotate(Math.toDegrees(cuboid.rotateAngleZ), 0, 0, 1);
        }
        if (cuboid.rotateAngleY != 0) {
            matrix.rotate(Math.toDegrees(cuboid.rotateAngleY), 0, 1, 0);
        }
        if (cuboid.rotateAngleX != 0) {
            matrix.rotate(Math.toDegrees(cuboid.rotateAngleX), 1, 0, 0);
        }

        for (ModelBox box : cuboid.cubeList) {
            this.buildBox(box, builder, matrix, format, sprite, layer);
        }

        if (cuboid.childModels != null) {
            for (ModelRenderer child : cuboid.childModels) {
                this.buildCuboid(child, matrix, builder, format, sprite, layer);
            }
        }

        matrix.pop();
    }

    private void buildBox(ModelBox box, ImmutableList.Builder<BakedQuad> builder, Matrix matrix, VertexFormat format, TextureAtlasSprite sprite, int layer) {
        TexturedQuad[] quads = ClientProxy.getQuadList(box);

        for (TexturedQuad quad : quads) {
            this.buildQuad(builder, matrix, format, quad, sprite, layer);
        }
    }

    private void buildQuad(ImmutableList.Builder<BakedQuad> builder, Matrix matrix, VertexFormat format, TexturedQuad quad, TextureAtlasSprite sprite, int layer) {
        Vec3d[] transformedVertices = new Vec3d[quad.nVertices];

        for (int i = 0; i < transformedVertices.length; i++) {
            PositionTextureVertex vertex = quad.vertexPositions[i];
            Point3f point = new Point3f((float) vertex.vector3D.x, (float) vertex.vector3D.y, (float) vertex.vector3D.z);
            matrix.transform(point);
            transformedVertices[i] = new Vec3d(point.x, point.y, point.z);
        }

        Vec3d vec0 = transformedVertices[1].subtractReverse(transformedVertices[0]);
        Vec3d vec1 = transformedVertices[1].subtractReverse(transformedVertices[2]);
        Vec3d normal = vec1.crossProduct(vec0).normalize();

        UnpackedBakedQuad.Builder quadBuilder = new UnpackedBakedQuad.Builder(format);
        EnumFacing quadFacing = EnumFacing.getFacingFromVector((float) normal.x, (float) normal.y, (float) normal.z);
        quadBuilder.setQuadOrientation(quadFacing);
        quadBuilder.setTexture(sprite);
        quadBuilder.setQuadTint(layer);

        for (int i = 0; i < quad.nVertices; i++) {
            PositionTextureVertex vertex = quad.vertexPositions[i];
            Point2f uv = new Point2f(sprite.getInterpolatedU(vertex.texturePositionX * 16), sprite.getInterpolatedV(vertex.texturePositionY * 16));
            Vec3d transformedVertex = transformedVertices[i];

            this.putVertexData(quadBuilder, format, transformedVertex, normal, uv);
        }

        builder.add(quadBuilder.build());
    }

    private void putVertexData(UnpackedBakedQuad.Builder builder, VertexFormat format, Vec3d vertex, Vec3d normal, Point2f uv) {
        for (int e = 0; e < format.getElementCount(); e++) {
            switch (format.getElement(e).getUsage()) {
                case POSITION:
                    builder.put(e, (float) vertex.x, (float) vertex.y, (float) vertex.z);
                    break;
                case COLOR:
                    builder.put(e, 1, 1, 1, 1);
                    break;
                case UV:
                    builder.put(e, uv.x, uv.y, 0, 1);
                    break;
                case NORMAL:
                    builder.put(e, (float) normal.x, (float) normal.y, (float) normal.z);
                    break;
                default:
                    builder.put(e);
            }
        }
    }

    @Override
    public void setModelAttributes(ModelBase model) {
        super.setModelAttributes(model);
        if (model instanceof ModelBiped) {
            ModelBiped biped = (ModelBiped) model;
            copyModelAngles(biped.bipedRightArm, this.bipedRightArm);
            copyModelAngles(biped.bipedLeftArm, this.bipedLeftArm);
            copyModelAngles(biped.bipedBody, this.bipedBody);
            copyModelAngles(biped.bipedHead, this.bipedHead);
            copyModelAngles(biped.bipedRightLeg, this.bipedRightLeg);
            copyModelAngles(biped.bipedLeftLeg, this.bipedLeftLeg);
        } else if (model instanceof DisplayMannequinModel) {
            DisplayMannequinModel mannequin = (DisplayMannequinModel) model;
            copyModelAngles(mannequin.rightArm, this.bipedRightArm);
            copyModelAngles(mannequin.leftArm, this.bipedLeftArm);
            copyModelAngles(mannequin.body, this.bipedBody);
            copyModelAngles(mannequin.head, this.bipedHead);
            copyModelAngles(mannequin.rightLeg, this.bipedRightLeg);
            copyModelAngles(mannequin.leftLeg, this.bipedLeftLeg);
        } else if (model instanceof HeadDisplayStandModel) {
            HeadDisplayStandModel mannequin = (HeadDisplayStandModel) model;
            copyModelAngles(mannequin.head, this.bipedHead);
        }
    }

    protected void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    protected float calculateRotation(float speed, float degree, boolean invert, float offset, float weight, float f, float f1) {
        float rotation = (MathHelper.cos(f * speed + offset) * degree * f1) + (weight * f1);
        return invert ? -rotation : rotation;
    }

    protected float calculateChainRotation(float speed, float degree, float swing, float swingAmount, float offset, int boxIndex) {
        return MathHelper.cos(swing * speed + offset * boxIndex) * swingAmount * degree;
    }

    protected float calculateChainOffset(double rootOffset, ModelRenderer... boxes) {
        return (float) ((rootOffset * Math.PI) / (2 * boxes.length));
    }

    @Override
    public WearableComponentModel setRegistryName(ResourceLocation name) {
        this.registryName = name;
        return this;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return this.registryName;
    }

    @Override
    public Class<WearableComponentModel> getRegistryType() {
        return WearableComponentModel.class;
    }
}
