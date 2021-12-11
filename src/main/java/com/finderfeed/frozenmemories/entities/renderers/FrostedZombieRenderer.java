package com.finderfeed.frozenmemories.entities.renderers;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.entities.FrostedZombie;
import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;

public class FrostedZombieRenderer extends HumanoidMobRenderer<FrostedZombie, AbstractZombieModel<FrostedZombie>> {

    private static final ResourceLocation ZOMBIE_LOCATION = new ResourceLocation( FrozenMemories.MOD_ID,"textures/entities/frosted_zombie.png");

    public FrostedZombieRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new FrostedZombieModel(ctx.bakeLayer(ModelLayers.ZOMBIE)), 0.5f);
        this.addLayer(new HumanoidArmorLayer<>(this,
                new FrostedZombieModel(ctx.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
                new FrostedZombieModel(ctx.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR))));
    }


    @Override
    public ResourceLocation getTextureLocation(FrostedZombie p_114891_) {
        return ZOMBIE_LOCATION;
    }

    public static class FrostedZombieModel extends AbstractZombieModel<FrostedZombie>{

        protected FrostedZombieModel(ModelPart p_170337_) {
            super(p_170337_);
        }

        @Override
        public boolean isAggressive(FrostedZombie frostedZombie) {
            return false;
        }
    }
}
