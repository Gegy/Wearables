package net.gegy1000.wearables.client.model.component.chest;

import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.gegy1000.wearables.server.movement.LocalPlayerState;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.ilexiconn.llibrary.LLibrary;
import net.ilexiconn.llibrary.client.util.ClientUtils;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class WingsModel extends WearableComponentModel {
    public ModelRenderer Main;
    public ModelRenderer rightWing;
    public ModelRenderer leftWing;
    public ModelRenderer shape46;
    public ModelRenderer shape53;
    public ModelRenderer shape47;
    public ModelRenderer shape52;
    public ModelRenderer WING2;
    public ModelRenderer wingggg;
    public ModelRenderer shape51;
    public ModelRenderer WINGSHIT;
    public ModelRenderer shape48;
    public ModelRenderer shape51_1;
    public ModelRenderer shape54;
    public ModelRenderer shape54_1;
    public ModelRenderer shape46_1;
    public ModelRenderer shape53_1;
    public ModelRenderer shape47_1;
    public ModelRenderer shape52_1;
    public ModelRenderer WING2_1;
    public ModelRenderer wingggg_1;
    public ModelRenderer shape51_2;
    public ModelRenderer WINGSHIT_1;
    public ModelRenderer shape48_1;
    public ModelRenderer shape51_3;
    public ModelRenderer shape54_2;
    public ModelRenderer shape54_3;

    private ModelRenderer[] rightWingParts;
    private ModelRenderer[] leftWingParts;

    public WingsModel() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.shape51_1 = new ModelRenderer(this, -17, 59);
        this.shape51_1.mirror = true;
        this.shape51_1.setRotationPoint(0.0F, 0.1F, 0.0F);
        this.shape51_1.addBox(-17.0F, 0.0F, 0.0F, 17, 0, 17, 0.0F);
        this.setRotateAngle(this.shape51_1, 0.012566370614359173F, -0.27960174616949157F, 0.0F);
        this.shape53 = new ModelRenderer(this, -18, 22);
        this.shape53.mirror = true;
        this.shape53.setRotationPoint(0.7F, 0.7F, -0.8F);
        this.shape53.addBox(-15.0F, 0.0F, 0.0F, 15, 0, 18, 0.0F);
        this.setRotateAngle(this.shape53, 0.06911503837897544F, -0.028274333882308142F, 0.0F);
        this.leftWing = new ModelRenderer(this, 0, 0);
        this.leftWing.setRotationPoint(3.0F, 0.89F, 2.84F);
        this.leftWing.addBox(0.0F, -0.5F, -1.0F, 13, 3, 3, 0.0F);
        this.setRotateAngle(this.leftWing, -1.661378914973402F, -0.7929030791810239F, 0.27523842303950574F);
        this.WINGSHIT_1 = new ModelRenderer(this, 33, 0);
        this.WINGSHIT_1.setRotationPoint(0.0F, 0.0F, 0.5F);
        this.WINGSHIT_1.addBox(-11.0F, -0.5F, -0.5F, 10, 1, 2, 0.0F);
        this.setRotateAngle(this.WINGSHIT_1, 0.0F, 2.408554367752175F, 0.0F);
        this.shape47 = new ModelRenderer(this, 0, 12);
        this.shape47.setRotationPoint(-12.7F, 0.2F, -0.5F);
        this.shape47.addBox(-13.0F, -0.5F, -0.5F, 13, 1, 2, 0.0F);
        this.setRotateAngle(this.shape47, -0.12566370614359174F, 0.3433062638672847F, -0.5136503988619312F);
        this.shape51 = new ModelRenderer(this, 12, 10);
        this.shape51.mirror = true;
        this.shape51.setRotationPoint(0.0F, 0.2F, 1.0F);
        this.shape51.addBox(-13.0F, 0.0F, 0.0F, 14, 0, 18, 0.0F);
        this.setRotateAngle(this.shape51, 0.012566370614359173F, -0.08011061266653972F, 0.0F);
        this.WINGSHIT = new ModelRenderer(this, 33, 0);
        this.WINGSHIT.setRotationPoint(0.0F, 0.0F, 0.5F);
        this.WINGSHIT.addBox(1.0F, -0.5F, -0.5F, 10, 1, 2, 0.0F);
        this.setRotateAngle(this.WINGSHIT, 0.0F, -2.408554367752175F, 0.0F);
        this.rightWing = new ModelRenderer(this, 0, 0);
        this.rightWing.setRotationPoint(-3.0F, 0.89F, 2.84F);
        this.rightWing.addBox(-13.0F, -0.5F, -1.0F, 13, 3, 3, 0.0F);
        this.setRotateAngle(this.rightWing, -1.661378914973402F, 0.7929030791810239F, -0.27523842303950574F);
        this.shape48 = new ModelRenderer(this, 0, 19);
        this.shape48.setRotationPoint(-10.0F, 0.0F, -0.5F);
        this.shape48.addBox(-9.0F, -0.5F, 0.0F, 9, 1, 1, 0.0F);
        this.setRotateAngle(this.shape48, 0.0F, 0.439822971502571F, 0.0F);
        this.shape54 = new ModelRenderer(this, 31, 7);
        this.shape54.setRotationPoint(10.3F, 0.0F, 0.7F);
        this.shape54.addBox(0.0F, -0.5F, -0.5F, 12, 1, 1, 0.0F);
        this.setRotateAngle(this.shape54, 0.0F, 0.3141592653589793F, 0.0F);
        this.WING2_1 = new ModelRenderer(this, 33, 0);
        this.WING2_1.setRotationPoint(12.3F, 0.2F, 0.4F);
        this.WING2_1.addBox(-9.0F, -0.5F, -1.0F, 9, 1, 2, 0.0F);
        this.setRotateAngle(this.WING2_1, 0.0F, 0.9498081789353141F, 0.0F);
        this.wingggg = new ModelRenderer(this, 0, 16);
        this.wingggg.setRotationPoint(-12.7F, 0.0F, 0.3F);
        this.wingggg.addBox(-10.0F, -0.5F, -0.5F, 10, 1, 1, 0.0F);
        this.setRotateAngle(this.wingggg, 0.0F, 0.2623229865747477F, -0.5654866776461628F);
        this.shape53_1 = new ModelRenderer(this, -18, 22);
        this.shape53_1.setRotationPoint(-0.7F, 0.7F, -0.8F);
        this.shape53_1.addBox(0.0F, 0.0F, 0.0F, 15, 0, 18, 0.0F);
        this.setRotateAngle(this.shape53_1, 0.06911503837897544F, 0.028274333882308142F, 0.0F);
        this.shape51_3 = new ModelRenderer(this, -17, 59);
        this.shape51_3.setRotationPoint(0.0F, 0.1F, 0.0F);
        this.shape51_3.addBox(0.0F, 0.0F, 0.0F, 17, 0, 17, 0.0F);
        this.setRotateAngle(this.shape51_3, 0.012566370614359173F, 0.27960174616949157F, 0.0F);
        this.Main = new ModelRenderer(this, 24, 16);
        this.Main.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Main.addBox(-0.5F, 0.0F, -0.5F, 1, 1, 1, 0.0F);
        this.wingggg_1 = new ModelRenderer(this, 0, 16);
        this.wingggg_1.setRotationPoint(12.7F, 0.0F, 0.3F);
        this.wingggg_1.addBox(0.0F, -0.5F, -0.5F, 10, 1, 1, 0.0F);
        this.setRotateAngle(this.wingggg_1, 0.0F, -0.2623229865747477F, 0.5654866776461628F);
        this.shape54_1 = new ModelRenderer(this, 33, 4);
        this.shape54_1.setRotationPoint(8.8F, 0.0F, 0.1F);
        this.shape54_1.addBox(0.0F, -0.5F, -0.5F, 9, 1, 1, 0.0F);
        this.setRotateAngle(this.shape54_1, 0.0F, 0.12566370614359174F, 0.0F);
        this.shape46_1 = new ModelRenderer(this, 0, 7);
        this.shape46_1.mirror = true;
        this.shape46_1.setRotationPoint(12.5F, 0.6F, 0.5F);
        this.shape46_1.addBox(0.0F, -0.5F, -1.0F, 13, 2, 2, 0.0F);
        this.setRotateAngle(this.shape46_1, 0.0879645943005142F, 0.8386307055832752F, -0.13962634015954636F);
        this.shape54_3 = new ModelRenderer(this, 33, 4);
        this.shape54_3.setRotationPoint(-8.8F, 0.0F, 0.1F);
        this.shape54_3.addBox(-9.0F, -0.5F, -0.5F, 9, 1, 1, 0.0F);
        this.setRotateAngle(this.shape54_3, 0.0F, -0.12566370614359174F, 0.0F);
        this.shape48_1 = new ModelRenderer(this, 0, 19);
        this.shape48_1.setRotationPoint(10.0F, 0.0F, -0.5F);
        this.shape48_1.addBox(0.0F, -0.5F, 0.0F, 9, 1, 1, 0.0F);
        this.setRotateAngle(this.shape48_1, 0.0F, -0.439822971502571F, 0.0F);
        this.shape54_2 = new ModelRenderer(this, 31, 7);
        this.shape54_2.setRotationPoint(-10.3F, 0.0F, 0.7F);
        this.shape54_2.addBox(-12.0F, -0.5F, -0.5F, 12, 1, 1, 0.0F);
        this.setRotateAngle(this.shape54_2, 0.0F, -0.3141592653589793F, 0.0F);
        this.WING2 = new ModelRenderer(this, 33, 0);
        this.WING2.setRotationPoint(-12.3F, 0.2F, 0.4F);
        this.WING2.addBox(0.0F, -0.5F, -1.0F, 9, 1, 2, 0.0F);
        this.setRotateAngle(this.WING2, 0.0F, -0.9498081789353141F, 0.0F);
        this.shape51_2 = new ModelRenderer(this, 12, 10);
        this.shape51_2.setRotationPoint(-0.2F, 0.2F, 1.0F);
        this.shape51_2.addBox(-1.0F, 0.0F, 0.0F, 14, 0, 18, 0.0F);
        this.setRotateAngle(this.shape51_2, 0.012566370614359173F, 0.08011061266653972F, 0.0F);
        this.shape47_1 = new ModelRenderer(this, 0, 12);
        this.shape47_1.setRotationPoint(12.7F, 0.2F, -0.5F);
        this.shape47_1.addBox(0.0F, -0.5F, -0.5F, 13, 1, 2, 0.0F);
        this.setRotateAngle(this.shape47_1, -0.12566370614359174F, -0.3433062638672847F, 0.5136503988619312F);
        this.shape52_1 = new ModelRenderer(this, -17, 41);
        this.shape52_1.mirror = true;
        this.shape52_1.setRotationPoint(1.0F, 0.3F, -1.2F);
        this.shape52_1.addBox(-13.0F, 0.0F, 0.0F, 24, 0, 17, 0.0F);
        this.setRotateAngle(this.shape52_1, 0.006283185307179587F, -0.11920598791121272F, 0.0F);
        this.shape52 = new ModelRenderer(this, -17, 41);
        this.shape52.setRotationPoint(1.0F, 0.3F, -1.2F);
        this.shape52.addBox(-13.0F, 0.0F, 0.0F, 24, 0, 17, 0.0F);
        this.setRotateAngle(this.shape52, 0.006283185307179587F, 0.11920598791121272F, 0.0F);
        this.shape46 = new ModelRenderer(this, 0, 7);
        this.shape46.mirror = true;
        this.shape46.setRotationPoint(-12.5F, 0.6F, 0.5F);
        this.shape46.addBox(-13.0F, -0.5F, -1.0F, 13, 2, 2, 0.0F);
        this.setRotateAngle(this.shape46, 0.0879645943005142F, -0.8386307055832752F, 0.13962634015954636F);
        this.wingggg.addChild(this.shape51_1);
        this.rightWing.addChild(this.shape53);
        this.Main.addChild(this.leftWing);
        this.shape47_1.addChild(this.WINGSHIT_1);
        this.shape46.addChild(this.shape47);
        this.shape47.addChild(this.shape51);
        this.shape47.addChild(this.WINGSHIT);
        this.Main.addChild(this.rightWing);
        this.wingggg.addChild(this.shape48);
        this.WINGSHIT.addChild(this.shape54);
        this.shape46_1.addChild(this.WING2_1);
        this.shape47.addChild(this.wingggg);
        this.leftWing.addChild(this.shape53_1);
        this.wingggg_1.addChild(this.shape51_3);
        this.shape47_1.addChild(this.wingggg_1);
        this.WING2.addChild(this.shape54_1);
        this.leftWing.addChild(this.shape46_1);
        this.WING2_1.addChild(this.shape54_3);
        this.wingggg_1.addChild(this.shape48_1);
        this.WINGSHIT_1.addChild(this.shape54_2);
        this.shape46.addChild(this.WING2);
        this.shape47_1.addChild(this.shape51_2);
        this.shape46_1.addChild(this.shape47_1);
        this.shape46_1.addChild(this.shape52_1);
        this.shape46.addChild(this.shape52);
        this.rightWing.addChild(this.shape46);

        this.rightWingParts = new ModelRenderer[] { this.rightWing, this.shape46, this.shape47, this.wingggg };
        this.leftWingParts = new ModelRenderer[] { this.leftWing, this.shape46_1, this.shape47_1, this.wingggg_1 };
    }

    @Override
    public void renderComponent(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
        boolean onGround = entity == null || WearableUtils.onGround(entity);
        if (entity != null && !onGround) {
            limbSwing = age;
            limbSwingAmount = 1.2F;
        } else if (entity == null) {
            limbSwing = age * 0.25F;
            limbSwingAmount = 0.4F;
        }
        float timer = 1.0F;
        if (entity instanceof EntityPlayer) {
            LocalPlayerState state = LocalPlayerState.getState((EntityPlayer) entity);
            timer = 1.0F - state.getRenderFlyTimer(LLibrary.PROXY.getPartialTicks());
            limbSwing += age * 0.15F;
            limbSwingAmount = ClientUtils.interpolate(limbSwingAmount, 0.4F, timer);
        }
        float flapOffset = this.calculateChainOffset(-2, this.rightWingParts);
        for (int index = 0; index < this.rightWingParts.length; index++) {
            ModelRenderer part = this.rightWingParts[index];
            float rotation = this.calculateChainRotation(0.25F, 0.3F, limbSwing, limbSwingAmount, flapOffset, index);
            if (index == 0) {
                part.rotateAngleY = rotation + 0.5F;
                if (onGround) {
                    part.rotateAngleY += timer * 0.8F;
                }
            } else {
                part.rotateAngleZ = rotation - 0.2F;
                if (onGround) {
                    part.rotateAngleZ -= timer * 0.8F;
                }
            }
        }
        for (int index = 0; index < this.leftWingParts.length; index++) {
            ModelRenderer part = this.leftWingParts[index];
            float rotation = this.calculateChainRotation(0.25F, 0.3F, limbSwing, limbSwingAmount, flapOffset, index);
            if (index == 0) {
                part.rotateAngleY = -rotation - 0.5F;
                if (onGround) {
                    part.rotateAngleY -= timer * 0.8F;
                }
            } else {
                part.rotateAngleZ = -rotation + 0.2F;
                if (onGround) {
                    part.rotateAngleZ += timer * 0.8F;
                }
            }
        }
        this.renderParented(this.bipedBody, this.Main, scale);
    }
}
