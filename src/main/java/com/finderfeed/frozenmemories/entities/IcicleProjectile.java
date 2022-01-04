package com.finderfeed.frozenmemories.entities;

import com.finderfeed.frozenmemories.helpers.Helpers;
import com.finderfeed.frozenmemories.registries.EntitiesRegistry;
import com.finderfeed.frozenmemories.registries.ParticlesRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class IcicleProjectile extends AbstractHurtingProjectile {

    private boolean shouldBeRemoved = false;

    public IcicleProjectile(EntityType<? extends AbstractHurtingProjectile> proj,Level p_36834_) {
        super(proj, p_36834_);
    }

    @Override
    public void tick() {
        if (this.tickCount >= 1200 || shouldBeRemoved){
            this.discard();
        }
        super.tick();
    }

    @Override
    public boolean hurt(DamageSource p_36839_, float p_36840_) {
        return false;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected float getInertia() {
        return 1;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticlesRegistry.SNOWFLAKE_PARTICLE.get();
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37258_) {

        super.onHitBlock(p_37258_);
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult hit) {
        Entity e = hit.getEntity();
        if (e instanceof LivingEntity entity && (e != getOwner()) ) {


            if (!e.level.isClientSide) {
                entity.hurt(DamageSource.MAGIC, 8);
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1));
            }
            if (e.level.isClientSide) {
                Helpers.createSnowflakeParticleExplosion(level, e.position().add(0, e.getBbHeight() / 2, 0),2,0.04f,1f);
            }
            super.onHitEntity(hit);

        }

        shouldBeRemoved = true;
    }
}
