package net.gegy1000.wearables.server.wearable.event;

import net.minecraft.entity.player.EntityPlayer;

public class FlipperEventHandler extends ComponentEventHandler {
    @Override
    public float getSpeedModifier(EntityPlayer player) {
        return player.isInWater() ? 1.0F : 0.8F;
    }

    @Override
    public void onRemoved(EntityPlayer player) {
        player.eyeHeight = player.getDefaultEyeHeight();
    }
}
