package net.gegy1000.wearables.server.wearable.component.chest;

import net.gegy1000.wearables.server.wearable.component.ComponentProperty;
import net.gegy1000.wearables.server.movement.JetpackMovementHandler;
import net.gegy1000.wearables.server.movement.MovementHandler;
import net.gegy1000.wearables.server.wearable.WearableCategory;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class JetpackComponent extends WearableComponentType {
    private static final ItemStack[] INGREDIENTS = new ItemStack[] { new ItemStack(Items.IRON_INGOT, 60), new ItemStack(Items.BLAZE_POWDER, 20), new ItemStack(Items.LEATHER, 10), new ItemStack(Items.STRING, 5) };
    private static final JetpackMovementHandler MOVEMENT_HANDLER = new JetpackMovementHandler();

    @Override
    public String getIdentifier() {
        return "jetpack";
    }

    @Override
    public WearableCategory getCategory() {
        return WearableCategory.CHEST_BACK;
    }

    @Override
    public ItemStack[] getIngredients() {
        return INGREDIENTS;
    }

    @Override
    public int getLayerCount() {
        return 3;
    }

    @Override
    public boolean canColour(int layer) {
        return layer != 2;
    }

    @Override
    public int getSupportedProperties() {
        return ComponentProperty.OFFSET_Y;
    }

    @Override
    public float getMinimum(int property) {
        if (property == ComponentProperty.OFFSET_Y) {
            return -0.4F;
        }
        return super.getMinimum(property);
    }

    @Override
    public float getMaximum(int property) {
        if (property == ComponentProperty.OFFSET_Y) {
            return 0.1F;
        }
        return super.getMaximum(property);
    }

    @Override
    public MovementHandler getMovementHandler() {
        return MOVEMENT_HANDLER;
    }

    @Override
    public void onFall(EntityPlayer player, LivingFallEvent event) {
        float distance = (float) (player.lastTickPosY - player.posY) * 10.0F;
        event.setDistance(distance);
    }

    @Override
    public boolean hasTooltip() {
        return true;
    }
}
