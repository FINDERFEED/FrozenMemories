package com.finderfeed.frozenmemories.entities;

import com.finderfeed.frozenmemories.blocks.MemoryCrack;
import com.finderfeed.frozenmemories.blocks.tileentities.MemoryTowerTile;
import com.finderfeed.frozenmemories.events.ForgeEventHandler;
import com.finderfeed.frozenmemories.registries.BlocksRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SerializableUUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class FrostedZombie extends Monster {
    public static final int MAX_ERUPTING_TIME = 50;

    private int erruptingTicks = 0;
    private boolean ERUPTING = false;

    public FrostedZombie(EntityType<? extends Monster> type, Level world) {
        super(type, world);
    }


    public FrostedZombie(EntityType<? extends Monster> type, Level world,boolean erupting) {
        super(type, world);
        this.ERUPTING = erupting;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH,35f)
                .add(Attributes.FOLLOW_RANGE, 35.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23F)
                .add(Attributes.ATTACK_DAMAGE, 5.5D)
                .add(Attributes.ARMOR, 7.0D);
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.25D, false));
        this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, true, 4, ()->true));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(ZombifiedPiglin.class));
        this.goalSelector.addGoal(5,new DestroyMemoryTowerGoal(this,1,20,10));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        return ERUPTING && (erruptingTicks < MAX_ERUPTING_TIME) ? true : super.hurt(p_21016_,p_21017_);
    }



    @Override
    protected boolean isSunBurnTick() {
        return false;
    }

    @Override
    public boolean doHurtTarget(Entity ent) {
        if (!level.isClientSide && ent instanceof LivingEntity living){
            if (level.random.nextDouble() >= 0.5){
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,200,1));
                living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,200,0));
            }
        }
        return super.doHurtTarget(ent);
    }


    @Override
    public void tick() {
        super.tick();
        if (ERUPTING && (erruptingTicks <= MAX_ERUPTING_TIME) ){
            goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
            erruptingTicks++;
            this.setDeltaMovement(0,0.03,0);
        }
    }


    @Override
    public boolean canCollideWith(Entity entity) {
        return ERUPTING && (erruptingTicks < MAX_ERUPTING_TIME) ? false : super.canCollideWith(entity);
    }

    @Override
    public boolean save(CompoundTag tag) {
        tag.putBoolean("eruptingBool",ERUPTING);
        tag.putInt("erupting",erruptingTicks);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.ERUPTING = tag.getBoolean("eruptingBool");
        this.erruptingTicks = tag.getInt("erupting");
        super.load(tag);
    }

    protected void playStepSound(BlockPos p_34316_, BlockState p_34317_) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_34327_) {
        return SoundEvents.ZOMBIE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }


    protected SoundEvent getStepSound() {
        return SoundEvents.ZOMBIE_STEP;
    }

    @Override
    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }



    public static class DestroyMemoryTowerGoal extends MoveToBlockGoal{
        public DestroyMemoryTowerGoal(PathfinderMob mob, double p_25610_, int p_25611_,int g) {
            super(mob, p_25610_, p_25611_,g);
        }
        @Override
        protected boolean isValidTarget(LevelReader reader, BlockPos pos) {
            return reader.getBlockState(pos).is(BlocksRegistry.MEMORY_TOWER.get());
        }


        @Override
        public double acceptedDistance() {
            return 1.75d;
        }

        @Override
        public boolean canUse() {
            if (mob.level.dimension() == ForgeEventHandler.MEMORY){
                return super.canUse();
            }
            return false;
        }
        @Override
        public void tick() {
            if (isReachedTarget()){
                Level level = mob.level;
                if (level.getBlockEntity(blockPos) instanceof MemoryTowerTile tower){
                    if (tower.getRemainingHP() < 0) {
                        mob.level.getEntitiesOfClass(Player.class, new AABB(-48, -48, -48, 48, 48, 48).move(mob.blockPosition())).forEach((player) -> {
                            if (player instanceof ServerPlayer serverPlayer && mob.level.dimension() == ForgeEventHandler.MEMORY) {
                                MemoryCrack.teleportPlayerBack(serverPlayer, true);
                            }
                        });
                    }else{
                        if (level.getGameTime() % 10 == 0){
                            tower.damage();
                        }
                    }
                }

            }
            super.tick();
        }
    }
}
