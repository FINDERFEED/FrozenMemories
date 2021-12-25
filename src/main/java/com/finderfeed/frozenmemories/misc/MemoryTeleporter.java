package com.finderfeed.frozenmemories.misc;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.PlayerProgressionStage;
import com.finderfeed.frozenmemories.events.ForgeEventHandler;
import com.finderfeed.frozenmemories.helpers.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class MemoryTeleporter implements ITeleporter {

    private BlockPos randomPos;

    public MemoryTeleporter(BlockPos randomPos){
        this.randomPos = randomPos;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        return repositionEntity.apply(true);
    }

    @Nullable
    @Override
    public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        ResourceKey<Level> dim = destWorld.dimension();
        if (entity instanceof Player player){
            if (dim == ForgeEventHandler.MEMORY){
                int progStateInt = PlayerProgressionStage.getPlayerProgressionStage(player);
                ProgressionState state = ProgressionState.STATES.get(progStateInt);
                if (state != null){
                    BlockPos destPos = state.getOffset().apply(randomPos);
                    PortalInfo info = new PortalInfo(Helpers.blockCenter(destPos),Vec3.ZERO,entity.getYRot(),entity.getXRot());
                    return info;
                }
            }else if (dim == Level.OVERWORLD){
                BlockPos pos = Helpers.getBlockPos(Helpers.TAG_RETURN_BLOCKPOS,player.getPersistentData()).above();
                destWorld.getChunkAt(pos).setUnsaved(true);
                PortalInfo info = new PortalInfo(Helpers.blockCenter(pos),Vec3.ZERO,entity.getYRot(),entity.getXRot());
                return info;
            }
        }
        return ITeleporter.super.getPortalInfo(entity, destWorld, defaultPortalInfo);
    }
}
