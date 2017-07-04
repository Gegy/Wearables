package net.gegy1000.wearables.client.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class JetpackFlameParticle extends Particle {
    private final float flameScale;

    protected JetpackFlameParticle(World world, double x, double y, double z, double speedX, double speedY, double speedZ) {
        super(world, x, y, z, speedX, speedY, speedZ);
        this.motionX = this.motionX * 0.009 + speedX;
        this.motionY = this.motionY * 0.009 + speedY;
        this.motionZ = this.motionZ * 0.009 + speedZ;
        this.posX += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F;
        this.posY += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F;
        this.posZ += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F;
        this.flameScale = this.particleScale;
        this.particleRed = 1.0F;
        this.particleGreen = 1.0F;
        this.particleBlue = 1.0F;
        this.particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
        this.canCollide = true;
        this.setParticleTextureIndex(48);
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        float f = ((float) this.particleAge + partialTicks) / (float) this.particleMaxAge;
        this.particleScale = this.flameScale * (1.0F - f * f * 0.5F);
        super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
    }

    @Override
    public int getBrightnessForRender(float partialTicks) {
        float ageScale = ((float) this.particleAge + partialTicks) / (float) this.particleMaxAge;
        ageScale = MathHelper.clamp(ageScale, 0.0F, 1.0F);
        int brightness = super.getBrightnessForRender(partialTicks);
        int j = brightness & 255;
        int k = brightness >> 16 & 255;
        j = j + (int) (ageScale * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }
        return j | k << 16;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setExpired();
        }

        this.move(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.95;
        this.motionY *= 0.95;
        this.motionZ *= 0.95;
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory {
        @Override
        public Particle createParticle(int particleID, World world, double x, double y, double z, double speedX, double speedY, double speedZ, int... p_178902_15_) {
            return new JetpackFlameParticle(world, x, y, z, speedX, speedY, speedZ);
        }
    }
}