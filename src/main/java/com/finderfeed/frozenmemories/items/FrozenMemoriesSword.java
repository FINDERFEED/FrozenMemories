package com.finderfeed.frozenmemories.items;

import com.finderfeed.frozenmemories.helpers.ClientHelpers;
import com.finderfeed.frozenmemories.misc.FrozenMemoriesItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.util.thread.EffectiveSide;

public class FrozenMemoriesSword extends SwordItem implements FrozenMemoriesItem {

    private final int playerStage;
    public FrozenMemoriesSword(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_,int playerStage) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
        this.playerStage = playerStage;
    }



    @Override
    public boolean hurtEnemy(ItemStack item, LivingEntity target, LivingEntity attacker) {
        Level world = target.getLevel();
        if (!world.isClientSide){
            if (world.random.nextDouble() >= 0.4) {
                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 0));
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1));
            }
        }
        return super.hurtEnemy(item, target, attacker);
    }

    @Override
    public Component getName(ItemStack p_41458_) {
        if (EffectiveSide.get().isClient()){
            return ClientHelpers.getNameBasedOnNeededLevel(this);
        }
        return super.getName(p_41458_);
    }

    @Override
    public int getNeededPlayerLevel() {
        return playerStage;
    }

    @Override
    public Item getFrozenItem() {
        return this;
    }
}
