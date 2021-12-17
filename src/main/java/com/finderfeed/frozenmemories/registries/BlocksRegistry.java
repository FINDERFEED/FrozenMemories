package com.finderfeed.frozenmemories.registries;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.blocks.FrozenZombieTrapBlock;
import com.finderfeed.frozenmemories.blocks.LoreTileBlock;
import com.finderfeed.frozenmemories.blocks.LoreTriggerBlock;
import com.finderfeed.frozenmemories.blocks.MemoryTowerBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlocksRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FrozenMemories.MOD_ID);

    public static final RegistryObject<Block> LORE_TILE_BLOCK = registerBlock(new LoreTileBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK)),"lore_tile_block");
    public static final RegistryObject<Block> LORE_TILE_TRIGGER_BLOCK = registerBlock(new LoreTriggerBlock(BlockBehaviour.Properties.copy(Blocks.LIGHT).lightLevel((d)->0)),"lore_tile_trigger_block");
    public static final RegistryObject<Block> MEMORY_WALL = registerBlock(new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK)),"memory_wall");
    public static final RegistryObject<Block> MEMORY_TOWER = registerBlock(new MemoryTowerBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()),"memory_tower");
    public static final RegistryObject<Block> FROZEN_ZOMBIE_TRAP = registerBlock(new FrozenZombieTrapBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).sound(SoundType.GLASS)),"frozen_zombie_trap");


    public static <T extends Block> RegistryObject<T> registerBlock(T block, String name){
        return BLOCKS.register(name,()->block);
    }

}
