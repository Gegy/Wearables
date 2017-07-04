package net.gegy1000.wearables.server.wearable.event;

import net.gegy1000.wearables.server.movement.LocalPlayerState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class WingEventHandler extends ComponentEventHandler {
    @Override
    public void onFall(EntityPlayer player, LivingFallEvent event) {
        event.setDistance(0.0F);
    }

    @Override
    public void onRemoved(EntityPlayer player) {
        LocalPlayerState state = LocalPlayerState.getState(player);
        player.eyeHeight = player.getDefaultEyeHeight();
        state.setFlyToggle(false);
    }
}
