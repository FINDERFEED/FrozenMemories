package com.finderfeed.frozenmemories.registries;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.blocks.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlocksRegistry {

    private static final VoxelShape CHOCOLATE_SHAPE = Block.box(5,0,5,11,5,11);

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FrozenMemories.MOD_ID);

    public static final RegistryObject<Block> LORE_TILE_BLOCK = registerBlock(()->new LoreTileBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK)),"lore_tile_block");
    public static final RegistryObject<Block> LORE_TILE_TRIGGER_BLOCK = registerBlock(()->new LoreTriggerBlock(BlockBehaviour.Properties.copy(Blocks.LIGHT).lightLevel((d)->0)),"lore_tile_trigger_block");
    public static final RegistryObject<Block> MEMORY_WALL = registerBlock(()->new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK)),"memory_wall");
    public static final RegistryObject<Block> MEMORY_TOWER = registerBlock(()->new MemoryTowerBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()),"memory_tower");
    public static final RegistryObject<Block> FROZEN_ZOMBIE_TRAP = registerBlock(()->new FrozenZombieTrapBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).sound(SoundType.GLASS)),"frozen_zombie_trap");
    public static final RegistryObject<Block> MEMORY_CRACK = registerBlock(()->new MemoryCrack(),"memory_crack");
    public static final RegistryObject<Block> HOT_CHOCOLATE_BLOCK = registerBlock(()->new Block(BlockBehaviour.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)){
        @Override
        public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
            return CHOCOLATE_SHAPE;
        }
    },"hot_chocolate");


    public static <T extends Block> RegistryObject<T> registerBlock(Supplier<T> block, String name){
        return BLOCKS.register(name,block);
    }

}
