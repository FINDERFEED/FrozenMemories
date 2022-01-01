package com.finderfeed.frozenmemories.items;

import com.finderfeed.frozenmemories.entities.IcicleProjectile;
import com.finderfeed.frozenmemories.misc.ProgressionState;
import com.finderfeed.frozenmemories.registries.EntitiesRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IcicleStaff extends FrozenItem{
    public IcicleStaff(Properties p_41383_) {
        super(p_41383_, ProgressionState.EIGHT);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide){
            IcicleProjectile icicleProjectile = new IcicleProjectile(EntitiesRegistry.ICICLE.get(),world);
            icicleProjectile.setPos(player.position().add(0,player.getBbHeight()/2 + player.getBbHeight()*0.1,0));
            icicleProjectile.setDeltaMovement(player.getLookAngle().multiply(2,2,2));
            icicleProjectile.setOwner(player);
            world.addFreshEntity(icicleProjectile);
            if (!player.isCreative()) {
                player.getCooldowns().addCooldown(this, 80);
            }
            return InteractionResultHolder.success(player.getItemInHand(hand));
        }
        return super.use(world, player, hand);
    }
}
