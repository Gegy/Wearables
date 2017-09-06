package net.gegy1000.wearables.server.movement;

import net.gegy1000.wearables.client.particle.WearableParticles;
import net.gegy1000.wearables.server.config.WearablesConfig;
import net.gegy1000.wearables.server.item.JetpackFuelItem;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.ilexiconn.llibrary.client.util.ClientUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class JetpackMovementHandler extends MovementHandler {
    @Override
    public void updateMovement(EntityPlayer player, MovementState movementState) {
        LocalPlayerState state = LocalPlayerState.getState(player);
        ItemStack fuel = this.getFuel(player);
        if (!player.world.isRemote) {
            movementState.setHasFuel(!fuel.isEmpty() || player.capabilities.isCreativeMode);
        }
        if (movementState.shouldMoveUp()) {
            if (movementState.hasFuel()) {
                if (!player.world.isRemote && !player.capabilities.isCreativeMode && player.ticksExisted % 10 == 0) {
                    fuel.setItemDamage(fuel.getItemDamage() + 1);
                    if (fuel.getItemDamage() >= fuel.getMaxDamage()) {
                        int slotIndex = this.getSlotFor(player.inventory, fuel);
                        fuel.shrink(1);
                        if (fuel.isEmpty()) {
                            player.inventory.setInventorySlotContents(slotIndex, new ItemStack(Items.GLASS_BOTTLE));
                        }
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
                        WearableParticles.JETPACK_FLAME.spawn(player.world, left.x + offsetX, left.y + 0.6, left.z + offsetZ, speedX, -1.0, speedZ);
                        WearableParticles.JETPACK_FLAME.spawn(player.world, right.x + offsetX, right.y + 0.6, right.z + offsetZ, speedX, -1.0, speedZ);
                    }
                }
            }
        }
        state.setAirborne(!WearableUtils.onGround(player));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void applyRotations(EntityPlayer player, float partialTicks) {
        float animationTimer = LocalPlayerState.getState(player).getRenderFlyTimer(partialTicks);
        float limbSwingAmount = player.prevLimbSwingAmount + (player.limbSwingAmount - player.prevLimbSwingAmount) * partialTicks;
        GlStateManager.rotate(ClientUtils.interpolateRotation(0.0F, -limbSwingAmount * 40.0F, animationTimer), 1.0F, 0.0F, 0.0F);
    }

    @Override
    public boolean isEnabled(EntityPlayer player) {
        return WearablesConfig.getGlobalRestrictions(player.world).allowJetpackFlight;
    }

    private int getSlotFor(InventoryPlayer inventory, ItemStack stack) {
        for (int index = 0; index < inventory.mainInventory.size(); ++index) {
            ItemStack inventoryStack = inventory.mainInventory.get(index);
            if (!inventoryStack.isEmpty() && this.stackEqualExact(stack, inventoryStack)) {
                return index;
            }
        }
        return -1;
    }

    private boolean stackEqualExact(ItemStack firstStack, ItemStack secondStack) {
        return firstStack.getItem() == secondStack.getItem() && (!firstStack.getHasSubtypes() || firstStack.getMetadata() == secondStack.getMetadata()) && ItemStack.areItemStackTagsEqual(firstStack, secondStack);
    }

    private ItemStack getFuel(EntityPlayer player) {
        for (ItemStack stack : player.inventory.mainInventory) {
            if (stack.getItem() instanceof JetpackFuelItem) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
