package net.gegy1000.wearables.client.render.component.chest;

import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.gegy1000.wearables.client.model.component.chest.TieModel;
import net.gegy1000.wearables.client.render.component.ComponentRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

public class TieRenderer extends ComponentRenderer {
    private static final TieModel MODEL = new TieModel();

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
        return new AxisAlignedBB(-0.1F, 0.5F, 0.1F, 0.1F, -0.05F, 0.2F);
    }
}
