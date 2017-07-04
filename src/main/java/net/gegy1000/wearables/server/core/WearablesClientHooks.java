package net.gegy1000.wearables.server.core;

import net.gegy1000.wearables.server.movement.MovementHandler;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class WearablesClientHooks {
    private static final ThreadLocal<ItemStack> RENDERED_STACK = new ThreadLocal<>();
    private static final ThreadLocal<ItemCameraTransforms.TransformType> CAMERA_TRANSFORM = new ThreadLocal<>();

    public static void updateRenderedStack(ItemStack stack) {
        RENDERED_STACK.set(stack);
    }

    public static void updateCameraTransform(ItemCameraTransforms.TransformType transformType) {
        CAMERA_TRANSFORM.set(transformType);
    }

    public static ItemStack getRenderedStack() {
        return RENDERED_STACK.get();
    }

    public static ItemCameraTransforms.TransformType getCameraTransform() {
        return CAMERA_TRANSFORM.get();
    }

    public static void applyRotations(RenderPlayer renderer, AbstractClientPlayer player, float yaw, float bodyYaw, float partialTicks) {
        List<MovementHandler> movementHandlers = WearableUtils.getMovementHandlers(player);
        for (MovementHandler movementHandler : movementHandlers) {
            movementHandler.applyRotations(player, yaw, bodyYaw, partialTicks);
        }
    }
}
