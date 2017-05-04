package net.gegy1000.wearables.client.render.component.feet;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.gegy1000.wearables.client.model.component.feet.FlippersModel;
import net.gegy1000.wearables.client.render.component.ComponentRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

public class FlippersRenderer extends ComponentRenderer {
    private static final FlippersModel MODEL = new FlippersModel();
    private static final ResourceLocation TEXTURE_1 = new ResourceLocation(Wearables.MODID, "textures/component/flippers_1.png");
    private static final ResourceLocation TEXTURE_2 = new ResourceLocation(Wearables.MODID, "textures/component/flippers_2.png");

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
        return new AxisAlignedBB(-0.6F, 1.6F, -0.1F, 0.6F, 1.2F, 0.8F);
    }
}
