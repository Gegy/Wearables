package net.gegy1000.wearables.server;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.movement.EntityRemovedListener;
import net.gegy1000.wearables.server.movement.LocalPlayerState;
import net.gegy1000.wearables.server.movement.MovementHandler;
import net.gegy1000.wearables.server.network.SyncConfigMessage;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.gegy1000.wearables.server.wearable.event.ComponentEventHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

@Mod.EventBusSubscriber(modid = Wearables.MODID)
public class ServerEventHandler {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            EntityPlayer player = event.player;
            List<WearableComponentType> components = WearableUtils.getActiveComponents(player);
            for (WearableComponentType component : components) {
                ComponentEventHandler eventHandler = component.getEventHandler();
                if (eventHandler != null) {
                    eventHandler.tick(player);
                }
                MovementHandler movementHandler = component.getMovementHandler();
                if (movementHandler != null && movementHandler.isEnabled(player)) {
                    movementHandler.updateMovement(player, MovementHandler.getState(player));
                }
            }
            boolean hasSpeedModifier = false;

            float speedModifier = 1.0F;
            for (WearableComponentType component : components) {
                ComponentEventHandler eventHandler = component.getEventHandler();
                if (eventHandler != null) {
                    float modifier = eventHandler.getSpeedModifier(player);
                    if (modifier >= 0.0F) {
                        speedModifier *= modifier;
                        hasSpeedModifier = true;
                    }
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
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            List<WearableComponentType> components = WearableUtils.getActiveComponents(player);
            for (WearableComponentType component : components) {
                ComponentEventHandler eventHandler = component.getEventHandler();
                if (eventHandler != null) {
                    if (eventHandler.onJump(player)) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            List<WearableComponentType> components = WearableUtils.getActiveComponents(player);
            for (WearableComponentType component : components) {
                ComponentEventHandler eventHandler = component.getEventHandler();
                if (eventHandler != null) {
                    eventHandler.onFall(player, event);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side == Side.SERVER) {
            MovementHandler.update();
        }
    }

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        EntityPlayer tracker = event.getEntityPlayer();
        if (!tracker.world.isRemote) {
            if (event.getTarget() instanceof EntityPlayer) {
                EntityPlayer tracked = (EntityPlayer) event.getTarget();
                MovementHandler.startTracking(tracker, tracked);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerLoggedInEvent event) {
        MovementHandler.createState(event.player);
        if (!event.player.world.isRemote && event.player instanceof EntityPlayerMP) {
            Wearables.NETWORK_WRAPPER.sendTo(new SyncConfigMessage(), (EntityPlayerMP) event.player);
        }
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerLoggedOutEvent event) {
        MovementHandler.removeState(event.player);
        LocalPlayerState.removeState(event.player);
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        event.getWorld().addEventListener(new EntityRemovedListener());
    }
}
