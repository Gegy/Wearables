package net.gegy1000.wearables.client.model.component.feet;

import com.google.common.collect.ImmutableList;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.ilexiconn.llibrary.client.util.Matrix;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;

public class Shoes1Model extends WearableComponentModel {
    private ModelRenderer rightBase;
    private ModelRenderer shape;
    private ModelRenderer shape_1;
    private ModelRenderer shape_2;
    private ModelRenderer shape_3;
    private ModelRenderer shape_4;
    private ModelRenderer shape_5;
    private ModelRenderer shape_6;
    private ModelRenderer shape_7;
    private ModelRenderer shape_8;
    private ModelRenderer leftBase;
    private ModelRenderer shape_9;
    private ModelRenderer shape_10;
    private ModelRenderer shape_11;
    private ModelRenderer shape_12;
    private ModelRenderer shape_13;
    private ModelRenderer shape_14;
    private ModelRenderer shape_15;
    private ModelRenderer shape_16;
    private ModelRenderer shape_17;

    public Shoes1Model() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.shape_12 = new ModelRenderer(this, 0, 16);
        this.shape_12.setRotationPoint(0.2999999999999998F, -0.8000000000000007F, -0.10000000000000003F);
        this.shape_12.addBox(1.0F, 0.0F, -4.0F, 1, 1, 6, 0.0F);
        this.leftBase = new ModelRenderer(this, 0, 8);
        this.leftBase.setRotationPoint(0.0F, 11.0F, 0.3F);
        this.leftBase.addBox(-2.5F, 0.0F, -4.0F, 5, 1, 6, 0.0F);
        this.shape_2 = new ModelRenderer(this, 0, 16);
        this.shape_2.setRotationPoint(-0.30000000000000027F, -0.8000000000000007F, -0.10000000000000003F);
        this.shape_2.addBox(-2.0F, 0.0F, -4.0F, 1, 1, 6, 0.0F);
        this.shape_13 = new ModelRenderer(this, 0, 16);
        this.shape_13.setRotationPoint(0.0F, -0.8000000000000007F, -3.6F);
        this.shape_13.addBox(-1.5F, 0.0F, -1.0F, 3, 1, 1, 0.0F);
        this.shape_16 = new ModelRenderer(this, 0, 16);
        this.shape_16.setRotationPoint(0.5999999999999999F, 0.0F, 0.20000000000000018F);
        this.shape_16.addBox(0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
        this.shape = new ModelRenderer(this, 0, 16);
        this.shape.setRotationPoint(0.0F, -0.8999999999999986F, -0.10000000000000003F);
        this.shape.addBox(-1.5F, 0.0F, -4.0F, 3, 1, 6, 0.0F);
        this.shape_1 = new ModelRenderer(this, 0, 13);
        this.shape_1.setRotationPoint(0.0F, 0.0F, -3.8F);
        this.shape_1.addBox(-2.1F, 0.0F, -1.0F, 4, 1, 1, 0.0F);
        this.shape_6 = new ModelRenderer(this, 0, 13);
        this.shape_6.setRotationPoint(0.5F, 0.0F, 0.2F);
        this.shape_6.addBox(0.9F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
        this.shape_15 = new ModelRenderer(this, 0, 13);
        this.shape_15.setRotationPoint(0.5F, 0.0F, 0.2F);
        this.shape_15.addBox(0.9F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
        this.shape_7 = new ModelRenderer(this, 0, 16);
        this.shape_7.setRotationPoint(0.5999999999999999F, 0.0F, 0.20000000000000018F);
        this.shape_7.addBox(0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
        this.shape_11 = new ModelRenderer(this, 0, 16);
        this.shape_11.setRotationPoint(-0.30000000000000027F, -0.8000000000000007F, -0.10000000000000003F);
        this.shape_11.addBox(-2.0F, 0.0F, -4.0F, 1, 1, 6, 0.0F);
        this.shape_3 = new ModelRenderer(this, 0, 16);
        this.shape_3.setRotationPoint(0.2999999999999998F, -0.8000000000000007F, -0.10000000000000003F);
        this.shape_3.addBox(1.0F, 0.0F, -4.0F, 1, 1, 6, 0.0F);
        this.rightBase = new ModelRenderer(this, 0, 8);
        this.rightBase.setRotationPoint(0.0F, 11.0F, 0.3F);
        this.rightBase.addBox(-2.5F, 0.0F, -4.0F, 5, 1, 6, 0.0F);
        this.shape_8 = new ModelRenderer(this, 0, 16);
        this.shape_8.setRotationPoint(-0.6000000000000001F, 0.0F, 0.20000000000000018F);
        this.shape_8.addBox(-1.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
        this.shape_17 = new ModelRenderer(this, 0, 16);
        this.shape_17.setRotationPoint(-0.6000000000000001F, 0.0F, 0.20000000000000018F);
        this.shape_17.addBox(-1.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
        this.shape_14 = new ModelRenderer(this, 0, 13);
        this.shape_14.setRotationPoint(-0.3F, 0.0F, 0.2F);
        this.shape_14.addBox(-2.1F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
        this.shape_4 = new ModelRenderer(this, 0, 16);
        this.shape_4.setRotationPoint(0.0F, -0.8000000000000007F, -3.6F);
        this.shape_4.addBox(-1.5F, 0.0F, -1.0F, 3, 1, 1, 0.0F);
        this.shape_5 = new ModelRenderer(this, 0, 13);
        this.shape_5.setRotationPoint(-0.3F, 0.0F, 0.2F);
        this.shape_5.addBox(-2.1F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
        this.shape_10 = new ModelRenderer(this, 0, 13);
        this.shape_10.setRotationPoint(0.0F, 0.0F, -3.8F);
        this.shape_10.addBox(-2.1F, 0.0F, -1.0F, 4, 1, 1, 0.0F);
        this.shape_9 = new ModelRenderer(this, 0, 16);
        this.shape_9.setRotationPoint(0.0F, -0.8999999999999986F, -0.10000000000000003F);
        this.shape_9.addBox(-1.5F, 0.0F, -4.0F, 3, 1, 6, 0.0F);
        this.leftBase.addChild(this.shape_12);
        this.rightBase.addChild(this.shape_2);
        this.leftBase.addChild(this.shape_13);
        this.shape_13.addChild(this.shape_16);
        this.rightBase.addChild(this.shape);
        this.rightBase.addChild(this.shape_1);
        this.shape_1.addChild(this.shape_6);
        this.shape_10.addChild(this.shape_15);
        this.shape_4.addChild(this.shape_7);
        this.leftBase.addChild(this.shape_11);
        this.rightBase.addChild(this.shape_3);
        this.shape_4.addChild(this.shape_8);
        this.shape_13.addChild(this.shape_17);
        this.shape_10.addChild(this.shape_14);
        this.rightBase.addChild(this.shape_4);
        this.shape_1.addChild(this.shape_5);
        this.leftBase.addChild(this.shape_10);
        this.leftBase.addChild(this.shape_9);
    }

    @Override
    public void renderComponent(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        this.renderParented(this.bipedRightLeg, this.rightBase, 1.1F, 0.0F, -0.05F, 0.0F, scale);
        this.renderParented(this.bipedLeftLeg, this.leftBase, 1.1F, 0.0F, -0.05F, 0.0F, scale);
    }

    @Override
    public void buildQuads(Matrix matrix, ImmutableList.Builder<BakedQuad> builder, VertexFormat format, TextureAtlasSprite sprite) {
        this.buildCuboidParented(this.bipedRightLeg, this.rightBase, 1.1F, 0.0F, -0.05F, 0.0F, matrix, builder, format, sprite);
        this.buildCuboidParented(this.bipedLeftLeg, this.leftBase, 1.1F, 0.0F, -0.05F, 0.0F, matrix, builder, format, sprite);
    }
}
