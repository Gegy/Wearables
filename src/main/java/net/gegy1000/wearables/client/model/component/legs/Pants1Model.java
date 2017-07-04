package net.gegy1000.wearables.client.model.component.legs;

import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class Pants1Model extends WearableComponentModel {
    public ModelRenderer baseRight;
    public ModelRenderer shape;
    public ModelRenderer shape_1;
    public ModelRenderer shape_2;
    public ModelRenderer shape_3;
    public ModelRenderer baseLeft;
    public ModelRenderer shape_4;
    public ModelRenderer shape_5;
    public ModelRenderer shape_6;
    public ModelRenderer shape_7;

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
}
