package net.gegy1000.wearables.client.render.component.head;

import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.gegy1000.wearables.client.model.component.head.Helmet1Model;
import net.gegy1000.wearables.client.render.component.ComponentRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

public class Helmet1Renderer extends ComponentRenderer {
    private static final Helmet1Model MODEL = new Helmet1Model();

    @Override
    public WearableComponentModel getModel(boolean smallArms) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTexture(boolean smallArms, int layer) {
        return null;
    }

    @Override
    public AxisAlignedBB getBounds() {
        return new AxisAlignedBB(-0.4F, 0.0F, -0.3F, 0.4F, -0.6F, 0.3F);
    }
}
