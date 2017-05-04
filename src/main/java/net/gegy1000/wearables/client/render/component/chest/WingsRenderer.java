package net.gegy1000.wearables.client.render.component.chest;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.gegy1000.wearables.client.model.component.chest.WingsModel;
import net.gegy1000.wearables.client.render.component.ComponentRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

public class WingsRenderer extends ComponentRenderer {
    private static final WingsModel MODEL = new WingsModel();
    private static final ResourceLocation TEXTURE = new ResourceLocation(Wearables.MODID, "textures/component/wings.png");
    private static final ResourceLocation TEXTURE_OVERLAY = new ResourceLocation(Wearables.MODID, "textures/component/wings_overlay.png");

    @Override
    public WearableComponentModel getModel(boolean smallArms) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTexture(boolean smallArms, int layer) {
        if (layer == 0) {
            return TEXTURE;
        } else {
            return TEXTURE_OVERLAY;
        }
    }

    @Override
    public AxisAlignedBB getBounds() {
        return new AxisAlignedBB(-2.0F, 1.0F, -2.0F, 2.0F, -0.6F, 0.1F);
    }
}
