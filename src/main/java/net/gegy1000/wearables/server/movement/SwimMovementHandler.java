package net.gegy1000.wearables.server.movement;

import net.ilexiconn.llibrary.client.util.ClientUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class SwimMovementHandler extends MovementHandler {
    @Override
    public void updateMovement(EntityPlayer player, MovementState movementState) {
        LocalPlayerState state = LocalPlayerState.getState(player);
        if (player.isInWater() && !player.capabilities.isFlying && !player.world.getBlockState(player.getPosition().down()).isFullBlock()) {
            player.eyeHeight = 0.25F;
            if (movementState.shouldMoveForward()) {
                float pitchCos = -MathHelper.cos((float) -Math.toRadians(player.rotationPitch));
                float pitchSin = MathHelper.sin((float) -Math.toRadians(player.rotationPitch));
                float yawCos = MathHelper.cos((float) (-Math.toRadians(player.rotationYaw) - (float) Math.PI));
                float yawSin = MathHelper.sin((float) (-Math.toRadians(player.rotationYaw) - (float) Math.PI));
                float moveX = yawSin * pitchCos;
                float moveZ = yawCos * pitchCos;
                float speed = 0.05F;
                player.motionY += pitchSin * speed;
                player.motionX += moveX * speed;
                player.motionZ += moveZ * speed;
            }
            player.motionY += 0.02;
            state.setSwimming(true);
        } else {
            player.eyeHeight = player.getDefaultEyeHeight();
            state.setSwimming(false);
        }
    }

    @Override
    public void applyRotations(EntityPlayer player, float yaw, float bodyYaw, float partialTicks) {
        float animationTimer = LocalPlayerState.getState(player).getRenderSwimTimer(partialTicks);
        float pitch = -ClientUtils.interpolateRotation(player.prevRotationPitch, player.rotationPitch, partialTicks) - 90;
        GlStateManager.rotate(ClientUtils.interpolateRotation(0.0F, pitch, animationTimer), 1.0F, 0.0F, 0.0F);
    }
}
