package com.finderfeed.frozenmemories.events;


import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.blocks.MemoryCrack;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.PlayerProgressionStage;
import com.finderfeed.frozenmemories.helpers.Helpers;
import com.finderfeed.frozenmemories.items.FrozenArmorItem;
import com.finderfeed.frozenmemories.misc.*;
import com.finderfeed.frozenmemories.registries.ItemsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Mod.EventBusSubscriber(modid = FrozenMemories.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHandler {
    private static final AABB BLOCK_AABB = new AABB(0,0,0,1,1,1);
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
    public static void cancelItemUse(PlayerInteractEvent.RightClickItem event){
        Item item = event.getItemStack().getItem();
        Player player = event.getPlayer();
        if (!player.level.isClientSide && item instanceof FrozenMemoriesItem frozenMemoriesItem && notMemory(player.level)){
            if (PlayerProgressionStage.getPlayerProgressionStage(player) < frozenMemoriesItem.getNeededPlayerLevel()){
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void cancelBlockPlace(BlockEvent.EntityPlaceEvent event){
        Item item = event.getPlacedBlock().getBlock().asItem();
        Entity e = event.getEntity();
        if ((e != null) && (!e.level.isClientSide) && (item instanceof FrozenMemoriesItem frozenMemoriesItem) && (e instanceof Player player) && notMemory(player.level)){
            if (PlayerProgressionStage.getPlayerProgressionStage(player) < frozenMemoriesItem.getNeededPlayerLevel()){
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void cancelEntityHit(LivingHurtEvent event){
        if ((event.getSource() != null) && (event.getSource().getEntity() instanceof Player player) && (!player.level.isClientSide) && notMemory(player.level)){
            Item item = player.getMainHandItem().getItem();
            if (item instanceof FrozenMemoriesItem frozenMemoriesItem){
                if (PlayerProgressionStage.getPlayerProgressionStage(player) < frozenMemoriesItem.getNeededPlayerLevel()){
                    event.setCanceled(true);
                }
            }
        }
    }
    private static boolean notMemory(Level world){
        return world.dimension() != MEMORY;
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
//                        BlockPos returnPos = Helpers.getBlockPos(Helpers.TAG_RETURN_BLOCKPOS, player.getPersistentData());
//                        player.getInventory().load(player.getPersistentData().getList(Helpers.TAG_INVENTORY,10));
//                        overworld.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(returnPos), 3,returnPos);
//                        MemoryTeleporter teleporter = new MemoryTeleporter(BlockPos.ZERO);
//                        player.changeDimension(overworld,teleporter);
                        MemoryCrack.teleportPlayerBack((ServerPlayer) player,true);
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


    public static final AABB SEARCH_AABB = new AABB(-10,-10,-10,10,10,10);
    public static final String TIME_TAG = FrozenMemories.MOD_ID + "_time_transforming";
    public static final int TRANSFORMING_TIME_SECONDS = 20;

    @SubscribeEvent
    public static void transformNearbyItems(TickEvent.PlayerTickEvent event){
        if (event.phase == TickEvent.Phase.START){
            Player pl = event.player;
            if (pl instanceof ServerPlayer player){



                Level world = player.level;
                if (world.getGameTime() % 40 == 0){
                    Helpers.updatePlayerStageOnClient(player);

                }
                if (world.getGameTime() % 20 == 0){
                    Biome b = world.getBiome(player.getOnPos());
                    if (b.getBiomeCategory() == Biome.BiomeCategory.ICY || b.getBiomeCategory() == Biome.BiomeCategory.TAIGA) {
                        boolean addArmorSpeedEffect = true;
                        for (ItemStack stack : player.getInventory().armor) {
                            if (!(stack.getItem() instanceof FrozenArmorItem)) {
                                addArmorSpeedEffect = false;
                            }
                        }
                        if (addArmorSpeedEffect) {
                            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1));
                        }
                    }

                    world.getEntitiesOfClass(ItemEntity.class,SEARCH_AABB.move(player.position()),(t)->{
                        Item i = t.getItem().getItem();
                        return i == Items.IRON_INGOT || i == Items.DIAMOND || i == Items.NETHERITE_INGOT;
                    })
                            .forEach((itemEntity)->{
                                BlockPos pos = itemEntity.getOnPos();
                                ItemStack item = itemEntity.getItem();
                                List<ItemEntity> icicles = world.getEntitiesOfClass(ItemEntity.class,BLOCK_AABB.move(pos),(t)->t.getItem().is(ItemsRegistry.MAGIC_ICICLE.get()));
                                if (!icicles.isEmpty()) {
                                    int iciclesCount = 0;
                                    for (ItemEntity i : icicles){
                                        iciclesCount+=i.getItem().getCount();
                                    }
                                    if (item.is(Items.IRON_INGOT)) {
                                        if (hasIceAround(itemEntity)) {
                                            CompoundTag tag = itemEntity.getPersistentData();
                                            int time = tag.getInt(TIME_TAG);
                                            if (time <= TRANSFORMING_TIME_SECONDS) {
                                                tag.putInt(TIME_TAG, time + 1);
                                            } else {
                                                int maxOutput = Math.min(itemEntity.getItem().getCount(),iciclesCount);
                                                if (maxOutput != itemEntity.getItem().getCount()){
                                                    ItemEntity frozenIron = new ItemEntity(itemEntity.level,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(ItemsRegistry.FROZEN_IRON_INGOT.get(),maxOutput));
                                                    itemEntity.getItem().shrink(maxOutput);
                                                    itemEntity.level.addFreshEntity(frozenIron);
                                                    icicles.forEach(Entity::discard);
                                                }else{
                                                    itemEntity.setItem(new ItemStack(ItemsRegistry.FROZEN_IRON_INGOT.get(), maxOutput));
                                                    int remainingToMinus = maxOutput;
                                                    for (ItemEntity icicle : icicles){
                                                        ItemStack stack = icicle.getItem();
                                                        if (stack.getCount() >= remainingToMinus){
                                                            stack.shrink(remainingToMinus);
                                                        }else{
                                                            remainingToMinus = remainingToMinus - stack.getCount();
                                                            icicle.discard();
                                                        }
                                                    }
                                                }

                                            }

                                        }
                                    } else if (item.is(Items.DIAMOND)) {
                                        if (hasIceAround(itemEntity)) {
                                            CompoundTag tag = itemEntity.getPersistentData();
                                            int time = tag.getInt(TIME_TAG);
                                            if (time <= TRANSFORMING_TIME_SECONDS) {
                                                tag.putInt(TIME_TAG, time + 1);
                                            } else {
                                                int maxOutput = Math.min(itemEntity.getItem().getCount(),iciclesCount);
                                                if (maxOutput != itemEntity.getItem().getCount()){
                                                    ItemEntity frozenIron = new ItemEntity(itemEntity.level,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(ItemsRegistry.FROZEN_DIAMOND.get(),maxOutput));
                                                    itemEntity.getItem().shrink(maxOutput);
                                                    itemEntity.level.addFreshEntity(frozenIron);
                                                    icicles.forEach(Entity::discard);
                                                }else{
                                                    itemEntity.setItem(new ItemStack(ItemsRegistry.FROZEN_DIAMOND.get(), maxOutput));
                                                    int remainingToMinus = maxOutput;
                                                    for (ItemEntity icicle : icicles){
                                                        ItemStack stack = icicle.getItem();
                                                        if (stack.getCount() >= remainingToMinus){
                                                            stack.shrink(remainingToMinus);
                                                        }else{
                                                            remainingToMinus = remainingToMinus - stack.getCount();
                                                            icicle.discard();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (item.is(Items.NETHERITE_INGOT)) {
                                        if (hasIceAround(itemEntity)) {
                                            CompoundTag tag = itemEntity.getPersistentData();
                                            int time = tag.getInt(TIME_TAG);
                                            if (time <= TRANSFORMING_TIME_SECONDS) {
                                                tag.putInt(TIME_TAG, time + 1);
                                            } else {
                                                int maxOutput = Math.min(itemEntity.getItem().getCount(),iciclesCount);
                                                if (maxOutput != itemEntity.getItem().getCount()){
                                                    ItemEntity frozenIron = new ItemEntity(itemEntity.level,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(ItemsRegistry.FROZEN_NETHERITE.get(),maxOutput));
                                                    itemEntity.getItem().shrink(maxOutput);
                                                    itemEntity.level.addFreshEntity(frozenIron);
                                                    icicles.forEach(Entity::discard);
                                                }else{
                                                    itemEntity.setItem(new ItemStack(ItemsRegistry.FROZEN_NETHERITE.get(), maxOutput));
                                                    int remainingToMinus = maxOutput;
                                                    for (ItemEntity icicle : icicles){
                                                        ItemStack stack = icicle.getItem();
                                                        if (stack.getCount() >= remainingToMinus){
                                                            stack.shrink(remainingToMinus);
                                                        }else{
                                                            remainingToMinus = remainingToMinus - stack.getCount();
                                                            icicle.discard();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            });

                }
            }
        }
    }







    private static boolean hasIceAround(ItemEntity itemEntity){
        Level world = itemEntity.level;
        BlockPos pos = itemEntity.getOnPos();
        return  world.getBlockState(pos).is(Blocks.WATER) &&
                world.getBlockState(pos.below()).is(Blocks.ICE) &&
                world.getBlockState(pos.north()).is(Blocks.ICE) &&
                world.getBlockState(pos.west()).is(Blocks.ICE) &&
                world.getBlockState(pos.south()).is(Blocks.ICE) &&
                world.getBlockState(pos.east()).is(Blocks.ICE) ;
    }
    @SubscribeEvent
    public static void registerCommands(final RegisterCommandsEvent event){
        FMCommands.register(event.getDispatcher());
    }



}
