package net.gegy1000.wearables.server.wearable.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class JetpackEventHandler extends ComponentEventHandler {
    @Override
    public void onFall(EntityPlayer player, LivingFallEvent event) {
        float distance = (float) (player.lastTickPosY - player.posY) * 10.0F;
        event.setDistance(distance);
    }
}
