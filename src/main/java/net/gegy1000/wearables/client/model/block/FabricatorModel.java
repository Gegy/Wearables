package net.gegy1000.wearables.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class FabricatorModel extends ModelBase {
    public ModelRenderer base;
    public ModelRenderer shape1;
    public ModelRenderer shape1_1;
    public ModelRenderer shape1_2;
    public ModelRenderer shape1_3;
    public ModelRenderer shape1_4;
    public ModelRenderer shape9;
    public ModelRenderer shape1_5;
    public ModelRenderer shape1_6;
    public ModelRenderer shape1_7;
    public ModelRenderer shape1_8;
    public ModelRenderer shape1_9;
    public ModelRenderer shape9_1;
    public ModelRenderer shape12;
    public ModelRenderer shape9_2;
    public ModelRenderer shape9_3;
    public ModelRenderer shape9_4;
    public ModelRenderer shape16;
    public ModelRenderer shape16_1;
    public ModelRenderer shape16_2;
    public ModelRenderer shape16_3;

    public FabricatorModel() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.shape1_1 = new ModelRenderer(this, 0, 28);
        this.shape1_1.setRotationPoint(-6.0F, -12.0F, 0.0F);
        this.shape1_1.addBox(-2.0F, 0.0F, -8.0F, 2, 11, 16, 0.0F);
        this.shape9_1 = new ModelRenderer(this, 0, 12);
        this.shape9_1.setRotationPoint(0.6F, 0.0F, 0.0F);
        this.shape9_1.addBox(-1.0F, 0.0F, -1.5F, 4, 1, 3, 0.0F);
        this.shape16_2 = new ModelRenderer(this, 0, 37);
        this.shape16_2.setRotationPoint(-2.0F, -0.05F, -1.0F);
        this.shape16_2.addBox(-2.0F, 0.0F, -1.5F, 4, 1, 3, 0.0F);
        this.shape12 = new ModelRenderer(this, 0, 28);
        this.shape12.setRotationPoint(0.5F, -2.6F, 0.0F);
        this.shape12.addBox(0.0F, 0.0F, -1.5F, 3, 1, 3, 0.0F);
        this.shape16_1 = new ModelRenderer(this, 30, 19);
        this.shape16_1.setRotationPoint(2.0F, -1.1F, 3.0F);
        this.shape16_1.addBox(-2.0F, 0.0F, -1.5F, 5, 1, 3, 0.0F);
        this.setRotateAngle(this.shape16_1, 0.0F, 0.1884955592153876F, 0.0F);
        this.shape1_5 = new ModelRenderer(this, 20, 0);
        this.shape1_5.setRotationPoint(0.0F, -12.0F, 6.0F);
        this.shape1_5.addBox(-6.0F, 0.0F, 0.0F, 12, 11, 2, 0.0F);
        this.shape9_3 = new ModelRenderer(this, 0, 33);
        this.shape9_3.setRotationPoint(1.2F, -0.3F, 0.0F);
        this.shape9_3.addBox(-1.8F, 0.0F, -1.0F, 5, 1, 2, 0.0F);
        this.shape1_3 = new ModelRenderer(this, 60, 24);
        this.shape1_3.setRotationPoint(-7.0F, -12.0F, 0.0F);
        this.shape1_3.addBox(-1.0F, -1.0F, -7.0F, 1, 1, 15, 0.0F);
        this.setRotateAngle(this.shape1_3, 0.0F, 0.0F, -0.12566370614359174F);
        this.shape1_8 = new ModelRenderer(this, 0, 23);
        this.shape1_8.setRotationPoint(0.0F, -4.0F, -8.0F);
        this.shape1_8.addBox(3.0F, 0.0F, 0.0F, 3, 1, 2, 0.0F);
        this.shape1_2 = new ModelRenderer(this, 60, 24);
        this.shape1_2.setRotationPoint(7.0F, -12.0F, 0.0F);
        this.shape1_2.addBox(0.0F, -1.0F, -7.0F, 1, 1, 15, 0.0F);
        this.setRotateAngle(this.shape1_2, 0.0F, 0.0F, 0.12566370614359174F);
        this.shape9_4 = new ModelRenderer(this, 0, 20);
        this.shape9_4.setRotationPoint(1.9F, 1.0F, 0.0F);
        this.shape9_4.addBox(0.0F, 0.0F, -0.5F, 1, 1, 1, 0.0F);
        this.shape1_9 = new ModelRenderer(this, 21, 27);
        this.shape1_9.setRotationPoint(0.0F, -9.0F, 0.0F);
        this.shape1_9.addBox(-6.0F, -1.0F, -8.0F, 12, 2, 14, 0.0F);
        this.shape16 = new ModelRenderer(this, 0, 6);
        this.shape16.setRotationPoint(3.0F, -1.5F, -5.0F);
        this.shape16.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
        this.setRotateAngle(this.shape16, 0.0F, -0.3141592653589793F, 0.0F);
        this.shape1_6 = new ModelRenderer(this, 0, 23);
        this.shape1_6.setRotationPoint(0.0F, -4.0F, -8.0F);
        this.shape1_6.addBox(-6.0F, 0.0F, 0.0F, 3, 1, 2, 0.0F);
        this.shape16_3 = new ModelRenderer(this, 0, 0);
        this.shape16_3.setRotationPoint(-1.0F, -0.05F, 1.3F);
        this.shape16_3.addBox(-3.0F, 0.0F, -1.5F, 5, 1, 4, 0.0F);
        this.shape9 = new ModelRenderer(this, 12, 9);
        this.shape9.setRotationPoint(-5.0F, -10.7F, -0.2F);
        this.shape9.addBox(-0.4F, -2.0F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(this.shape9, 0.0F, 0.3769911184307752F, 0.0F);
        this.shape9_2 = new ModelRenderer(this, 7, 6);
        this.shape9_2.setRotationPoint(3.0F, 0.0F, 0.0F);
        this.shape9_2.addBox(0.0F, 0.0F, -1.0F, 1, 1, 2, 0.0F);
        this.shape1 = new ModelRenderer(this, 0, 28);
        this.shape1.mirror = true;
        this.shape1.setRotationPoint(6.0F, -12.0F, 0.0F);
        this.shape1.addBox(0.0F, 0.0F, -8.0F, 2, 11, 16, 0.0F);
        this.shape1_7 = new ModelRenderer(this, 0, 23);
        this.shape1_7.setRotationPoint(0.0F, -3.0F, -8.0F);
        this.shape1_7.addBox(-6.0F, 0.0F, 0.0F, 12, 2, 2, 0.0F);
        this.shape1_4 = new ModelRenderer(this, 0, 17);
        this.shape1_4.setRotationPoint(0.0F, -12.0F, 7.0F);
        this.shape1_4.addBox(-7.5F, -1.0F, 0.0F, 15, 1, 1, 0.0F);
        this.setRotateAngle(this.shape1_4, -0.12566370614359174F, 0.0F, 0.0F);
        this.base = new ModelRenderer(this, 33, 0);
        this.base.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.base.addBox(-8.0F, -1.0F, -8.0F, 16, 2, 16, 0.0F);
        this.base.addChild(this.shape1_1);
        this.shape9.addChild(this.shape9_1);
        this.shape16_1.addChild(this.shape16_2);
        this.shape9.addChild(this.shape12);
        this.shape1_9.addChild(this.shape16_1);
        this.base.addChild(this.shape1_5);
        this.shape12.addChild(this.shape9_3);
        this.base.addChild(this.shape1_3);
        this.base.addChild(this.shape1_8);
        this.base.addChild(this.shape1_2);
        this.shape9_3.addChild(this.shape9_4);
        this.base.addChild(this.shape1_9);
        this.shape1_9.addChild(this.shape16);
        this.base.addChild(this.shape1_6);
        this.shape16_2.addChild(this.shape16_3);
        this.base.addChild(this.shape9);
        this.shape9_1.addChild(this.shape9_2);
        this.base.addChild(this.shape1);
        this.base.addChild(this.shape1_7);
        this.base.addChild(this.shape1_4);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ticks, float yaw, float pitch, float scale) {
        this.base.render(scale);
    }

    private void setRotateAngle(ModelRenderer box, float x, float y, float z) {
        box.rotateAngleX = x;
        box.rotateAngleY = y;
        box.rotateAngleZ = z;
    }
}
