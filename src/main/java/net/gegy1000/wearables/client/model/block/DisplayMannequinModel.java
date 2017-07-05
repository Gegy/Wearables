package net.gegy1000.wearables.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class DisplayMannequinModel extends ModelBase {
    private ModelRenderer base;
    private ModelRenderer baseTopFront;
    private ModelRenderer baseTopBack;
    private ModelRenderer baseTopRight;
    private ModelRenderer baseTopLeft;
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer leftArm;
    public ModelRenderer leftLeg;
    public ModelRenderer rightLeg;
    public ModelRenderer rightArm;

    public DisplayMannequinModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.base = new ModelRenderer(this, 0, 46);
        this.base.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.base.addBox(-8.0F, 0.0F, -8.0F, 16, 2, 16, 0.0F);
        this.leftLeg = new ModelRenderer(this, 0, 16);
        this.leftLeg.mirror = true;
        this.leftLeg.setRotationPoint(1.9F, 10.0F, 0.1F);
        this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.baseTopRight = new ModelRenderer(this, 48, 0);
        this.baseTopRight.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.baseTopRight.addBox(-7.0F, 0.0F, -3.0F, 2, 2, 6, 0.0F);
        this.baseTopLeft = new ModelRenderer(this, 48, 0);
        this.baseTopLeft.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.baseTopLeft.addBox(5.0F, 0.0F, -3.0F, 2, 2, 6, 0.0F);
        this.body = new ModelRenderer(this, 16, 16);
        this.body.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.baseTopFront = new ModelRenderer(this, 0, 32);
        this.baseTopFront.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.baseTopFront.addBox(-7.0F, 0.0F, -5.0F, 14, 2, 2, 0.0F);
        this.baseTopBack = new ModelRenderer(this, 0, 32);
        this.baseTopBack.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.baseTopBack.addBox(-7.0F, 0.0F, 3.0F, 14, 2, 2, 0.0F);
        this.leftArm = new ModelRenderer(this, 40, 16);
        this.leftArm.setRotationPoint(5.0F, 0.0F, 0.0F);
        this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.rightLeg = new ModelRenderer(this, 0, 16);
        this.rightLeg.setRotationPoint(-1.9F, 10.0F, 0.1F);
        this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.rightArm = new ModelRenderer(this, 40, 16);
        this.rightArm.setRotationPoint(-5.0F, 0.0F, 0.0F);
        this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ticks, float yaw, float pitch, float scale) {
        this.base.render(scale);
        this.leftLeg.render(scale);
        this.baseTopRight.render(scale);
        this.baseTopLeft.render(scale);
        this.body.render(scale);
        this.head.render(scale);
        this.baseTopFront.render(scale);
        this.baseTopBack.render(scale);
        this.leftArm.render(scale);
        this.rightLeg.render(scale);
        this.rightArm.render(scale);
    }
}
