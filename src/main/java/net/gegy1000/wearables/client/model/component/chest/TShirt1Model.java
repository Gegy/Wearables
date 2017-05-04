package net.gegy1000.wearables.client.model.component.chest;

import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class TShirt1Model extends WearableComponentModel {
    public ModelRenderer armRight;
    public ModelRenderer armLeft;
    public ModelRenderer chest;
    public ModelRenderer shape;
    public ModelRenderer shape_1;
    public ModelRenderer shape_2;
    public ModelRenderer shape_3;
    public ModelRenderer shape_4;
    public ModelRenderer shape_5;
    public ModelRenderer shape_6;

    public TShirt1Model() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.shape_6 = new ModelRenderer(this, 0, 0);
        this.shape_6.setRotationPoint(2.0F, 0.0F, 0.0F);
        this.shape_6.addBox(0.0F, 0.0F, -3.0F, 3, 5, 1, 0.0F);
        this.setRotateAngle(this.shape_6, 0.0F, 0.0F, 0.5654866776461628F);
        this.shape_1 = new ModelRenderer(this, 0, 0);
        this.shape_1.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.shape_1.addBox(-2.0F, 0.0F, -3.0F, 4, 8, 1, 0.0F);
        this.shape_3 = new ModelRenderer(this, 0, 0);
        this.shape_3.setRotationPoint(-2.0F, 0.0F, 0.0F);
        this.shape_3.addBox(-3.0F, 0.0F, -3.0F, 3, 5, 1, 0.0F);
        this.setRotateAngle(this.shape_3, 0.0F, 0.0F, -0.6283185307179586F);
        this.chest = new ModelRenderer(this, 0, 0);
        this.chest.setRotationPoint(0.0F, -0.2F, 0.0F);
        this.chest.addBox(-5.0F, 0.0F, 2.0F, 10, 12, 1, 0.0F);
        this.shape_5 = new ModelRenderer(this, 0, 0);
        this.shape_5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape_5.addBox(4.0F, 0.0F, -2.0F, 1, 12, 4, 0.0F);
        this.shape = new ModelRenderer(this, 0, 0);
        this.shape.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape.addBox(-5.0F, 0.0F, -3.0F, 3, 12, 1, 0.0F);
        this.armRight = new ModelRenderer(this, 0, 0);
        this.armRight.setRotationPoint(0.0F, -0.10000000000000009F, 0.0F);
        this.armRight.addBox(-3.5F, -2.0F, -2.5F, 5, 4, 5, 0.0F);
        this.shape_2 = new ModelRenderer(this, 0, 0);
        this.shape_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape_2.addBox(2.0F, 0.0F, -3.0F, 3, 12, 1, 0.0F);
        this.shape_4 = new ModelRenderer(this, 0, 0);
        this.shape_4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape_4.addBox(-5.0F, 0.0F, -2.0F, 1, 12, 4, 0.0F);
        this.armLeft = new ModelRenderer(this, 0, 0);
        this.armLeft.setRotationPoint(0.0F, -0.10000000000000009F, 0.0F);
        this.armLeft.addBox(-1.5F, -2.0F, -2.5F, 5, 4, 5, 0.0F);
        this.shape_2.addChild(this.shape_6);
        this.shape.addChild(this.shape_1);
        this.shape.addChild(this.shape_3);
        this.shape.addChild(this.shape_5);
        this.chest.addChild(this.shape);
        this.shape.addChild(this.shape_2);
        this.shape.addChild(this.shape_4);
    }

    @Override
    public void renderComponent(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        this.renderParented(this.bipedBody, this.chest, 0.95F, 0.0F, 0.025F, 0.0F, scale);
        this.renderParented(this.bipedLeftArm, this.armLeft, 1.0F, 0.0F, -0.01F, 0.0F, scale);
        this.renderParented(this.bipedRightArm, this.armRight, 1.0F, 0.0F, -0.01F, 0.0F, scale);
    }
}
