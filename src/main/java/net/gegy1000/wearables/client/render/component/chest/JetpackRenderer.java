package net.gegy1000.wearables.client.render.component.chest;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.gegy1000.wearables.client.model.component.chest.JetpackModel;
import net.gegy1000.wearables.client.render.component.ComponentRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

public class JetpackRenderer extends ComponentRenderer {
    private static final JetpackModel MODEL = new JetpackModel();
    private static final ResourceLocation TEXTURE_1 = new ResourceLocation(Wearables.MODID, "textures/component/jetpack_1.png");
    private static final ResourceLocation TEXTURE_2 = new ResourceLocation(Wearables.MODID, "textures/component/jetpack_2.png");
    private static final ResourceLocation TEXTURE_3 = new ResourceLocation(Wearables.MODID, "textures/component/jetpack_3.png");

    @Override
    public WearableComponentModel getModel(boolean smallArms) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTexture(boolean smallArms, int layer) {
        switch (layer) {
            case 1:
                return TEXTURE_1;
            case 2:
                return TEXTURE_2;
            case 3:
                return TEXTURE_3;
        }
        return TEXTURE_1;
    }

    @Override
    public AxisAlignedBB getBounds() {
        return new AxisAlignedBB(-0.4F, 0.8F, -0.5F, 0.4F, -0.1F, 0.1F);
    }
}
