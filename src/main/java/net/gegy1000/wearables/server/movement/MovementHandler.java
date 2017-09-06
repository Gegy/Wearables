package net.gegy1000.wearables.server.movement;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.network.UpdateMovementMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public abstract class MovementHandler implements IForgeRegistryEntry<MovementHandler> {
    private static final Map<UUID, MovementState> MOVEMENT_STATES = new HashMap<>();

    private ResourceLocation registryName;

    @Override
    public MovementHandler setRegistryName(ResourceLocation name) {
        this.registryName = name;
        return this;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return this.registryName;
    }

    @Override
    public Class<MovementHandler> getRegistryType() {
        return MovementHandler.class;
    }

    public abstract void updateMovement(EntityPlayer player, MovementState movementState);

    @SideOnly(Side.CLIENT)
    public abstract void applyRotations(EntityPlayer player, float partialTicks);

    public boolean isEnabled(EntityPlayer player) {
        return true;
    }

    public static MovementState createState(EntityPlayer player) {
        MovementState state = new MovementState(player);
        MOVEMENT_STATES.put(player.getUniqueID(), state);
        return state;
    }

    public static void removeState(EntityPlayer player) {
        MOVEMENT_STATES.remove(player.getUniqueID());
    }

    public static MovementState getState(EntityPlayer player) {
        return MOVEMENT_STATES.computeIfAbsent(player.getUniqueID(), uuid -> new MovementState(player));
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
