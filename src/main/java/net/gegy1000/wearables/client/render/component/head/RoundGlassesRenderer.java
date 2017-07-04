package net.gegy1000.wearables.client.render.component.head;

import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.gegy1000.wearables.client.model.component.head.RoundGlassesModel;
import net.gegy1000.wearables.client.render.component.ComponentRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

public class RoundGlassesRenderer extends ComponentRenderer {
    private static final RoundGlassesModel MODEL = new RoundGlassesModel();

    @Override
    public AxisAlignedBB getBounds() {
        return new AxisAlignedBB(-0.25F, -0.15F, -0.05F, 0.25F, -0.325F, 0.3F);
    }

    @Override
    public WearableComponentModel getModel(boolean smallArms) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTexture(boolean smallArms, int layer) {
        return null;
    }
}
