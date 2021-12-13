package com.finderfeed.frozenmemories.registries;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.items.BuildingHelperWand;
import com.finderfeed.frozenmemories.items.FrozenMemoriesItem;
import com.finderfeed.frozenmemories.items.HelperWand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemsRegistry {


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FrozenMemories.MOD_ID);

    public static final RegistryObject<BlockItem> LORE_TILE_BLOCK = registerBlockItem(new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES)
            ,()->BlocksRegistry.LORE_TILE_BLOCK.get(),"lore_tile_block");
    public static final RegistryObject<BlockItem> LORE_TILE_TRIGGER_BLOCK = registerBlockItem(new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES)
            ,()->BlocksRegistry.LORE_TILE_TRIGGER_BLOCK.get(),"lore_tile_trigger_block");

    public static final RegistryObject<HelperWand> HELPER_WAND = registerItem(new HelperWand(),"helper_wand");
    public static final RegistryObject<BuildingHelperWand> BUILDING_WAND = registerItem(new BuildingHelperWand(),"building_wand");

    public static final RegistryObject<Item> PLACEHOLDER_UNKNOWN_ITEM = registerItem(new Item(new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES)),"unknown_item");
    public static final RegistryObject<FrozenMemoriesItem> TEST_ITEM = registerItem(new FrozenMemoriesItem(new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES),1),"test");
    public static final RegistryObject<BlockItem> MEMORY_WALL = registerBlockItem(new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES),
            ()->BlocksRegistry.MEMORY_WALL.get(),"memory_wall");
    public static final RegistryObject<BlockItem> FROZEN_ZOMBIE_TRAP = registerBlockItem(new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES),
            ()->BlocksRegistry.FROZEN_ZOMBIE_TRAP.get(),"frozen_zombie_trap");



    public static <T extends Item> RegistryObject<T> registerItem(T item,String name){
        return ITEMS.register(name,()->item);
    }

    public static RegistryObject<BlockItem> registerBlockItem(Item.Properties props, Supplier<Block> block, String name){
        return ITEMS.register(name,()->new BlockItem(block.get(),props));
    }

}
