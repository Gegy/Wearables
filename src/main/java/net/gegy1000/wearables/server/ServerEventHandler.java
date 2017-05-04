package net.gegy1000.wearables.server;

import net.gegy1000.wearables.server.movement.EntityRemovedListener;
import net.gegy1000.wearables.server.movement.LocalPlayerState;
import net.gegy1000.wearables.server.movement.MovementHandler;
import net.gegy1000.wearables.server.movement.MovementState;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

public class ServerEventHandler {
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            EntityPlayer player = event.player;
            List<WearableComponentType> components = WearableUtils.getActiveComponents(player);
            for (WearableComponentType component : components) {
                component.tick(player);
                MovementHandler movementHandler = component.getMovementHandler();
                if (movementHandler != null) {
                    MovementState movementState = MovementHandler.MOVEMENT_STATES.computeIfAbsent(player.getUniqueID(), uuid -> new MovementState(player));
                    movementHandler.updateMovement(player, movementState);
                }
            }
            boolean hasSpeedModifier = false;
            float speedModifier = 1.0F;
            for (WearableComponentType component : components) {
                if (component.getSpeedModifier(player) >= 0.0F) {
                    speedModifier *= component.getSpeedModifier(player);
                    hasSpeedModifier = true;
                }
            }
            if (hasSpeedModifier) {
                player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(speedModifier * 0.1);
            }
            LocalPlayerState state = LocalPlayerState.getState(player);
            state.update();
            state.updateEquipment(components);
        }
    }

    @SubscribeEvent
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            List<WearableComponentType> components = WearableUtils.getActiveComponents(player);
            for (WearableComponentType component : components) {
                if (component.onJump(player)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            List<WearableComponentType> components = WearableUtils.getActiveComponents(player);
            for (WearableComponentType component : components) {
                component.onFall(player, event);
            }
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side == Side.SERVER) {
            MovementHandler.update();
        }
    }

    @SubscribeEvent
    public void onStartTracking(PlayerEvent.StartTracking event) {
        EntityPlayer tracker = event.getEntityPlayer();
        if (!tracker.world.isRemote) {
            if (event.getTarget() instanceof EntityPlayer) {
                EntityPlayer tracked = (EntityPlayer) event.getTarget();
                MovementHandler.startTracking(tracker, tracked);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerLoggedInEvent event) {
        MovementHandler.createState(event.player);
    }

    @SubscribeEvent
    public void onPlayerLogout(PlayerLoggedOutEvent event) {
        MovementHandler.removeState(event.player);
        LocalPlayerState.removeState(event.player);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        event.getWorld().addEventListener(new EntityRemovedListener());
    }
}
