package net.gegy1000.wearables.client.model.component.feet;

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
public class FlippersModel extends WearableComponentModel {
    private ModelRenderer baseRight;
    private ModelRenderer shape16;
    private ModelRenderer shape16_1;
    private ModelRenderer shape18;
    private ModelRenderer shape21;
    private ModelRenderer shape21_1;
    private ModelRenderer shape21_2;
    private ModelRenderer shape21_3;
    private ModelRenderer shape26;
    private ModelRenderer shape26_1;
    private ModelRenderer baseLeft;
    private ModelRenderer shape16_2;
    private ModelRenderer shape16_3;
    private ModelRenderer shape18_1;
    private ModelRenderer shape21_4;
    private ModelRenderer shape21_5;
    private ModelRenderer shape21_6;
    private ModelRenderer shape21_7;
    private ModelRenderer shape26_2;
    private ModelRenderer shape26_3;

    public FlippersModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.shape18 = new ModelRenderer(this, 9, 12);
        this.shape18.setRotationPoint(0.0F, 0.1F, -4.7F);
        this.shape18.addBox(-1.0F, 0.0F, 0.0F, 2, 1, 3, 0.0F);
        this.setRotateAngle(this.shape18, 0.5410520681182421F, 0.0F, 0.0F);
        this.baseLeft = new ModelRenderer(this, 14, 0);
        this.baseLeft.setRotationPoint(-0.6F, 11.0F, 0.3F);
        this.baseLeft.addBox(-1.5F, 0.0F, -3.0F, 3, 1, 2, 0.0F);
        this.setRotateAngle(this.baseLeft, 0.0F, -0.3490658503988659F, 0.0F);
        this.shape21_4 = new ModelRenderer(this, 0, 11);
        this.shape21_4.setRotationPoint(1.1F, -0.15F, 0.1F);
        this.shape21_4.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
        this.setRotateAngle(this.shape21_4, 0.0F, 0.0F, -0.3490658503988659F);
        this.shape21_3 = new ModelRenderer(this, 0, 11);
        this.shape21_3.setRotationPoint(0.0F, 0.1F, 0.0F);
        this.shape21_3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
        this.setRotateAngle(this.shape21_3, 0.0F, -0.08726646259971647F, -0.45378560551852565F);
        this.shape21_7 = new ModelRenderer(this, 0, 11);
        this.shape21_7.setRotationPoint(0.0F, 0.1F, 0.0F);
        this.shape21_7.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
        this.setRotateAngle(this.shape21_7, 0.0F, -0.08726646259971647F, -0.45378560551852565F);
        this.shape18_1 = new ModelRenderer(this, 9, 12);
        this.shape18_1.setRotationPoint(0.0F, 0.1F, -4.7F);
        this.shape18_1.addBox(-1.0F, 0.0F, 0.0F, 2, 1, 3, 0.0F);
        this.setRotateAngle(this.shape18_1, 0.5410520681182421F, 0.0F, 0.0F);
        this.shape16_1 = new ModelRenderer(this, 0, 0);
        this.shape16_1.setRotationPoint(-1.45F, 0.0F, -10.0F);
        this.shape16_1.addBox(-1.5F, 0.0F, 0.0F, 3, 1, 8, 0.0F);
        this.setRotateAngle(this.shape16_1, 0.0F, 0.19198621771937624F, 0.0F);
        this.shape16 = new ModelRenderer(this, 0, 0);
        this.shape16.setRotationPoint(1.45F, 0.0F, -10.0F);
        this.shape16.addBox(-1.5F, 0.0F, 0.0F, 3, 1, 8, 0.0F);
        this.setRotateAngle(this.shape16, 0.0F, -0.19198621771937624F, 0.0F);
        this.shape21_6 = new ModelRenderer(this, 0, 11);
        this.shape21_6.setRotationPoint(-1.1F, -0.15F, 0.1F);
        this.shape21_6.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
        this.setRotateAngle(this.shape21_6, 0.0F, 0.0F, 0.3490658503988659F);
        this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.1F);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.shape21_5 = new ModelRenderer(this, 0, 11);
        this.shape21_5.setRotationPoint(0.0F, 0.1F, 0.0F);
        this.shape21_5.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
        this.setRotateAngle(this.shape21_5, 0.0F, 0.08726646259971647F, 0.45378560551852565F);
        this.shape21_2 = new ModelRenderer(this, 0, 11);
        this.shape21_2.setRotationPoint(-1.1F, -0.15F, 0.1F);
        this.shape21_2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
        this.setRotateAngle(this.shape21_2, 0.0F, 0.0F, 0.3490658503988659F);
        this.baseRight = new ModelRenderer(this, 14, 0);
        this.baseRight.setRotationPoint(0.6F, 11.0F, 0.3F);
        this.baseRight.addBox(-1.5F, 0.0F, -3.0F, 3, 1, 2, 0.0F);
        this.setRotateAngle(this.baseRight, 0.0F, 0.3490658503988659F, 0.0F);
        this.shape26_3 = new ModelRenderer(this, 9, 12);
        this.shape26_3.setRotationPoint(-1.5F, 0.9F, 0.0F);
        this.shape26_3.addBox(-1.0F, 0.0F, 0.0F, 2, 1, 3, 0.0F);
        this.setRotateAngle(this.shape26_3, 0.0F, 0.0F, -1.0471975511965976F);
        this.shape16_2 = new ModelRenderer(this, 0, 0);
        this.shape16_2.setRotationPoint(1.45F, 0.0F, -10.0F);
        this.shape16_2.addBox(-1.5F, 0.0F, 0.0F, 3, 1, 8, 0.0F);
        this.setRotateAngle(this.shape16_2, 0.0F, -0.19198621771937624F, 0.0F);
        this.shape16_3 = new ModelRenderer(this, 0, 0);
        this.shape16_3.setRotationPoint(-1.45F, 0.0F, -10.0F);
        this.shape16_3.addBox(-1.5F, 0.0F, 0.0F, 3, 1, 8, 0.0F);
        this.setRotateAngle(this.shape16_3, 0.0F, 0.19198621771937624F, 0.0F);
        this.shape21_1 = new ModelRenderer(this, 0, 11);
        this.shape21_1.setRotationPoint(0.0F, 0.1F, 0.0F);
        this.shape21_1.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
        this.setRotateAngle(this.shape21_1, 0.0F, 0.08726646259971647F, 0.45378560551852565F);
        this.shape21 = new ModelRenderer(this, 0, 11);
        this.shape21.setRotationPoint(1.1F, -0.15F, 0.1F);
        this.shape21.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
        this.setRotateAngle(this.shape21, 0.0F, 0.0F, -0.3490658503988659F);
        this.shape26_1 = new ModelRenderer(this, 10, 12);
        this.shape26_1.setRotationPoint(-1.7F, 0.7F, 0.0F);
        this.shape26_1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(this.shape26_1, 0.0F, 0.0F, -0.7740535232594852F);
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.1F);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.shape26_2 = new ModelRenderer(this, 10, 12);
        this.shape26_2.setRotationPoint(1.0F, 0.0F, 0.0F);
        this.shape26_2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(this.shape26_2, 0.0F, 0.0F, 0.7740535232594852F);
        this.shape26 = new ModelRenderer(this, 9, 12);
        this.shape26.setRotationPoint(1.5F, 0.9F, 0.0F);
        this.shape26.addBox(-1.0F, 0.0F, 0.0F, 2, 1, 3, 0.0F);
        this.setRotateAngle(this.shape26, 0.0F, 0.0F, 1.0471975511965976F);
        this.baseRight.addChild(this.shape18);
        this.shape16_2.addChild(this.shape21_4);
        this.shape16_1.addChild(this.shape21_3);
        this.shape16_3.addChild(this.shape21_7);
        this.baseLeft.addChild(this.shape18_1);
        this.baseRight.addChild(this.shape16_1);
        this.baseRight.addChild(this.shape16);
        this.shape16_3.addChild(this.shape21_6);
        this.shape16_2.addChild(this.shape21_5);
        this.shape16_1.addChild(this.shape21_2);
        this.shape18_1.addChild(this.shape26_3);
        this.baseLeft.addChild(this.shape16_2);
        this.baseLeft.addChild(this.shape16_3);
        this.shape16.addChild(this.shape21_1);
        this.shape16.addChild(this.shape21);
        this.shape18.addChild(this.shape26_1);
        this.shape18_1.addChild(this.shape26_2);
        this.shape18.addChild(this.shape26);
    }

    @Override
    public void renderComponent(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        this.renderParented(this.bipedRightLeg, this.baseRight, 1.5F, 0.0F, -0.35F, 0.1F, scale);
        this.renderParented(this.bipedLeftLeg, this.baseLeft, 1.5F, 0.0F, -0.35F, 0.1F, scale);
    }

    @Override
    public void buildQuads(Matrix matrix, ImmutableList.Builder<BakedQuad> builder, VertexFormat format, TextureAtlasSprite sprite, int layer) {
        this.buildCuboidParented(this.bipedRightLeg, this.baseRight, 1.5F, 0.0F, -0.35F, 0.1F, matrix, builder, format, sprite, layer);
        this.buildCuboidParented(this.bipedLeftLeg, this.baseLeft, 1.5F, 0.0F, -0.35F, 0.1F, matrix, builder, format, sprite, layer);
    }
}
