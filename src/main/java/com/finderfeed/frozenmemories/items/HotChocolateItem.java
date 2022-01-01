package com.finderfeed.frozenmemories.items;

import com.finderfeed.frozenmemories.misc.FrozenMemoriesItem;
import com.finderfeed.frozenmemories.misc.ProgressionState;
import com.finderfeed.frozenmemories.registries.BlocksRegistry;
import com.finderfeed.frozenmemories.registries.ItemsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;


public class HotChocolateItem extends FrozenBlockItem  {
    public HotChocolateItem(Block block,Properties p_41383_) {
        super(block,p_41383_, ProgressionState.ONE);
    }


    @Override
    public ItemStack finishUsingItem(ItemStack p_41409_, Level p_41410_, LivingEntity entity) {
        if (entity instanceof ServerPlayer player){
            boolean flag = player.addItem(ItemsRegistry.MUG.get().getDefaultInstance());
            if (!flag){
                ItemEntity item = new ItemEntity(player.level,player.getX(),player.getY(),player.getZ(),ItemsRegistry.MUG.get().getDefaultInstance());
                player.level.addFreshEntity(item);
            }
        }
        return super.finishUsingItem(p_41409_, p_41410_, entity);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41358_) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }


    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

}
