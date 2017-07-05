package net.gegy1000.wearables.client.model.component.head;

import com.google.common.collect.ImmutableList;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.ilexiconn.llibrary.client.util.Matrix;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;

public class DragonHornsModel extends WearableComponentModel {
    private ModelRenderer hornLeft;
    private ModelRenderer hornRight;
    private ModelRenderer shape99;
    private ModelRenderer shape99_1;
    private ModelRenderer shape99_2;
    private ModelRenderer shape99_3;
    private ModelRenderer shape99_4;
    private ModelRenderer shape99_5;

    public DragonHornsModel() {
        this.textureWidth = 16;
        this.textureHeight = 16;
        this.shape99_2 = new ModelRenderer(this, 0, 0);
        this.shape99_2.setRotationPoint(-0.5F, -5.8F, -0.4F);
        this.shape99_2.addBox(-1.0F, -4.0F, -1.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(this.shape99_2, 0.3769911184307752F, 0.0F, 0.1884955592153876F);
        this.shape99_1 = new ModelRenderer(this, 0, 0);
        this.shape99_1.setRotationPoint(-0.5F, -7.0F, 2.7F);
        this.shape99_1.addBox(-2.0F, -6.0F, -2.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(this.shape99_1, 0.3769911184307752F, 0.0F, -0.3769911184307752F);
        this.shape99 = new ModelRenderer(this, 0, 0);
        this.shape99.setRotationPoint(1.5F, -0.9F, -1.7F);
        this.shape99.addBox(-3.0F, -7.0F, 0.0F, 3, 7, 3, 0.0F);
        this.setRotateAngle(this.shape99, -0.439822971502571F, 0.0F, -0.25132741228718347F);
        this.shape99_3 = new ModelRenderer(this, 0, 0);
        this.shape99_3.setRotationPoint(-1.5F, -0.9F, -1.7F);
        this.shape99_3.addBox(0.0F, -7.0F, 0.0F, 3, 7, 3, 0.0F);
        this.setRotateAngle(this.shape99_3, -0.439822971502571F, 0.0F, 0.25132741228718347F);
        this.shape99_5 = new ModelRenderer(this, 0, 0);
        this.shape99_5.setRotationPoint(0.5F, -5.8F, -0.4F);
        this.shape99_5.addBox(0.0F, -4.0F, -1.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(this.shape99_5, 0.3769911184307752F, 0.0F, -0.1884955592153876F);
        this.shape99_4 = new ModelRenderer(this, 0, 0);
        this.shape99_4.setRotationPoint(0.5F, -7.0F, 2.7F);
        this.shape99_4.addBox(0.0F, -6.0F, -2.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(this.shape99_4, 0.3769911184307752F, 0.0F, 0.3769911184307752F);
        this.hornLeft = new ModelRenderer(this, 0, 0);
        this.hornLeft.setRotationPoint(7.5F, -17.5F, 0.3F);
        this.hornLeft.addBox(-2.0F, -1.0F, -2.0F, 4, 6, 4, 0.0F);
        this.setRotateAngle(this.hornLeft, -0.5654866776461628F, -0.06283185307179587F, 0.5654866776461628F);
        this.hornRight = new ModelRenderer(this, 0, 0);
        this.hornRight.setRotationPoint(-7.5F, -17.5F, 0.3F);
        this.hornRight.addBox(-2.0F, -1.0F, -2.0F, 4, 6, 4, 0.0F);
        this.setRotateAngle(this.hornRight, -0.5654866776461628F, 0.06283185307179587F, -0.5654866776461628F);
        this.shape99_1.addChild(this.shape99_2);
        this.shape99.addChild(this.shape99_1);
        this.hornLeft.addChild(this.shape99);
        this.hornRight.addChild(this.shape99_3);
        this.shape99_4.addChild(this.shape99_5);
        this.shape99_3.addChild(this.shape99_4);
    }

    @Override
    public void renderComponent(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        this.renderParented(this.bipedHead, this.hornLeft, 0.5F, 0.0F, 0.0F, 0.0F, scale);
        this.renderParented(this.bipedHead, this.hornRight, 0.5F, 0.0F, 0.0F, 0.0F, scale);
    }

    @Override
    public void buildQuads(Matrix matrix, ImmutableList.Builder<BakedQuad> builder, VertexFormat format, TextureAtlasSprite sprite) {
        this.buildCuboidParented(this.bipedHead, this.hornLeft, 0.5F, 0.0F, 0.0F, 0.0F, matrix, builder, format, sprite);
        this.buildCuboidParented(this.bipedHead, this.hornRight, 0.5F, 0.0F, 0.0F, 0.0F, matrix, builder, format, sprite);
    }
}
