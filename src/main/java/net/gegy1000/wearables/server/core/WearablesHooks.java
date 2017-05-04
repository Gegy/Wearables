package net.gegy1000.wearables.server.core;

import net.gegy1000.wearables.server.util.WearableUtils;
import net.gegy1000.wearables.server.wearable.component.ComponentRegistry;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.List;
import java.util.Map;

public class WearablesHooks {
    public static int getDepthStriderModifier(EntityLivingBase entity, int strider) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            List<WearableComponentType> components = WearableUtils.getActiveComponents(player);
            int modifier = 1;
            for (WearableComponentType component : components) {
                if (component.getDepthStrideModifier(player) >= 0) {
                    modifier *= component.getDepthStrideModifier(player);
                }
            }
            return strider + modifier;
        }
        return strider;
    }

    public static boolean isPotionActive(EntityLivingBase entity, Potion potion, Map<Potion, PotionEffect> active) {
        if (entity instanceof EntityPlayer && potion == MobEffects.NIGHT_VISION) {
            if (WearableUtils.hasComponent((EntityPlayer) entity, ComponentRegistry.NIGHT_VISION_GOGGLES)) {
                return true;
            }
        }
        return active.containsKey(potion);
    }

    public static PotionEffect getActivePotionEffect(EntityLivingBase entity, Potion potion, Map<Potion, PotionEffect> active) {
        if (entity instanceof EntityPlayer && potion == MobEffects.NIGHT_VISION) {
            if (WearableUtils.hasComponent((EntityPlayer) entity, ComponentRegistry.NIGHT_VISION_GOGGLES)) {
                return new PotionEffect(MobEffects.NIGHT_VISION, 1000);
            }
        }
        return active.get(potion);
    }
}
