package net.gegy1000.wearables.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HeadDisplayStandModel extends ModelBase {
    private ModelRenderer base1;
    private ModelRenderer base2;
    private ModelRenderer neck;
    public ModelRenderer head;

    public HeadDisplayStandModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.base1 = new ModelRenderer(this, 18, 16);
        this.base1.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.base1.addBox(-5.0F, 0.0F, -3.0F, 10, 2, 6, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.base2 = new ModelRenderer(this, 10, 24);
        this.base2.setRotationPoint(0.0F, 21.9F, 0.0F);
        this.base2.addBox(-4.0F, 0.0F, -4.0F, 8, 2, 8, 0.0F);
        this.neck = new ModelRenderer(this, 0, 16);
        this.neck.setRotationPoint(0.0F, 17.0F, 0.0F);
        this.neck.addBox(-2.5F, 0.0F, -2.0F, 5, 5, 4, 0.0F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        this.base1.render(scale);
        this.head.render(scale);
        this.base2.render(scale);
        this.neck.render(scale);
    }
}
