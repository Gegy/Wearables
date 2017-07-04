package net.gegy1000.wearables.server.movement;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.config.WearablesConfig;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.ilexiconn.llibrary.client.util.ClientUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class WingsMovementHandler extends MovementHandler {
    @Override
    public void updateMovement(EntityPlayer player, MovementState movementState) {
        LocalPlayerState state = LocalPlayerState.getState(player);
        boolean onGround = WearableUtils.onGround(player);
        boolean flying = player.capabilities.isFlying;
        if (Wearables.PROXY.isClientPlayer(player)) {
            if (state.canFly() && !flying) {
                state.setFlyToggle(true);
            } else if (onGround || player.isSneaking() || flying) {
                state.setFlyToggle(false);
            }
        }
        state.setAirborne(!onGround && !flying && !player.isInWater());
        if (state.isAirborne() && state.isFlyToggle()) {
            player.eyeHeight = 0.25F;
        } else {
            player.eyeHeight = player.getDefaultEyeHeight();
        }
        if (state.isAirborne() && state.isFlyToggle()) {
            float speed = 0.03F;
            if (movementState.shouldMoveUp() || movementState.shouldMoveForward()) {
                speed = 0.07F;
            }
            float pitchCos = -MathHelper.cos((float) -Math.toRadians(player.rotationPitch - 20.0F));
            float pitchSin = MathHelper.sin((float) -Math.toRadians(player.rotationPitch - 20.0F));
            float yaw = player.rotationYaw - (player.renderYawOffset - player.rotationYaw) * 0.3F;
            float yawCos = MathHelper.cos((float) (-Math.toRadians(yaw) - (float) Math.PI));
            float yawSin = MathHelper.sin((float) (-Math.toRadians(yaw) - (float) Math.PI));
            float moveX = yawSin * pitchCos;
            float moveZ = yawCos * pitchCos;
            player.motionY += pitchSin * speed + 0.05F;
            player.motionX += moveX * speed;
            player.motionZ += moveZ * speed;
        }
        if (player.motionY < 0.0) {
            player.motionY *= 0.9;
        }
    }

    @Override
    public void applyRotations(EntityPlayer player, float partialTicks) {
        LocalPlayerState state = LocalPlayerState.getState(player);
        float animationTimer = state.getRenderFlyTimer(partialTicks) * state.getRenderFlyToggleTimer(partialTicks);
        float roll = ClientUtils.interpolateRotation(player.prevRenderYawOffset - player.prevRotationYaw, player.renderYawOffset - player.rotationYaw, partialTicks) * 1.1F;
        GlStateManager.rotate(ClientUtils.interpolateRotation(0.0F, roll, animationTimer), 0.0F, 0.0F, 1.0F);
        float pitch = -ClientUtils.interpolateRotation(player.prevRotationPitch, player.rotationPitch, partialTicks) - 90;
        GlStateManager.rotate(ClientUtils.interpolateRotation(0.0F, pitch, animationTimer), 1.0F, 0.0F, 0.0F);
    }

    @Override
    public boolean isEnabled(EntityPlayer player) {
        return WearablesConfig.getGlobalRestrictions(player.world).allowWingFlight;
    }
}
