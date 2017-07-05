package net.gegy1000.wearables.server.util;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.item.WearableItem;
import net.gegy1000.wearables.server.movement.MovementHandler;
import net.gegy1000.wearables.server.wearable.Wearable;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WearableUtils {
    private static final float TO_RADIANS = 0.017453292F;
    private static final EntityEquipmentSlot[] ARMOUR_SLOTS = new EntityEquipmentSlot[] { EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET };

    public static boolean hasSlimArms(Entity entity) {
        if (entity instanceof AbstractClientPlayer) {
            String skinType = ((AbstractClientPlayer) entity).getSkinType();
            return skinType != null && skinType.equals("slim");
        }
        return false;
    }

    public static void dropInventory(World world, BlockPos pos, IItemHandler inventory) {
        WearableUtils.dropInventory(world, pos, inventory, inventory.getSlots());
    }

    public static void dropInventory(World world, BlockPos pos, IItemHandler inventory, int slotCount) {
        float motionScale = 0.05F;
        Random random = world.rand;
        for (int i = 0; i < slotCount; i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                float offsetX = random.nextFloat() * 0.8F + 0.1F;
                float offsetY = random.nextFloat() * 0.8F + 0.1F;
                float offsetZ = random.nextFloat() * 0.8F + 0.1F;
                EntityItem entity = new EntityItem(world, pos.getX() + offsetX, pos.getY() + offsetY, pos.getZ() + offsetZ, stack.copy());
                entity.motionX = random.nextGaussian() * motionScale;
                entity.motionY = random.nextGaussian() * motionScale + 0.2;
                entity.motionZ = random.nextGaussian() * motionScale;
                world.spawnEntity(entity);
            }
        }
    }

    public static AxisAlignedBB calculateUnion(Wearable wearable) {
        AxisAlignedBB union = null;
        for (WearableComponent component : wearable.getComponents()) {
            AxisAlignedBB bounds = component.getType().getBounds();
            if (union == null) {
                union = bounds;
            } else {
                union = union.union(bounds);
            }
        }
        if (union == null) {
            union = new AxisAlignedBB(0, 0, 0, 0, 0, 0);
        }
        return union;
    }

    public static List<WearableComponentType> getActiveComponents(EntityPlayer player) {
        List<WearableComponentType> componentTypes = new ArrayList<>();
        for (EntityEquipmentSlot slot : ARMOUR_SLOTS) {
            ItemStack stack = player.getItemStackFromSlot(slot);
            if (stack.getItem() instanceof WearableItem) {
                Wearable wearable = WearableItem.getWearable(stack);
                componentTypes.addAll(wearable.getComponents().stream().map(WearableComponent::getType).collect(Collectors.toList()));
            }
        }
        return componentTypes;
    }

    public static List<MovementHandler> getMovementHandlers(EntityPlayer player) {
        List<MovementHandler> movementHandlers = new ArrayList<>();
        List<WearableComponentType> componentTypes = WearableUtils.getActiveComponents(player);
        for (WearableComponentType type : componentTypes) {
            MovementHandler movementHandler = type.getMovementHandler();
            if (movementHandler != null) {
                movementHandlers.add(movementHandler);
            }
        }
        return movementHandlers;
    }

    public static boolean hasComponent(EntityPlayer player, WearableComponentType type) {
        for (EntityEquipmentSlot slot : ARMOUR_SLOTS) {
            ItemStack stack = player.getItemStackFromSlot(slot);
            if (stack.getItem() instanceof WearableItem) {
                Wearable wearable = WearableItem.getWearable(stack);
                for (WearableComponent component : wearable.getComponents()) {
                    if (component.getType().equals(type)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Vec3d offsetFront(EntityPlayer player, float front, float pitch) {
        float cos = MathHelper.cos(-player.renderYawOffset * TO_RADIANS - (float) Math.PI) * front;
        float sin = MathHelper.sin(-player.renderYawOffset * TO_RADIANS - (float) Math.PI) * front;
        float pitchCos = -MathHelper.cos(-pitch * TO_RADIANS);
        float pitchSin = MathHelper.sin(-pitch * TO_RADIANS);
        return new Vec3d(player.prevPosX + (sin * pitchCos), player.prevPosY + pitchSin, player.prevPosZ + (cos * pitchCos));
    }

    public static Vec3d offsetSide(EntityPlayer player, float side, float pitch) {
        float yaw = player.renderYawOffset;
        if (side < 0) {
            side = -side;
            yaw += 90;
        } else {
            yaw -= 90;
        }
        float cos = MathHelper.cos(-yaw * TO_RADIANS - (float) Math.PI) * side;
        float sin = MathHelper.sin(-yaw * TO_RADIANS - (float) Math.PI) * side;
        float pitchCos = -MathHelper.cos(-pitch * TO_RADIANS);
        float pitchSin = MathHelper.sin(-pitch * TO_RADIANS);
        return new Vec3d(player.prevPosX + (sin * pitchCos), player.prevPosY + pitchSin, player.prevPosZ + (cos * pitchCos));
    }

    public static Vec3d offset(EntityPlayer player, float front, float side, float pitch) {
        Vec3d frontVec = WearableUtils.offsetFront(player, front, pitch).addVector(-player.prevPosX, -player.prevPosY, -player.prevPosZ);
        return WearableUtils.offsetSide(player, side, pitch).add(frontVec);
    }

    public static boolean onGround(Entity entity) {
        boolean onGround = entity.onGround;
        if (!Wearables.PROXY.isClientPlayer(entity)) {
            double moveY = -0.2;
            double actualMoveY = moveY;
            List collidingEntities = entity.world.getCollisionBoxes(entity, entity.getEntityBoundingBox().offset(0, moveY, 0));
            for (Object collidingEntity : collidingEntities) {
                moveY = ((AxisAlignedBB) collidingEntity).calculateYOffset(entity.getEntityBoundingBox(), moveY);
            }
            onGround = actualMoveY != moveY;
        }
        return onGround || entity.isRiding();
    }

    public static float scaleTimer(int timer, boolean enabled, float partialTicks, float maximum) {
        float offset = timer;
        if (enabled) {
            offset += partialTicks;
        } else {
            offset -= partialTicks;
        }
        return MathHelper.clamp(offset / maximum, 0.0F, 1.0F);
    }

    public static int updateAnimation(int timer, boolean enabled, int maximum) {
        if (enabled) {
            if (timer < maximum) {
                timer++;
            }
        } else {
            if (timer > 0) {
                timer--;
            }
        }
        return timer;
    }
}
