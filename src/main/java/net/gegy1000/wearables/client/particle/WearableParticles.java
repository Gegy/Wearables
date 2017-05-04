package net.gegy1000.wearables.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public enum WearableParticles {
    JETPACK_FLAME(new JetpackFlameParticle.Factory());

    IParticleFactory factory;

    WearableParticles(IParticleFactory factory) {
        this.factory = factory;
    }

    public Particle create(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... parameters) {
        return this.factory.createParticle(-1, world, x, y, z, velocityX, velocityY, velocityZ, parameters);
    }

    public void spawn(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... parameters) {
        Particle particle = this.create(world, x, y, z, velocityX, velocityY, velocityZ, parameters);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }
}
