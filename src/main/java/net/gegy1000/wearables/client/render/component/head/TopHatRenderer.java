package net.gegy1000.wearables.client.render.component.head;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.gegy1000.wearables.client.model.component.head.TopHatModel;
import net.gegy1000.wearables.client.render.component.ComponentRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

public class TopHatRenderer extends ComponentRenderer {
    private static final TopHatModel MODEL = new TopHatModel();
    private static final ResourceLocation TEXTURE = new ResourceLocation(Wearables.MODID, "textures/component/top_hat.png");
    private static final ResourceLocation TEXTURE_LINE = new ResourceLocation(Wearables.MODID, "textures/component/top_hat_line.png");

    @Override
    public AxisAlignedBB getBounds() {
        return new AxisAlignedBB(-0.4F, -0.5F, -0.4F, 0.4F, -1.0F, 0.4F);
    }

    @Override
    public WearableComponentModel getModel(boolean smallArms) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTexture(boolean smallArms, int layer) {
        return layer == 0 ? TEXTURE : TEXTURE_LINE;
    }
}
