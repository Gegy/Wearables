package net.gegy1000.wearables.server.core;

import net.gegy1000.wearables.server.movement.MovementHandler;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;

import java.util.List;

public class WearablesClientHooks {
    public static void applyRotations(RenderPlayer renderer, AbstractClientPlayer player, float partialTicks) {
        List<MovementHandler> movementHandlers = WearableUtils.getMovementHandlers(player);
        for (MovementHandler movementHandler : movementHandlers) {
            movementHandler.applyRotations(player, partialTicks);
        }
    }
}
