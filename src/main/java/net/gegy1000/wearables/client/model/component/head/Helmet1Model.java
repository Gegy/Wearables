package net.gegy1000.wearables.client.model.component.head;

import com.google.common.collect.ImmutableList;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.ilexiconn.llibrary.client.util.Matrix;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Helmet1Model extends WearableComponentModel {
    private ModelRenderer base;
    private ModelRenderer shape;
    private ModelRenderer shape_1;
    private ModelRenderer shape_2;
    private ModelRenderer shape_3;
    private ModelRenderer shape_4;
    private ModelRenderer shape_5;
    private ModelRenderer shape_6;
    private ModelRenderer shape_7;
    private ModelRenderer shape_8;
    private ModelRenderer shape_9;
    private ModelRenderer shape_10;
    private ModelRenderer shape_11;
    private ModelRenderer shape_12;
    private ModelRenderer shape_13;
    private ModelRenderer shape_14;
    private ModelRenderer shape_15;
    private ModelRenderer shape_16;
    private ModelRenderer shape_17;

    public Helmet1Model() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.shape_1 = new ModelRenderer(this, 0, 0);
        this.shape_1.setRotationPoint(-0.5F, 4.4F, -0.3F);
        this.shape_1.addBox(-4.0F, -8.0F, -4.0F, 9, 2, 1, 0.0F);
        this.shape_9 = new ModelRenderer(this, 0, 0);
        this.shape_9.setRotationPoint(-0.5000000000000001F, 5.9F, 0.0F);
        this.shape_9.addBox(-3.5F, -9.0F, 4.0F, 8, 5, 1, 0.0F);
        this.shape_4 = new ModelRenderer(this, 0, 0);
        this.shape_4.setRotationPoint(-0.5F, 6.4F, -2.3F);
        this.shape_4.addBox(-4.0F, -8.5F, 0.0F, 1, 3, 2, 0.0F);
        this.shape_13 = new ModelRenderer(this, 0, 0);
        this.shape_13.setRotationPoint(-0.5F, 6.4F, -3.3F);
        this.shape_13.addBox(4.0F, -8.5F, 1.0F, 1, 3, 2, 0.0F);
        this.base = new ModelRenderer(this, 0, 0);
        this.base.setRotationPoint(0.0F, -4.3F, -0.2F);
        this.base.addBox(-4.0F, -4.0F, -4.0F, 8, 1, 8, 0.0F);
        this.shape = new ModelRenderer(this, 0, 0);
        this.shape.setRotationPoint(-0.5000000000000001F, 4.4F, -0.3F);
        this.shape.addBox(4.0F, -8.0F, -4.0F, 1, 2, 9, 0.0F);
        this.shape_8 = new ModelRenderer(this, 0, 0);
        this.shape_8.setRotationPoint(-0.5F, 6.4F, -0.3F);
        this.shape_8.addBox(-4.0F, -8.0F, 4.0F, 9, 4, 1, 0.0F);
        this.shape_14 = new ModelRenderer(this, 0, 0);
        this.shape_14.setRotationPoint(0.5F, 4.9F, -0.3F);
        this.shape_14.addBox(3.0F, -7.5F, -3.0F, 1, 3, 1, 0.0F);
        this.shape_17 = new ModelRenderer(this, 0, 0);
        this.shape_17.setRotationPoint(-0.5F, 7.1F, 2.7F);
        this.shape_17.addBox(4.0F, -7.0F, 0.0F, 1, 2, 1, 0.0F);
        this.shape_6 = new ModelRenderer(this, 0, 0);
        this.shape_6.setRotationPoint(-0.5F, 6.4F, -0.3F);
        this.shape_6.addBox(-4.0F, -8.0F, 0.0F, 1, 2, 4, 0.0F);
        this.shape_12 = new ModelRenderer(this, 0, 0);
        this.shape_12.setRotationPoint(-0.5F, 6.4F, -0.3F);
        this.shape_12.addBox(4.0F, -8.0F, 0.0F, 1, 2, 4, 0.0F);
        this.shape_10 = new ModelRenderer(this, 0, 0);
        this.shape_10.setRotationPoint(-0.5000000000000001F, 4.4F, -0.3F);
        this.shape_10.addBox(-4.0F, -8.0F, 4.0F, 9, 2, 1, 0.0F);
        this.shape_11 = new ModelRenderer(this, 0, 0);
        this.shape_11.setRotationPoint(0.7F, 5.9F, 0.2F);
        this.shape_11.addBox(3.0F, -9.0F, 1.0F, 1, 4, 3, 0.0F);
        this.shape_3 = new ModelRenderer(this, 0, 0);
        this.shape_3.setRotationPoint(-0.5F, 4.9F, -0.3F);
        this.shape_3.addBox(-4.0F, -7.5F, -3.0F, 1, 3, 1, 0.0F);
        this.shape_15 = new ModelRenderer(this, 0, 0);
        this.shape_15.setRotationPoint(0.5F, 4.9F, -0.3F);
        this.shape_15.addBox(2.0F, -8.0F, -4.0F, 2, 2, 1, 0.0F);
        this.shape_7 = new ModelRenderer(this, 0, 0);
        this.shape_7.setRotationPoint(0.5F, 7.1F, 2.7F);
        this.shape_7.addBox(-5.0F, -7.0F, 0.0F, 1, 2, 1, 0.0F);
        this.shape_16 = new ModelRenderer(this, 0, 0);
        this.shape_16.setRotationPoint(-0.5F, 4.9F, -0.3F);
        this.shape_16.addBox(-4.0F, -8.0F, -4.0F, 2, 2, 1, 0.0F);
        this.shape_5 = new ModelRenderer(this, 0, 0);
        this.shape_5.setRotationPoint(-0.7F, 5.9F, 0.2F);
        this.shape_5.addBox(-4.0F, -9.0F, 1.0F, 1, 4, 3, 0.0F);
        this.shape_2 = new ModelRenderer(this, 0, 0);
        this.shape_2.setRotationPoint(-0.5000000000000001F, 4.4F, -0.3F);
        this.shape_2.addBox(-4.0F, -8.0F, -4.0F, 1, 2, 9, 0.0F);
        this.base.addChild(this.shape_1);
        this.base.addChild(this.shape_9);
        this.base.addChild(this.shape_4);
        this.base.addChild(this.shape_13);
        this.base.addChild(this.shape);
        this.base.addChild(this.shape_8);
        this.base.addChild(this.shape_14);
        this.base.addChild(this.shape_17);
        this.base.addChild(this.shape_6);
        this.base.addChild(this.shape_12);
        this.base.addChild(this.shape_10);
        this.base.addChild(this.shape_11);
        this.base.addChild(this.shape_3);
        this.base.addChild(this.shape_15);
        this.base.addChild(this.shape_7);
        this.base.addChild(this.shape_16);
        this.base.addChild(this.shape_5);
        this.base.addChild(this.shape_2);
    }

    @Override
    public void renderComponent(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        this.renderParented(this.bipedHead, this.base, 1.1F, 0.0F, 0.0F, 0.0F, scale);
    }

    @Override
    public void buildQuads(Matrix matrix, ImmutableList.Builder<BakedQuad> builder, VertexFormat format, TextureAtlasSprite sprite, int layer) {
        this.buildCuboidParented(this.bipedHead, this.base, 1.1F, 0.0F, 0.0F, 0.0F, matrix, builder, format, sprite, layer);
    }
}
