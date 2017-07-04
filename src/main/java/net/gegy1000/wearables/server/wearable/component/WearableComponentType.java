package net.gegy1000.wearables.server.wearable.component;

import net.gegy1000.wearables.server.movement.MovementHandler;
import net.gegy1000.wearables.server.wearable.WearableCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public abstract class WearableComponentType {
    public abstract String getIdentifier();

    public abstract WearableCategory getCategory();

    public abstract ItemStack[] getIngredients();

    public int getLayerCount() {
        return 1;
    }

    public boolean canColour(int layer) {
        return true;
    }

    public float getColourX(int layer) {
        return 0.0F;
    }

    public float getColourY(int layer) {
        return 0.0F;
    }

    public boolean compatibleWith(WearableComponentType component) {
        return component.getCategory() != this.getCategory();
    }

    public int getSupportedProperties() {
        return 0;
    }

    public float getMinimum(int property) {
        return 0.0F;
    }

    public float getMaximum(int property) {
        return 0.0F;
    }

    public float getSpeedModifier(EntityPlayer player) {
        return -1.0F;
    }

    public void tick(EntityPlayer player) {
    }

    public boolean onJump(EntityPlayer player) {
        return false;
    }

    public void onFall(EntityPlayer player, LivingFallEvent event) {
    }

    public int getDepthStrideModifier(EntityPlayer player) {
        return -1;
    }

    public MovementHandler getMovementHandler() {
        return null;
    }

    public boolean hasTooltip() {
        return false;
    }

    public void onRemoved(EntityPlayer player) {
    }
}
