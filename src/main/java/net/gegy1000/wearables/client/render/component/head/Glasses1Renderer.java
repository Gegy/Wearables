package net.gegy1000.wearables.client.render.component.head;

import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.gegy1000.wearables.client.model.component.head.Glasses1Model;
import net.gegy1000.wearables.client.render.component.ComponentRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

public class Glasses1Renderer extends ComponentRenderer {
    private static final Glasses1Model MODEL = new Glasses1Model();

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
        return new AxisAlignedBB(-0.3F, -0.15F, -0.08F, 0.3F, -0.3F, 0.3F);
    }
}
