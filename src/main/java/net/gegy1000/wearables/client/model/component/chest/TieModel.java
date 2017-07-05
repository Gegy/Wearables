package net.gegy1000.wearables.client.model.component.chest;

import com.google.common.collect.ImmutableList;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.ilexiconn.llibrary.client.util.Matrix;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;

public class TieModel extends WearableComponentModel {
    private ModelRenderer shape15;
    private ModelRenderer shape18;
    private ModelRenderer shape15_1;
    private ModelRenderer shape18_1;
    private ModelRenderer shape26;
    private ModelRenderer shape18_2;
    private ModelRenderer shape18_3;
    private ModelRenderer shape18_4;
    private ModelRenderer shape18_5;

    public TieModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.shape15 = new ModelRenderer(this, 0, 0);
        this.shape15.setRotationPoint(0.0F, -0.1F, -2.8F);
        this.shape15.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(this.shape15, -0.031415926535897934F, 0.0F, -0.2953097094374406F);
        this.shape18_5 = new ModelRenderer(this, 0, 0);
        this.shape18_5.setRotationPoint(0.0F, 0.92F, 0.0F);
        this.shape18_5.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.shape18_1 = new ModelRenderer(this, 0, 0);
        this.shape18_1.setRotationPoint(-1.0F, 6.0F, 0.0F);
        this.shape18_1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(this.shape18_1, 0.0F, 0.0F, -0.9110618695410401F);
        this.shape26 = new ModelRenderer(this, 0, 0);
        this.shape26.setRotationPoint(0.01F, 2.99F, 0.0F);
        this.shape26.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(this.shape26, 0.0F, 0.0F, -0.11309733552923257F);
        this.shape18_4 = new ModelRenderer(this, 0, 0);
        this.shape18_4.setRotationPoint(1.0F, 6.0F, 0.0F);
        this.shape18_4.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(this.shape18_4, 0.0F, 0.0F, 0.9110618695410401F);
        this.shape15_1 = new ModelRenderer(this, 0, 0);
        this.shape15_1.setRotationPoint(0.8245580027861918F, 0.5300831014745355F, 0.0F);
        this.shape15_1.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(this.shape15_1, 0.0F, 0.0F, 0.5654866776461627F);
        this.shape18_3 = new ModelRenderer(this, 0, 0);
        this.shape18_3.setRotationPoint(-1.09F, 1.0F, 0.0F);
        this.shape18_3.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
        this.setRotateAngle(this.shape18_3, 0.0439822971502571F, 0.0F, -0.4084070449666731F);
        this.shape18 = new ModelRenderer(this, 0, 0);
        this.shape18.setRotationPoint(0.09F, 1.0F, 0.0F);
        this.shape18.addBox(-1.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
        this.setRotateAngle(this.shape18, 0.0439822971502571F, 0.0F, 0.4084070449666731F);
        this.shape18_2 = new ModelRenderer(this, 0, 0);
        this.shape18_2.setRotationPoint(0.0F, 0.92F, 0.0F);
        this.shape18_2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.shape18_4.addChild(this.shape18_5);
        this.shape18.addChild(this.shape18_1);
        this.shape18.addChild(this.shape26);
        this.shape18_3.addChild(this.shape18_4);
        this.shape15.addChild(this.shape15_1);
        this.shape15_1.addChild(this.shape18_3);
        this.shape15.addChild(this.shape18);
        this.shape18_1.addChild(this.shape18_2);
    }

    @Override
    public void renderComponent(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        this.renderParented(this.bipedBody, this.shape15, 1.0F, 0.0F, 0.0F, 0.0F, scale);
    }

    @Override
    public void buildQuads(Matrix matrix, ImmutableList.Builder<BakedQuad> builder, VertexFormat format, TextureAtlasSprite sprite) {
        this.buildCuboidParented(this.bipedBody, this.shape15, matrix, builder, format, sprite);
    }
}
