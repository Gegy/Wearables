package net.gegy1000.wearables.client.render.component;

import net.gegy1000.wearables.client.model.component.WearableComponentModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ComponentRenderer {
    public abstract WearableComponentModel getModel(boolean smallArms);

    public abstract ResourceLocation getTexture(boolean smallArms, int layer);

    public abstract AxisAlignedBB getBounds();

    public float getInventoryScale(ItemCameraTransforms.TransformType type) {
        return 1.0F;
    }

    public float[] adjustColour(float[] colour, int layer) {
        return colour;
    }

    public float getItemRotationY() {
        return 0.0F;
    }
}
