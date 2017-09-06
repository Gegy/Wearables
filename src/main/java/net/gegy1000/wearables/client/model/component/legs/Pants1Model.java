package net.gegy1000.wearables.client.model.component.legs;

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
public class Pants1Model extends WearableComponentModel {
    private ModelRenderer baseRight;
    private ModelRenderer shape;
    private ModelRenderer shape_1;
    private ModelRenderer shape_2;
    private ModelRenderer shape_3;
    private ModelRenderer baseLeft;
    private ModelRenderer shape_4;
    private ModelRenderer shape_5;
    private ModelRenderer shape_6;
    private ModelRenderer shape_7;

    public Pants1Model() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.shape = new ModelRenderer(this, 0, 0);
        this.shape.setRotationPoint(0.0F, 0.5F, -0.7F);
        this.shape.addBox(-1.5F, 0.0F, 2.5F, 3, 2, 1, 0.0F);
        this.shape_6 = new ModelRenderer(this, 0, 0);
        this.shape_6.setRotationPoint(0.0F, 1.3F, 0.1F);
        this.shape_6.addBox(-1.0F, 0.0F, -2.5F, 2, 1, 1, 0.0F);
        this.shape_7 = new ModelRenderer(this, 0, 0);
        this.shape_7.setRotationPoint(0.0F, 1.3F, -0.1F);
        this.shape_7.addBox(-1.0F, 0.0F, 2.5F, 2, 1, 1, 0.0F);
        this.shape_2 = new ModelRenderer(this, 0, 0);
        this.shape_2.setRotationPoint(0.0F, 1.3F, -0.1F);
        this.shape_2.addBox(-1.0F, 0.0F, 2.5F, 2, 1, 1, 0.0F);
        this.baseRight = new ModelRenderer(this, 0, 3);
        this.baseRight.setRotationPoint(0.0F, -0.09999999999999964F, 0.0F);
        this.baseRight.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
        this.shape_4 = new ModelRenderer(this, 0, 0);
        this.shape_4.setRotationPoint(0.5F, 1.1F, -0.3F);
        this.shape_4.addBox(-1.5F, 0.0F, -2.5F, 3, 2, 1, 0.0F);
        this.setRotateAngle(this.shape_4, 0.0F, 0.0F, 0.06283185307179587F);
        this.shape_3 = new ModelRenderer(this, 0, 0);
        this.shape_3.setRotationPoint(0.0F, 1.3F, 0.1F);
        this.shape_3.addBox(-1.0F, 0.0F, -2.5F, 2, 1, 1, 0.0F);
        this.shape_1 = new ModelRenderer(this, 0, 0);
        this.shape_1.setRotationPoint(-0.5F, 1.1F, -0.3F);
        this.shape_1.addBox(-1.5F, 0.0F, -2.5F, 3, 2, 1, 0.0F);
        this.setRotateAngle(this.shape_1, 0.0F, 0.0F, -0.06283185307179587F);
        this.baseLeft = new ModelRenderer(this, 0, 3);
        this.baseLeft.setRotationPoint(0.20000000000000018F, -0.09999999999999964F, 0.0F);
        this.baseLeft.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
        this.shape_5 = new ModelRenderer(this, 0, 0);
        this.shape_5.setRotationPoint(0.0F, 0.5F, -0.7F);
        this.shape_5.addBox(-1.5F, 0.0F, 2.5F, 3, 2, 1, 0.0F);
        this.baseRight.addChild(this.shape);
        this.shape_4.addChild(this.shape_6);
        this.shape_5.addChild(this.shape_7);
        this.shape.addChild(this.shape_2);
        this.baseLeft.addChild(this.shape_4);
        this.shape_1.addChild(this.shape_3);
        this.baseRight.addChild(this.shape_1);
        this.baseLeft.addChild(this.shape_5);
    }

    @Override
    public void renderComponent(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        this.renderParented(this.bipedRightLeg, this.baseRight, scale);
        this.renderParented(this.bipedLeftLeg, this.baseLeft, scale);
    }

    @Override
    public void buildQuads(Matrix matrix, ImmutableList.Builder<BakedQuad> builder, VertexFormat format, TextureAtlasSprite sprite, int layer) {
        this.buildCuboidParented(this.bipedRightLeg, this.baseRight, matrix, builder, format, sprite, layer);
        this.buildCuboidParented(this.bipedLeftLeg, this.baseLeft, matrix, builder, format, sprite, layer);
    }
}
