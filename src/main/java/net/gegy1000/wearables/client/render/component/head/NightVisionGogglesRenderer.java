package net.gegy1000.wearables.client.render.component.head;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.gegy1000.wearables.client.model.component.head.NightVisionGogglesModel;
import net.gegy1000.wearables.client.render.component.ComponentRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

public class NightVisionGogglesRenderer extends ComponentRenderer {
    private static final NightVisionGogglesModel MODEL = new NightVisionGogglesModel();
    private static final ResourceLocation TEXTURE = new ResourceLocation(Wearables.MODID, "textures/component/night_vision.png");
    private static final ResourceLocation TEXTURE_STATIC = new ResourceLocation(Wearables.MODID, "textures/component/night_vision_static.png");

    @Override
    public WearableComponentModel getModel(boolean smallArms) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTexture(boolean smallArms, int layer) {
        if (layer == 0) {
            return TEXTURE;
        } else {
            return TEXTURE_STATIC;
        }
    }

    @Override
    public AxisAlignedBB getBounds() {
        return new AxisAlignedBB(-0.4F, -0.1F, -0.4F, 0.4F, -0.5F, 0.4F);
    }
}
