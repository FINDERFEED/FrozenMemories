package com.finderfeed.frozenmemories.registries;

import com.finderfeed.frozenmemories.FrozenMemories;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlocksRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FrozenMemories.MOD_ID);

    public static final RegistryObject<Block> LORE_TILE_BLOCK = registerBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK),"lore_tile_block");

    public static RegistryObject<Block> registerBlock(BlockBehaviour.Properties props, String name){
        return BLOCKS.register(name,()->new Block(props));
    }

}
