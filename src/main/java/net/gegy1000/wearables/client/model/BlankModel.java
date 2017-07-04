package net.gegy1000.wearables.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlankModel extends ModelBiped {
    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ticks, float yaw, float pitch, float scale) {
    }
}
