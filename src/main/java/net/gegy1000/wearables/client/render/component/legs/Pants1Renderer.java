package net.gegy1000.wearables.client.render.component.legs;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.gegy1000.wearables.client.model.component.legs.Pants1Model;
import net.gegy1000.wearables.client.render.component.ComponentRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

public class Pants1Renderer extends ComponentRenderer {
    private static final Pants1Model MODEL = new Pants1Model();
    private static final ResourceLocation TEXTURE_1 = new ResourceLocation(Wearables.MODID, "textures/component/pants_1.png");
    private static final ResourceLocation TEXTURE_2 = new ResourceLocation(Wearables.MODID, "textures/component/pants_2.png");

    @Override
    public WearableComponentModel getModel(boolean smallArms) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTexture(boolean smallArms, int layer) {
        return layer == 0 ? TEXTURE_1 : TEXTURE_2;
    }

    @Override
    public AxisAlignedBB getBounds() {
        return new AxisAlignedBB(-0.3F, 1.5F, -0.2F, 0.3F, 0.6F, 0.2F);
    }
}
