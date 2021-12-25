package com.finderfeed.frozenmemories.worldgen_features;


import com.finderfeed.frozenmemories.registries.BlocksRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;


public class FrozenZombieTrapsFeature extends Feature<NoneFeatureConfiguration> {
    public FrozenZombieTrapsFeature(Codec<NoneFeatureConfiguration> p_65786_) {
        super(p_65786_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos pos = ctx.origin();
        BlockPos realPos = new BlockPos(pos.getX(),level.getHeight(Heightmap.Types.WORLD_SURFACE_WG,pos.getX(),pos.getZ()),pos.getZ());
        BlockState state = level.getBlockState(realPos.below());
        BlockState state2 = level.getBlockState(realPos.below(2));
        if (state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.ICE)){
            level.setBlock(realPos.below(), BlocksRegistry.FROZEN_ZOMBIE_TRAP.get().defaultBlockState(),3);
            return true;
        }
        if (state2.is(Blocks.GRASS_BLOCK) || state2.is(Blocks.ICE)){
            level.setBlock(realPos.below(2), BlocksRegistry.FROZEN_ZOMBIE_TRAP.get().defaultBlockState(),3);
            return true;
        }
        return false;
    }
}
