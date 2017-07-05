package net.gegy1000.wearables.client.model.component.head;

import com.google.common.collect.ImmutableList;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.ilexiconn.llibrary.client.util.Matrix;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;

public class TopHatModel extends WearableComponentModel {
    private ModelRenderer base;
    private ModelRenderer shape1;
    private ModelRenderer shape1_1;
    private ModelRenderer shape1_2;
    private ModelRenderer shape1_3;
    private ModelRenderer shape1_4;
    private ModelRenderer shape1_5;
    private ModelRenderer shape1_6;
    private ModelRenderer shape1_7;
    private ModelRenderer shape1_8;
    private ModelRenderer shape1_9;
    private ModelRenderer shape1_10;
    private ModelRenderer shape1_11;
    private ModelRenderer shape1_12;
    private ModelRenderer shape1_13;
    private ModelRenderer shape1_14;
    private ModelRenderer shape1_15;
    private ModelRenderer shape1_16;
    private ModelRenderer shape1_17;
    private ModelRenderer shape1_18;
    private ModelRenderer shape1_19;

    public TopHatModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.base = new ModelRenderer(this, 0, 0);
        this.base.setRotationPoint(-4.5F, -8.0F, -4.5F);
        this.base.addBox(0.0F, 0.0F, 0.0F, 2, 1, 9, 0.0F);
        this.shape1_7 = new ModelRenderer(this, 0, 18);
        this.shape1_7.setRotationPoint(7.0F, -3.9999999999999964F, 2.000000000000001F);
        this.shape1_7.addBox(0.0F, 0.0F, 0.0F, 1, 4, 5, 0.0F);
        this.shape1_11 = new ModelRenderer(this, 0, 14);
        this.shape1_11.setRotationPoint(1.5F, -6.969999999999999F, 1.5F);
        this.shape1_11.addBox(0.0F, 0.0F, 0.0F, 1, 7, 6, 0.0F);
        this.shape1_15 = new ModelRenderer(this, 0, 0);
        this.shape1_15.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1_15.addBox(-1.0F, -3.0F, 0.0F, 1, 3, 5, 0.0F);
        this.setRotateAngle(this.shape1_15, 0.0F, 0.0F, -0.06283185307179587F);
        this.shape1_10 = new ModelRenderer(this, 0, 19);
        this.shape1_10.setRotationPoint(1.5F, -6.969999999999999F, 1.5F);
        this.shape1_10.addBox(0.0F, 0.0F, 0.0F, 6, 7, 1, 0.0F);
        this.shape1_12 = new ModelRenderer(this, 0, 0);
        this.shape1_12.setRotationPoint(-0.5F, 0.0F, -0.5F);
        this.shape1_12.addBox(0.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);
        this.shape1_3 = new ModelRenderer(this, 0, 0);
        this.shape1_3.setRotationPoint(1.0F, 0.0F, 7.0F);
        this.shape1_3.addBox(0.0F, 0.0F, 0.0F, 7, 1, 2, 0.0F);
        this.shape1_5 = new ModelRenderer(this, 0, 22);
        this.shape1_5.setRotationPoint(2.0F, -4.0F, 1.0F);
        this.shape1_5.addBox(0.0F, 0.0F, 0.0F, 5, 4, 1, 0.0F);
        this.shape1_1 = new ModelRenderer(this, 0, 0);
        this.shape1_1.setRotationPoint(1.0F, 0.0F, 0.0F);
        this.shape1_1.addBox(0.0F, 0.0F, 0.0F, 7, 1, 2, 0.0F);
        this.shape1_2 = new ModelRenderer(this, 0, 0);
        this.shape1_2.setRotationPoint(7.0F, 0.0F, 0.0F);
        this.shape1_2.addBox(0.0F, 0.0F, 0.0F, 2, 1, 9, 0.0F);
        this.shape1 = new ModelRenderer(this, 0, 0);
        this.shape1.setRotationPoint(-0.5F, 0.0F, 0.5F);
        this.shape1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8, 0.0F);
        this.shape1_4 = new ModelRenderer(this, 0, 18);
        this.shape1_4.setRotationPoint(2.0F, -4.0F, 2.0F);
        this.shape1_4.addBox(-1.0F, 0.0F, 0.0F, 1, 4, 5, 0.0F);
        this.shape1_14 = new ModelRenderer(this, 0, 0);
        this.shape1_14.setRotationPoint(-0.5F, 0.0F, 1.5F);
        this.shape1_14.addBox(0.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);
        this.shape1_18 = new ModelRenderer(this, 0, 0);
        this.shape1_18.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1_18.addBox(0.0F, -3.0F, 0.0F, 1, 3, 5, 0.0F);
        this.setRotateAngle(this.shape1_18, 0.0F, 0.0F, 0.06283185307179587F);
        this.shape1_17 = new ModelRenderer(this, 0, 0);
        this.shape1_17.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1_17.addBox(0.0F, -3.0F, 0.0F, 5, 3, 1, 0.0F);
        this.setRotateAngle(this.shape1_17, -0.06283185307179587F, 0.0F, 0.0F);
        this.shape1_19 = new ModelRenderer(this, 0, 0);
        this.shape1_19.setRotationPoint(0.0F, -0.030000000000001137F, 0.0F);
        this.shape1_19.addBox(0.0F, 0.0F, 0.0F, 6, 1, 6, 0.0F);
        this.shape1_9 = new ModelRenderer(this, 0, 14);
        this.shape1_9.setRotationPoint(6.5F, -6.969999999999999F, 1.5F);
        this.shape1_9.addBox(0.0F, 0.0F, 0.0F, 1, 7, 6, 0.0F);
        this.shape1_6 = new ModelRenderer(this, 0, 22);
        this.shape1_6.setRotationPoint(2.0F, -4.0F, 7.0F);
        this.shape1_6.addBox(0.0F, 0.0F, 0.0F, 5, 4, 1, 0.0F);
        this.shape1_8 = new ModelRenderer(this, 0, 19);
        this.shape1_8.setRotationPoint(1.5F, -6.969999999999999F, 6.5F);
        this.shape1_8.addBox(0.0F, 0.0F, 0.0F, 6, 7, 1, 0.0F);
        this.shape1_13 = new ModelRenderer(this, 0, 0);
        this.shape1_13.setRotationPoint(1.5F, 0.0F, 0.5F);
        this.shape1_13.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8, 0.0F);
        this.shape1_16 = new ModelRenderer(this, 0, 0);
        this.shape1_16.setRotationPoint(0.0F, 0.0F, 1.0F);
        this.shape1_16.addBox(0.0F, -3.0F, -1.0F, 5, 3, 1, 0.0F);
        this.setRotateAngle(this.shape1_16, 0.06283185307179587F, 0.0F, 0.0F);
        this.base.addChild(this.shape1_7);
        this.base.addChild(this.shape1_11);
        this.shape1_4.addChild(this.shape1_15);
        this.base.addChild(this.shape1_10);
        this.shape1_1.addChild(this.shape1_12);
        this.base.addChild(this.shape1_3);
        this.base.addChild(this.shape1_5);
        this.base.addChild(this.shape1_1);
        this.base.addChild(this.shape1_2);
        this.base.addChild(this.shape1);
        this.base.addChild(this.shape1_4);
        this.shape1_3.addChild(this.shape1_14);
        this.shape1_7.addChild(this.shape1_18);
        this.shape1_6.addChild(this.shape1_17);
        this.shape1_11.addChild(this.shape1_19);
        this.base.addChild(this.shape1_9);
        this.base.addChild(this.shape1_6);
        this.base.addChild(this.shape1_8);
        this.shape1_2.addChild(this.shape1_13);
        this.shape1_5.addChild(this.shape1_16);
    }

    @Override
    public void renderComponent(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        this.renderParented(this.bipedHead, this.base, 1.0F, 0.0F, -0.0625F, 0.0F, scale);
    }

    @Override
    public void buildQuads(Matrix matrix, ImmutableList.Builder<BakedQuad> builder, VertexFormat format, TextureAtlasSprite sprite) {
        this.buildCuboidParented(this.bipedHead, this.base, 1.0F, 0.0F, -0.0625F, 0.0F, matrix, builder, format, sprite);
    }
}
