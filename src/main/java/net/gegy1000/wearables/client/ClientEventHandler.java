package net.gegy1000.wearables.client;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.movement.LocalPlayerState;
import net.gegy1000.wearables.server.movement.MovementHandler;
import net.gegy1000.wearables.server.movement.MovementState;
import net.gegy1000.wearables.server.network.UpdateMovementMessage;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.gegy1000.wearables.server.wearable.component.ComponentRegistry;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.ilexiconn.llibrary.client.event.PlayerModelEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {
    private static final Minecraft MC = Minecraft.getMinecraft();

    public static int ticks = 0;

    private MovementState movementState;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        ticks++;
        EntityPlayer player = MC.player;
        if (player != null) {
            if (this.movementState == null || this.movementState.getPlayer() != player) {
                this.movementState = new MovementState(player);
                MovementHandler.MOVEMENT_STATES.put(player.getUniqueID(), this.movementState);
            }
            this.movementState.unmarkDirty();
            if (WearableUtils.getMovementHandlers(MC.player).isEmpty()) {
                this.movementState.setMoveUp(false);
                this.movementState.setMoveForward(false);
                this.movementState.setMoveBackward(false);
            } else {
                LocalPlayerState state = LocalPlayerState.getState(player);
                boolean jumping = MC.gameSettings.keyBindJump.isKeyDown() && !MC.player.capabilities.isFlying;
                state.setJumping(jumping);
                this.movementState.setFlyToggle(state.isFlyToggle());
                this.movementState.setMoveUp(jumping && state.canFly());
                this.movementState.setMoveForward(MC.gameSettings.keyBindForward.isKeyDown());
                this.movementState.setMoveBackward(MC.gameSettings.keyBindBack.isKeyDown());
            }
            if (this.movementState.isDirty()) {
                Wearables.NETWORK_WRAPPER.sendToServer(new UpdateMovementMessage(this.movementState, false));
            }
        }
    }

    @SubscribeEvent
    public void setFogDensity(EntityViewRenderEvent.FogDensity event) {
        if (WearableUtils.hasComponent(MC.player, ComponentRegistry.NIGHT_VISION_GOGGLES)) {
            GlStateManager.setFog(GlStateManager.FogMode.EXP);
            event.setDensity(0.04F);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void setFogColors(EntityViewRenderEvent.FogColors event) {
        if (WearableUtils.hasComponent(MC.player, ComponentRegistry.NIGHT_VISION_GOGGLES)) {
            float brightnessFactor = MC.world.getLightFor(EnumSkyBlock.SKY, MC.player.getPosition()) / 15.0F * MC.world.getSunBrightnessFactor(1.0F);
            float inverseFactor = 1.0F - brightnessFactor;
            event.setRed(0.05F * inverseFactor + event.getRed() * brightnessFactor);
            event.setGreen(0.5F * inverseFactor + event.getGreen() * brightnessFactor);
            event.setBlue(0.1F * inverseFactor + event.getBlue() * brightnessFactor);
        }
    }

    @SubscribeEvent
    public void setRotationAngles(PlayerModelEvent.SetRotationAngles event) {
        EntityPlayer player = event.getEntityPlayer();
        if (!WearableUtils.onGround(player)) {
            LocalPlayerState state = LocalPlayerState.getState(player);
            ModelPlayer model = event.getModel();
            List<WearableComponentType> components = WearableUtils.getActiveComponents(player);
            if (components.contains(ComponentRegistry.JETPACK) || components.contains(ComponentRegistry.WINGS)) {
                if (!player.isInWater() && state.isAirborne()) {
                    model.bipedRightArm.rotateAngleX = 0.0F;
                    model.bipedLeftArm.rotateAngleX = 0.0F;
                    model.bipedRightLeg.rotateAngleX = 0.0F;
                    model.bipedLeftLeg.rotateAngleX = 0.0F;
                    model.bipedRightArmwear.rotateAngleX = 0.0F;
                    model.bipedLeftArmwear.rotateAngleX = 0.0F;
                    model.bipedRightLegwear.rotateAngleX = 0.0F;
                    model.bipedLeftLegwear.rotateAngleX = 0.0F;
                    if (state.isFlyToggle() && components.contains(ComponentRegistry.WINGS)) {
                        model.bipedHead.rotateAngleX = -1.55F;
                        model.bipedHeadwear.rotateAngleX = -1.55F;
                    }
                }
            } else if (components.contains(ComponentRegistry.FLIPPERS)) {
                if (state.isSwimming()) {
                    model.bipedHead.rotateAngleX = -1.55F;
                    model.bipedHeadwear.rotateAngleX = -1.55F;
                }
            }
        }
    }
}
