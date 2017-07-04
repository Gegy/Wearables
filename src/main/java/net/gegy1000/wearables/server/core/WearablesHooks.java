package net.gegy1000.wearables.server.core;

import net.gegy1000.wearables.server.util.WearableUtils;
import net.gegy1000.wearables.server.wearable.component.ComponentTypes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.Map;

public class WearablesHooks {
    public static boolean isPotionActive(EntityLivingBase entity, Potion potion, Map<Potion, PotionEffect> active) {
        if (entity instanceof EntityPlayer && potion == MobEffects.NIGHT_VISION) {
            if (WearableUtils.hasComponent((EntityPlayer) entity, ComponentTypes.NIGHT_VISION_GOGGLES)) {
                return true;
            }
        }
        return active.containsKey(potion);
    }

    public static PotionEffect getActivePotionEffect(EntityLivingBase entity, Potion potion, Map<Potion, PotionEffect> active) {
        if (entity instanceof EntityPlayer && potion == MobEffects.NIGHT_VISION) {
            if (WearableUtils.hasComponent((EntityPlayer) entity, ComponentTypes.NIGHT_VISION_GOGGLES)) {
                return new PotionEffect(MobEffects.NIGHT_VISION, 1000);
            }
        }
        return active.get(potion);
    }
}
