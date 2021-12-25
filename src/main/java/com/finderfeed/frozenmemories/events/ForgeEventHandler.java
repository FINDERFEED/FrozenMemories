package com.finderfeed.frozenmemories.events;


import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.PlayerProgressionStage;
import com.finderfeed.frozenmemories.helpers.Helpers;
import com.finderfeed.frozenmemories.misc.MemoryTeleporter;
import com.finderfeed.frozenmemories.misc.ProgressionState;
import com.finderfeed.frozenmemories.misc.ServerWorldTask;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Mod.EventBusSubscriber(modid = FrozenMemories.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHandler {

    public static final ResourceKey<Level> MEMORY = ResourceKey.create(Registry.DIMENSION_REGISTRY,new ResourceLocation(FrozenMemories.MOD_ID,"memory"));
    public static final ResourceKey<Biome> MEMORY_BIOME = ResourceKey.create(Registry.BIOME_REGISTRY,new ResourceLocation(FrozenMemories.MOD_ID,"memory"));

    @SubscribeEvent
    public static void clone(PlayerEvent.Clone event){
        PlayerProgressionStage.handleCloneEvent(event);
        Player old = event.getOriginal();
        Player newp = event.getPlayer();
        newp.getPersistentData().put(Helpers.TAG_INVENTORY,old.getPersistentData().getList(Helpers.TAG_INVENTORY,10));
        Helpers.writeBlockPos(Helpers.TAG_RETURN_BLOCKPOS,Helpers.getBlockPos(Helpers.TAG_RETURN_BLOCKPOS,old.getPersistentData()),newp.getPersistentData());
    }

    @SubscribeEvent
    public static void addFeatures(BiomeLoadingEvent event){
        if (event.getCategory() == Biome.BiomeCategory.ICY){
            event.getGeneration().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION,ModEventHandler.PlacedFeatures.FROZEN_ZOMBIE_TRAPS);
        }
        if (event.getCategory() == Biome.BiomeCategory.ICY || event.getCategory() == Biome.BiomeCategory.TAIGA){
            event.getGeneration().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION,ModEventHandler.PlacedFeatures.MEMORY_TOWER);
        }
    }

    @SubscribeEvent
    public static void cancelMemoryBlockBreak(BlockEvent.BreakEvent event){
        LevelAccessor world = event.getWorld();
        if (world instanceof Level level && !level.isClientSide && !event.getPlayer().isCreative()){
            if (level.dimension() == MEMORY){
                int stage = PlayerProgressionStage.getPlayerProgressionStage(event.getPlayer());
                BlockState state = event.getState();
                ProgressionState prog = ProgressionState.STATES.get(stage);
                if (prog != null){
                    if (Arrays.stream(prog.getAllowedBlocks()).noneMatch((s)->s == state)){
                        event.setCanceled(true);
                    }
                }else{
                    event.getPlayer().sendMessage(new TextComponent("Something went wrong"),event.getPlayer().getUUID());
                }
            }
        }
    }

    @SubscribeEvent
    public static void cancelDeathInMemory(LivingDeathEvent event){
        Level world = event.getEntityLiving().getLevel();
        if (!world.isClientSide && world.dimension() == MEMORY){
            if (event.getEntityLiving() instanceof Player player ){
                player.setHealth(player.getMaxHealth());

                if (world.getServer() != null) {
                    ServerLevel overworld = world.getServer().getLevel(Level.OVERWORLD);
                    if (overworld != null) {
                        BlockPos returnPos = Helpers.getBlockPos(Helpers.TAG_RETURN_BLOCKPOS, player.getPersistentData());
                        player.getInventory().load(player.getPersistentData().getList(Helpers.TAG_INVENTORY,10));
                        overworld.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(returnPos), 3,returnPos);
                        MemoryTeleporter teleporter = new MemoryTeleporter(BlockPos.ZERO);
                        player.changeDimension(overworld,teleporter);
                    }
                }
                event.setCanceled(true);

            }
        }
    }

    private static final List<ServerWorldTask> TASKS = new ArrayList<>();
    private static final List<ServerWorldTask> TO_REMOVE = new ArrayList<>();

    @SubscribeEvent
    public static void serverTick(TickEvent.WorldTickEvent event){
        Level world = event.world;
        if (world instanceof ServerLevel level && event.phase == TickEvent.Phase.START && event.side == LogicalSide.SERVER){
            for (ServerWorldTask t : TASKS){
                if (t.getDimension() == level.dimension() && t.tick()){
                    t.getTask().run();
                    TO_REMOVE.add(t);
                }
            }
            TASKS.removeAll(TO_REMOVE);
            TO_REMOVE.clear();
        }
    }

    public static void addServerTask(ServerWorldTask task){
        TASKS.add(task);
    }



}
