package net.gegy1000.wearables.client.render.component.chest;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.gegy1000.wearables.client.model.component.chest.ModOffCapeModel;
import net.gegy1000.wearables.client.render.component.ComponentRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

public class ModOffCapeRenderer extends ComponentRenderer {
    private static final ModOffCapeModel MODEL = new ModOffCapeModel();
    private static final ResourceLocation MODOFF_CAPE = new ResourceLocation(Wearables.MODID, "textures/component/modoff_cape_background.png");
    private static final ResourceLocation MODOFF_CAPE_TEXT = new ResourceLocation(Wearables.MODID, "textures/component/modoff_cape_text.png");

    @Override
    public WearableComponentModel getModel(boolean smallArms) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTexture(boolean smallArms, int layer) {
        return layer == 0 ? MODOFF_CAPE : MODOFF_CAPE_TEXT;
    }

    @Override
    public AxisAlignedBB getBounds() {
        return new AxisAlignedBB(-0.35F, 1.1F, -0.2F, 0.35F, 0.05F, -0.1F);
    }

    @Override
    public float getItemRotationY() {
        return 180.0F;
    }
}
