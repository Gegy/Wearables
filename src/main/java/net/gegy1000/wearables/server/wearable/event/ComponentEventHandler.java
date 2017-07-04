package net.gegy1000.wearables.server.wearable.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public class ComponentEventHandler implements IForgeRegistryEntry<ComponentEventHandler> {
    private ResourceLocation registryName;

    @Override
    public ComponentEventHandler setRegistryName(ResourceLocation name) {
        this.registryName = name;
        return this;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return this.registryName;
    }

    @Override
    public Class<ComponentEventHandler> getRegistryType() {
        return ComponentEventHandler.class;
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

    public void onRemoved(EntityPlayer player) {
    }
}
