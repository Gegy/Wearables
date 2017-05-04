package net.gegy1000.wearables.client.render.component.head;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.gegy1000.wearables.client.model.component.head.Retro3DGlassesModel;
import net.gegy1000.wearables.client.render.component.ComponentRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

public class Retro3DGlassesRenderer extends ComponentRenderer {
    private static final Retro3DGlassesModel MODEL = new Retro3DGlassesModel();
    private static final ResourceLocation TEXTURE = new ResourceLocation(Wearables.MODID, "textures/component/retro_3d_glasses.png");
    private static final ResourceLocation TEXTURE_COLOUR = new ResourceLocation(Wearables.MODID, "textures/component/retro_3d_glasses_colour.png");

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
        return layer == 0 ? TEXTURE : TEXTURE_COLOUR;
    }
}
