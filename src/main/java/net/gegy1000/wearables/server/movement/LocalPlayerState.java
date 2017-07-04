package net.gegy1000.wearables.server.movement;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.gegy1000.wearables.server.wearable.event.ComponentEventHandler;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalPlayerState {
    private static final Map<EntityPlayer, LocalPlayerState> CLIENT_PLAYER_STATES = new HashMap<>();
    private static final Map<EntityPlayer, LocalPlayerState> SERVER_PLAYER_STATES = new HashMap<>();

    private final EntityPlayer player;
    private List<WearableComponentType> lastEquipment;

    private int swimAnimation;
    private boolean swimming;

    private int airborneAnimation;
    private boolean airborne;

    private int jumpDelay;
    private boolean jumping;

    private int flyToggleAnimation;
    private boolean flyToggle;

    public LocalPlayerState(EntityPlayer player) {
        this.player = player;
    }

    public void updateEquipment(List<WearableComponentType> equipment) {
        if (this.lastEquipment != null) {
            for (WearableComponentType component : this.lastEquipment) {
                ComponentEventHandler eventHandler = component.getEventHandler();
                if (eventHandler != null) {
                    if (!equipment.contains(component)) {
                        eventHandler.onRemoved(this.player);
                    }
                }
            }
        }
        this.lastEquipment = equipment;
    }

    public void update() {
        if (this.player.world.isRemote) {
            this.swimAnimation = WearableUtils.updateAnimation(this.swimAnimation, this.swimming, 10);
            this.airborneAnimation = WearableUtils.updateAnimation(this.airborneAnimation, this.airborne, 5);
        }
        this.jumpDelay = WearableUtils.updateAnimation(this.jumpDelay, this.jumping, 3);
        this.flyToggleAnimation = WearableUtils.updateAnimation(this.flyToggleAnimation, this.flyToggle, 5);
    }

    public void setSwimming(boolean swimming) {
        this.swimming = swimming;
    }

    public void setAirborne(boolean airborne) {
        this.airborne = airborne;
    }

    public void setFlyToggle(boolean flyToggle) {
        this.flyToggle = flyToggle;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean canFly() {
        return this.jumpDelay >= 3 || (this.player.world.isRemote && !Wearables.PROXY.isClientPlayer(this.player));
    }

    public boolean isAirborne() {
        return this.airborne;
    }

    public boolean isSwimming() {
        return this.swimming;
    }

    public boolean isFlyToggle() {
        return this.flyToggle;
    }

    public float getRenderSwimTimer(float partialTicks) {
        return WearableUtils.scaleTimer(this.swimAnimation, this.swimming, partialTicks, 10.0F);
    }

    public float getRenderFlyTimer(float partialTicks) {
        return WearableUtils.scaleTimer(this.airborneAnimation, this.airborne, partialTicks, 5.0F);
    }

    public float getRenderFlyToggleTimer(float partialTicks) {
        return WearableUtils.scaleTimer(this.flyToggleAnimation, this.flyToggle, partialTicks, 5.0F);
    }

    public static LocalPlayerState getState(EntityPlayer player) {
        return LocalPlayerState.getStates(player).computeIfAbsent(player, LocalPlayerState::new);
    }

    public static void removeState(EntityPlayer player) {
        LocalPlayerState.getStates(player).remove(player);
    }

    private static Map<EntityPlayer, LocalPlayerState> getStates(EntityPlayer player) {
        return player.world.isRemote ? CLIENT_PLAYER_STATES : SERVER_PLAYER_STATES;
    }
}
