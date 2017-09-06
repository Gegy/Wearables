package net.gegy1000.wearables.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AssemblerModel extends ModelBase {
    private ModelRenderer base;
    private ModelRenderer body;
    private ModelRenderer head;
    private ModelRenderer leftArm;
    private ModelRenderer rightArm;
    private ModelRenderer rightLeg;
    private ModelRenderer leftLeg;
    private ModelRenderer baseTop;
    private ModelRenderer shape15;
    private ModelRenderer field_78116_c;
    private ModelRenderer shape15_1;
    private ModelRenderer shape20;
    private ModelRenderer shape20_1;
    private ModelRenderer shape20_2;
    private ModelRenderer shape20_3;
    private ModelRenderer shape20_4;
    private ModelRenderer shape20_5;
    private ModelRenderer shape20_6;
    private ModelRenderer shape20_7;
    private ModelRenderer shape20_8;

    public AssemblerModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.shape20_5 = new ModelRenderer(this, 40, 16);
        this.shape20_5.setRotationPoint(0.0F, 0.0F, -3.0F);
        this.shape20_5.addBox(-0.5F, 0.0F, -3.0F, 1, 0, 3, 0.0F);
        this.setRotateAngle(this.shape20_5, 1.4451326206513047F, -0.12566370614359174F, 0.5026548245743669F);
        this.rightArm = new ModelRenderer(this, 40, 0);
        this.rightArm.setRotationPoint(-4.0F, 0.0F, 0.0F);
        this.rightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(this.rightArm, 0.0F, 0.0F, 0.17453292519943295F);
        this.leftArm = new ModelRenderer(this, 40, 0);
        this.leftArm.setRotationPoint(4.0F, 0.0F, 0.0F);
        this.leftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(this.leftArm, 0.0F, 0.0F, -0.17453292519943295F);
        this.shape15 = new ModelRenderer(this, 9, 14);
        this.shape15.setRotationPoint(0.0F, 0.5F, 0.0F);
        this.shape15.addBox(-3.0F, 0.0F, -1.5F, 6, 1, 3, 0.0F);
        this.body = new ModelRenderer(this, 30, 5);
        this.body.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.body.addBox(-1.0F, 1.0F, -1.0F, 2, 11, 2, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.head.addBox(-2.5F, -6.0F, -2.5F, 5, 6, 5, 0.0F);
        this.field_78116_c = new ModelRenderer(this, 21, 5);
        this.field_78116_c.setRotationPoint(0.0F, -0.5F, 0.0F);
        this.field_78116_c.addBox(-1.0F, -5.0F, -1.0F, 2, 6, 2, 0.0F);
        this.base = new ModelRenderer(this, 0, 31);
        this.base.setRotationPoint(0.0F, 23.0F, -0.9F);
        this.base.addBox(-8.0F, 0.0F, -4.5F, 16, 1, 11, 0.0F);
        this.shape15_1 = new ModelRenderer(this, 19, 0);
        this.shape15_1.setRotationPoint(0.0F, 10.5F, 0.0F);
        this.shape15_1.addBox(-2.0F, 0.5F, -1.5F, 4, 1, 3, 0.0F);
        this.shape20_7 = new ModelRenderer(this, 40, 16);
        this.shape20_7.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.shape20_7.addBox(-0.5F, 0.0F, -2.0F, 1, 0, 2, 0.0F);
        this.setRotateAngle(this.shape20_7, 0.6283185307179586F, 0.25132741228718347F, 0.25132741228718347F);
        this.baseTop = new ModelRenderer(this, 0, 19);
        this.baseTop.setRotationPoint(0.0F, -1.0F, 1.0F);
        this.baseTop.addBox(-7.5F, 0.0F, -4.5F, 15, 2, 9, 0.0F);
        this.shape20_1 = new ModelRenderer(this, 40, 16);
        this.shape20_1.setRotationPoint(0.5F, 0.0F, -2.0F);
        this.shape20_1.addBox(-0.5F, 0.0F, -5.0F, 1, 0, 5, 0.0F);
        this.setRotateAngle(this.shape20_1, 0.5654866776461628F, -0.439822971502571F, 0.0F);
        this.shape20_4 = new ModelRenderer(this, 40, 16);
        this.shape20_4.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.shape20_4.addBox(-0.5F, 0.0F, -3.0F, 1, 0, 3, 0.0F);
        this.setRotateAngle(this.shape20_4, 1.0053096491487339F, 0.1884955592153876F, 0.5654866776461628F);
        this.shape20_8 = new ModelRenderer(this, 40, 16);
        this.shape20_8.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.shape20_8.addBox(-0.5F, 0.0F, -3.0F, 1, 0, 3, 0.0F);
        this.setRotateAngle(this.shape20_8, 1.0053096491487339F, 0.439822971502571F, -0.06283185307179587F);
        this.shape20_2 = new ModelRenderer(this, 40, 16);
        this.shape20_2.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.shape20_2.addBox(-0.5F, 0.0F, -2.0F, 1, 0, 2, 0.0F);
        this.setRotateAngle(this.shape20_2, 0.6283185307179586F, -0.25132741228718347F, 0.25132741228718347F);
        this.shape20 = new ModelRenderer(this, 40, 16);
        this.shape20.setRotationPoint(-2.6F, -0.6F, -1.2F);
        this.shape20.addBox(0.0F, 0.0F, -2.0F, 1, 0, 2, 0.0F);
        this.setRotateAngle(this.shape20, 1.0053096491487339F, -0.06283185307179587F, -0.25132741228718347F);
        this.shape20_3 = new ModelRenderer(this, 40, 16);
        this.shape20_3.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.shape20_3.addBox(-0.5F, 0.0F, -2.0F, 1, 0, 2, 0.0F);
        this.setRotateAngle(this.shape20_3, 0.6911503837897545F, -0.25132741228718347F, 0.06283185307179587F);
        this.rightLeg = new ModelRenderer(this, 0, 12);
        this.rightLeg.setRotationPoint(-1.5F, 10.0F, 0.1F);
        this.rightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 13, 2, 0.0F);
        this.setRotateAngle(this.rightLeg, 0.0F, 0.0F, 0.08726646259971647F);
        this.leftLeg = new ModelRenderer(this, 0, 12);
        this.leftLeg.setRotationPoint(1.5F, 10.0F, 0.1F);
        this.leftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 13, 2, 0.0F);
        this.setRotateAngle(this.leftLeg, 0.0F, 0.0F, -0.08726646259971647F);
        this.shape20_6 = new ModelRenderer(this, 40, 16);
        this.shape20_6.setRotationPoint(0.0F, 0.0F, -3.0F);
        this.shape20_6.addBox(-0.5F, 0.0F, -2.0F, 1, 0, 2, 0.0F);
        this.setRotateAngle(this.shape20_6, 1.0681415022205298F, 0.06283185307179587F, 0.3769911184307752F);
        this.shape20_4.addChild(this.shape20_5);
        this.body.addChild(this.shape15);
        this.shape15.addChild(this.field_78116_c);
        this.shape15.addChild(this.shape15_1);
        this.shape20_6.addChild(this.shape20_7);
        this.base.addChild(this.baseTop);
        this.shape20.addChild(this.shape20_1);
        this.shape20_3.addChild(this.shape20_4);
        this.shape20_7.addChild(this.shape20_8);
        this.shape20_1.addChild(this.shape20_2);
        this.shape15.addChild(this.shape20);
        this.shape20_2.addChild(this.shape20_3);
        this.shape20_5.addChild(this.shape20_6);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ticks, float yaw, float pitch, float scale) {
        this.rightArm.render(scale);
        this.leftArm.render(scale);
        this.body.render(scale);
        this.head.render(scale);
        this.base.render(scale);
        this.rightLeg.render(scale);
        this.leftLeg.render(scale);
    }

    private void setRotateAngle(ModelRenderer box, float x, float y, float z) {
        box.rotateAngleX = x;
        box.rotateAngleY = y;
        box.rotateAngleZ = z;
    }
}
