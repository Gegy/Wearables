package net.gegy1000.wearables.server.movement;

import net.gegy1000.wearables.client.particle.WearableParticles;
import net.gegy1000.wearables.server.item.JetpackFuelItem;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.ilexiconn.llibrary.client.util.ClientUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class JetpackMovementHandler extends MovementHandler {
    @Override
    public void updateMovement(EntityPlayer player, MovementState movementState) {
        LocalPlayerState state = LocalPlayerState.getState(player);
        ItemStack fuel = this.getFuel(player);
        if (!player.world.isRemote) {
            movementState.setHasFuel(fuel != null || player.capabilities.isCreativeMode);
        }
        if (movementState.shouldMoveUp()) {
            if (movementState.hasFuel()) {
                if (fuel != null && !player.world.isRemote && !player.capabilities.isCreativeMode && player.ticksExisted % 10 == 0) {
                    fuel.setItemDamage(fuel.getItemDamage() + 1);
                    if (fuel.getItemDamage() >= fuel.getMaxDamage()) {
                        fuel.stackSize--;
                        player.inventory.setInventorySlotContents(player.inventory.getSlotFor(fuel), null);
                    }
                }
                float angle = 0.0F;
                if (movementState.shouldMoveForward() || movementState.shouldMoveBackward()) {
                    angle = 40.0F;
                }
                float pitchCos = -MathHelper.cos((float) -Math.toRadians(angle - 90.0F));
                float pitchSin = MathHelper.sin((float) -Math.toRadians(angle - 90.0F));
                float yawCos = MathHelper.cos((float) (-Math.toRadians(player.rotationYaw) - (float) Math.PI));
                float yawSin = MathHelper.sin((float) (-Math.toRadians(player.rotationYaw) - (float) Math.PI));
                float moveX = yawSin * pitchCos;
                float moveZ = yawCos * pitchCos;
                float speed = 0.1F;
                player.motionY += pitchSin * speed * 0.5F + 0.05F;
                player.motionX += moveX * speed;
                player.motionZ += moveZ * speed;
                if (player.world.isRemote) {
                    Vec3d right = WearableUtils.offset(player, -0.25F, 0.15F, 0.0F);
                    Vec3d left = WearableUtils.offset(player, -0.25F, -0.15F, 0.0F);
                    Random random = player.world.rand;
                    for (int i = 0; i < 10; i++) {
                        float offsetX = (random.nextFloat() - 0.5F) * 0.1F;
                        float offsetZ = (random.nextFloat() - 0.5F) * 0.1F;
                        float speedX = (random.nextFloat() - 0.5F) * 0.4F;
                        float speedZ = (random.nextFloat() - 0.5F) * 0.4F;
                        WearableParticles.JETPACK_FLAME.spawn(player.world, left.xCoord + offsetX, left.yCoord + 0.6, left.zCoord + offsetZ, speedX, -1.0, speedZ);
                        WearableParticles.JETPACK_FLAME.spawn(player.world, right.xCoord + offsetX, right.yCoord + 0.6, right.zCoord + offsetZ, speedX, -1.0, speedZ);
                    }
                }
            }
        }
        state.setAirborne(!WearableUtils.onGround(player));
    }

    @Override
    public void applyRotations(EntityPlayer player, float yaw, float bodyYaw, float partialTicks) {
        float animationTimer = LocalPlayerState.getState(player).getRenderFlyTimer(partialTicks);
        float limbSwingAmount = player.prevLimbSwingAmount + (player.limbSwingAmount - player.prevLimbSwingAmount) * partialTicks;
        GlStateManager.rotate(ClientUtils.interpolateRotation(0.0F, -limbSwingAmount * 40.0F, animationTimer), 1.0F, 0.0F, 0.0F);
    }

    private ItemStack getFuel(EntityPlayer player) {
        for (ItemStack stack : player.inventory.mainInventory) {
            if (stack != null && stack.getItem() instanceof JetpackFuelItem) {
                return stack;
            }
        }
        return null;
    }
}
