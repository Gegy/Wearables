package net.gegy1000.wearables.client.model.component.chest;

import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class BowTieModel extends WearableComponentModel {
    public ModelRenderer BowTie1;
    public ModelRenderer BowRight1;
    public ModelRenderer BowLeft1;
    public ModelRenderer BowRight2;
    public ModelRenderer BowRight3;
    public ModelRenderer BowLeft2;
    public ModelRenderer BowLeft3;

    public BowTieModel() {
        this.textureWidth = 16;
        this.textureHeight = 8;
        this.BowTie1 = new ModelRenderer(this, 0, 0);
        this.BowTie1.setRotationPoint(0.0F, 1.1F, -2.3F);
        this.BowTie1.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 1, 0.0F);
        this.setRotateAngle(this.BowTie1, -0.06283185307179587F, 0.0F, 0.0F);
        this.BowRight1 = new ModelRenderer(this, 0, 3);
        this.BowRight1.setRotationPoint(-4.05F, 0.0F, -0.8F);
        this.BowRight1.addBox(0.0F, -0.5F, 0.0F, 4, 1, 1, 0.0F);
        this.BowLeft3 = new ModelRenderer(this, 0, 3);
        this.BowLeft3.setRotationPoint(0.0F, 0.5F, 0.05F);
        this.BowLeft3.addBox(-4.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
        this.setRotateAngle(this.BowLeft3, 0.0F, 0.0F, 0.1884955592153876F);
        this.BowLeft2 = new ModelRenderer(this, 0, 3);
        this.BowLeft2.setRotationPoint(0.0F, -0.5F, 0.05F);
        this.BowLeft2.addBox(-4.0F, -1.0F, 0.0F, 4, 1, 1, 0.0F);
        this.setRotateAngle(this.BowLeft2, 0.0F, 0.0F, -0.1884955592153876F);
        this.BowRight3 = new ModelRenderer(this, 0, 3);
        this.BowRight3.setRotationPoint(0.0F, 0.5F, 0.05F);
        this.BowRight3.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
        this.setRotateAngle(this.BowRight3, 0.0F, 0.0F, -0.1884955592153876F);
        this.BowLeft1 = new ModelRenderer(this, 0, 3);
        this.BowLeft1.setRotationPoint(4.05F, 0.0F, -0.8F);
        this.BowLeft1.addBox(-4.0F, -0.5F, 0.0F, 4, 1, 1, 0.0F);
        this.BowRight2 = new ModelRenderer(this, 0, 3);
        this.BowRight2.setRotationPoint(0.0F, -0.5F, 0.05F);
        this.BowRight2.addBox(0.0F, -1.0F, 0.0F, 4, 1, 1, 0.0F);
        this.setRotateAngle(this.BowRight2, 0.0F, 0.0F, 0.1884955592153876F);
        this.BowTie1.addChild(this.BowRight1);
        this.BowLeft1.addChild(this.BowLeft3);
        this.BowLeft1.addChild(this.BowLeft2);
        this.BowRight1.addChild(this.BowRight3);
        this.BowTie1.addChild(this.BowLeft1);
        this.BowRight1.addChild(this.BowRight2);
    }

    @Override
    public void renderComponent(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        this.renderParented(this.bipedBody, this.BowTie1, 0.8F, 0.0F, 0.0F, 0.0F, scale);
    }
}
