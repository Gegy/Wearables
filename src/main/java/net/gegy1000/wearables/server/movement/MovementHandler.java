package net.gegy1000.wearables.server.movement;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.network.UpdateMovementMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public abstract class MovementHandler {
    public static final Map<UUID, MovementState> MOVEMENT_STATES = new HashMap<>();

    public abstract void updateMovement(EntityPlayer player, MovementState movementState);

    public abstract void applyRotations(EntityPlayer player, float yaw, float bodyYaw, float partialTicks);

    public static void createState(EntityPlayer player) {
        MOVEMENT_STATES.put(player.getUniqueID(), new MovementState(player));
    }

    public static void removeState(EntityPlayer player) {
        MOVEMENT_STATES.remove(player.getUniqueID());
    }

    public static void update() {
        for (Map.Entry<UUID, MovementState> entry : MOVEMENT_STATES.entrySet()) {
            MovementState state = entry.getValue();
            if (state.isDirty()) {
                World world = state.getPlayer().getEntityWorld();
                if (world instanceof WorldServer) {
                    WorldServer worldServer = (WorldServer) world;
                    Set<? extends EntityPlayer> trackers = worldServer.getEntityTracker().getTrackingPlayers(state.getPlayer());
                    UpdateMovementMessage message = new UpdateMovementMessage(state, true);
                    for (EntityPlayer tracker : trackers) {
                        Wearables.NETWORK_WRAPPER.sendTo(message, (EntityPlayerMP) tracker);
                    }
                    Wearables.NETWORK_WRAPPER.sendTo(message, (EntityPlayerMP) state.getPlayer());
                }
                state.unmarkDirty();
            }
        }
    }

    public static void startTracking(EntityPlayer tracker, EntityPlayer tracked) {
        MovementState state = MOVEMENT_STATES.get(tracked.getUniqueID());
        if (state != null) {
            Wearables.NETWORK_WRAPPER.sendTo(new UpdateMovementMessage(state, true), (EntityPlayerMP) tracker);
        }
    }
}
