package com.finderfeed.frozenmemories.blocks;

import com.finderfeed.frozenmemories.registries.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class MemoryTowerBlock extends Block implements EntityBlock {
    public MemoryTowerBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ctx) {
        if (!world.isClientSide && hand == InteractionHand.MAIN_HAND){
            player.sendMessage(new TextComponent("Currently its just a placeholder. This will have a usage in next update."),player.getUUID());
        }
        return super.use(state, world, pos, player, hand, ctx);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TileEntitiesRegistry.MEMORY_TOWER.get().create(pos,state);
    }
}
