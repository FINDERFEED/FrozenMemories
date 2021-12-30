package com.finderfeed.frozenmemories.entities;

import com.finderfeed.frozenmemories.helpers.Helpers;
import com.finderfeed.frozenmemories.registries.BlocksRegistry;
import com.finderfeed.frozenmemories.registries.EntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Consumer;

public class FrozenZombieMeteorite extends AbstractHurtingProjectile {

    private final Consumer<FrostedZombie> commands;


    public FrozenZombieMeteorite(EntityType<? extends AbstractHurtingProjectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
        this.commands = null;

    }

    public FrozenZombieMeteorite(Level p_37249_,Consumer<FrostedZombie> commands) {
        super(EntitiesRegistry.FROZEN_ZOMBIE_METEORITE.get(), p_37249_);
        this.commands = commands;


    }
    public FrozenZombieMeteorite(Level p_37249_) {
        super(EntitiesRegistry.FROZEN_ZOMBIE_METEORITE.get(), p_37249_);
        this.commands = null;
    }


    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onHitBlock(BlockHitResult res) {
        BlockPos pos = res.getBlockPos();
        if (!level.getBlockState(pos).is(BlocksRegistry.MEMORY_WALL.get())){
            super.onHitBlock(res);
            if (!level.isClientSide) {
                this.spawnZombies(pos);
                this.discard();
            }
        }

    }
    @Override
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        return false;
    }

    public void spawnZombies(BlockPos hitpos){
        level.explode(null,position().x,position().y,position().z,1, Explosion.BlockInteraction.NONE);
        int count = level.random.nextInt(3)+1;
        for (int i = 0; i < count;i++){
            BlockPos pos = hitpos.above(1).offset(level.random.nextInt(4),0,level.random.nextInt(4));
            FrostedZombie zombie = new FrostedZombie(EntitiesRegistry.FROSTED_ZOMBIE.get(),level);
            zombie.setPos(Helpers.blockCenter(pos));
            if (commands != null) {
                commands.accept(zombie);
            }
            level.addFreshEntity(zombie);
        }
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    protected float getInertia() {
        return 1;
    }



    @Override
    protected void defineSynchedData() {

    }
}
