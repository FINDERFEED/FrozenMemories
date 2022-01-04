package com.finderfeed.frozenmemories.registries;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.items.*;
import com.finderfeed.frozenmemories.misc.FMArmorMaterials;
import com.finderfeed.frozenmemories.misc.FMToolTiers;
import com.finderfeed.frozenmemories.misc.ProgressionState;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemsRegistry {


    public static final Supplier<Item.Properties> DEFAULT = ()->new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES);
    public static final Supplier<Item.Properties> DEFAULT_STACKS_1 = ()->new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES).stacksTo(1);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FrozenMemories.MOD_ID);

    public static final RegistryObject<BlockItem> LORE_TILE_BLOCK = registerBlockItem(new Item.Properties()
            , BlocksRegistry.LORE_TILE_BLOCK,"lore_tile_block");
    public static final RegistryObject<BlockItem> LORE_TILE_TRIGGER_BLOCK = registerBlockItem(new Item.Properties()
            , BlocksRegistry.LORE_TILE_TRIGGER_BLOCK,"lore_tile_trigger_block");

    public static final RegistryObject<HelperWand> HELPER_WAND = registerItem(new HelperWand(),"helper_wand");
    public static final RegistryObject<BuildingHelperWand> BUILDING_WAND = registerItem(new BuildingHelperWand(),"building_wand");

    public static final RegistryObject<FrozenItem> ICICLE_STAFF = registerItem(new IcicleStaff(new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES)),"icicle_staff");

    public static final RegistryObject<Item> PLACEHOLDER_UNKNOWN_ITEM = registerItem(new Item(new Item.Properties()),"unknown_item");
    public static final RegistryObject<FrozenItem> FROZEN_IRON_INGOT = registerItem(new FrozenItem(new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES),ProgressionState.THREE),"frozen_iron_ingot");
    public static final RegistryObject<FrozenItem> MAGIC_ICICLE = registerItem(new FrozenItem(new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES),ProgressionState.THREE){
        @Override
        public boolean isFoil(ItemStack stack) {
            return true;
        }
    },"magic_icicle");
    public static final RegistryObject<FrozenItem> FROZEN_DIAMOND = registerItem(new FrozenItem(new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES), ProgressionState.FIVE),"frozen_diamond");
    public static final RegistryObject<FrozenItem> FROZEN_NETHERITE = registerItem(new FrozenItem(new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES),ProgressionState.SEVEN),"frozen_netherite");

    public static final RegistryObject<FrozenArmorItem> FROZEN_IRON_HELMET = registerItem(new FrozenArmorItem(FMArmorMaterials.IRON, EquipmentSlot.HEAD,DEFAULT.get(),ProgressionState.FOUR),"frozen_iron_helmet");
    public static final RegistryObject<FrozenArmorItem> FROZEN_IRON_CHESTPLATE = registerItem(new FrozenArmorItem(FMArmorMaterials.IRON, EquipmentSlot.CHEST,DEFAULT.get(),ProgressionState.FOUR),"frozen_iron_chestplate");
    public static final RegistryObject<FrozenArmorItem> FROZEN_IRON_LEGGINGS = registerItem(new FrozenArmorItem(FMArmorMaterials.IRON, EquipmentSlot.LEGS,DEFAULT.get(),ProgressionState.FOUR),"frozen_iron_leggings");
    public static final RegistryObject<FrozenArmorItem> FROZEN_IRON_BOOTS = registerItem(new FrozenArmorItem(FMArmorMaterials.IRON, EquipmentSlot.FEET,DEFAULT.get(),ProgressionState.FOUR),"frozen_iron_boots");

    public static final RegistryObject<FrozenArmorItem> FROZEN_DIAMOND_HELMET = registerItem(new FrozenArmorItem(FMArmorMaterials.DIAMOND, EquipmentSlot.HEAD,DEFAULT.get(),ProgressionState.SIX),"frozen_diamond_helmet");
    public static final RegistryObject<FrozenArmorItem> FROZEN_DIAMOND_CHESTPLATE = registerItem(new FrozenArmorItem(FMArmorMaterials.DIAMOND, EquipmentSlot.CHEST,DEFAULT.get(),ProgressionState.SIX),"frozen_diamond_chestplate");
    public static final RegistryObject<FrozenArmorItem> FROZEN_DIAMOND_LEGGINGS = registerItem(new FrozenArmorItem(FMArmorMaterials.DIAMOND, EquipmentSlot.LEGS,DEFAULT.get(),ProgressionState.SIX),"frozen_diamond_leggings");
    public static final RegistryObject<FrozenArmorItem> FROZEN_DIAMOND_BOOTS = registerItem(new FrozenArmorItem(FMArmorMaterials.DIAMOND, EquipmentSlot.FEET,DEFAULT.get(),ProgressionState.SIX),"frozen_diamond_boots");

    public static final RegistryObject<FrozenArmorItem> FROZEN_NETHERITE_HELMET = registerItem(new FrozenArmorItem(FMArmorMaterials.NETHERITE, EquipmentSlot.HEAD,DEFAULT.get().fireResistant(),ProgressionState.EIGHT),"frozen_netherite_helmet");
    public static final RegistryObject<FrozenArmorItem> FROZEN_NETHERITE_CHESTPLATE = registerItem(new FrozenArmorItem(FMArmorMaterials.NETHERITE, EquipmentSlot.CHEST,DEFAULT.get().fireResistant(),ProgressionState.EIGHT),"frozen_netherite_chestplate");
    public static final RegistryObject<FrozenArmorItem> FROZEN_NETHERITE_LEGGINGS = registerItem(new FrozenArmorItem(FMArmorMaterials.NETHERITE, EquipmentSlot.LEGS,DEFAULT.get().fireResistant(),ProgressionState.EIGHT),"frozen_netherite_leggings");
    public static final RegistryObject<FrozenArmorItem> FROZEN_NETHERITE_BOOTS = registerItem(new FrozenArmorItem(FMArmorMaterials.NETHERITE, EquipmentSlot.FEET,DEFAULT.get().fireResistant(),ProgressionState.EIGHT),"frozen_netherite_boots");

    public static final RegistryObject<FrozenArmorItem> SKATES = registerItem(new SkatesItem(FMArmorMaterials.SKATES, EquipmentSlot.FEET,DEFAULT.get().fireResistant(),ProgressionState.TWO),"skates");



    public static final RegistryObject<FrozenMemoriesSword> FROZEN_IRON_SWORD = registerItem(new FrozenMemoriesSword(FMToolTiers.FROZEN_IRON,3, -2.4F,new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES), ProgressionState.THREE),"frozen_iron_sword");
    public static final RegistryObject<FrozenMemoriesSword> FROZEN_DIAMOND_SWORD = registerItem(new FrozenMemoriesSword(FMToolTiers.FROZEN_DIAMOND,3, -2.4F,new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES),ProgressionState.FIVE),"frozen_diamond_sword");
    public static final RegistryObject<FrozenMemoriesSword> FROZEN_NETHERITE_SWORD = registerItem(new FrozenMemoriesSword(FMToolTiers.FROZEN_NETHERITE,3, -2.4F,new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES).fireResistant(),ProgressionState.SEVEN),"frozen_netherite_sword");

    public static final RegistryObject<FrozenPickaxeItem> FROZEN_IRON_PICKAXE= registerItem(new FrozenPickaxeItem(FMToolTiers.FROZEN_IRON,1, -2.8F,new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES), ProgressionState.THREE),"frozen_iron_pickaxe");
    public static final RegistryObject<FrozenPickaxeItem> FROZEN_DIAMOND_PICKAXE = registerItem(new FrozenPickaxeItem(FMToolTiers.FROZEN_DIAMOND,1, -2.8F,new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES),ProgressionState.FIVE),"frozen_diamond_pickaxe");
    public static final RegistryObject<FrozenPickaxeItem> FROZEN_NETHERITE_PICKAXE = registerItem(new FrozenPickaxeItem(FMToolTiers.FROZEN_NETHERITE,1, -2.8F,new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES).fireResistant(),ProgressionState.SEVEN),"frozen_netherite_pickaxe");

    public static final RegistryObject<FrozenShovelItem> FROZEN_IRON_SHOVEL = registerItem(new FrozenShovelItem(FMToolTiers.FROZEN_IRON,1.5f, -3F,new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES), ProgressionState.THREE),"frozen_iron_shovel");
    public static final RegistryObject<FrozenShovelItem> FROZEN_DIAMOND_SHOVEL = registerItem(new FrozenShovelItem(FMToolTiers.FROZEN_DIAMOND,1.5f, -3F,new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES),ProgressionState.FIVE),"frozen_diamond_shovel");
    public static final RegistryObject<FrozenShovelItem> FROZEN_NETHERITE_SHOVEL = registerItem(new FrozenShovelItem(FMToolTiers.FROZEN_NETHERITE,1.5f, -3F,new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES).fireResistant(),ProgressionState.SEVEN),"frozen_netherite_shovel");


    public static final RegistryObject<FrozenAxeItem> FROZEN_IRON_AXE = registerItem(new FrozenAxeItem(FMToolTiers.FROZEN_IRON,5, -3.1F,new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES), ProgressionState.THREE),"frozen_iron_axe");
    public static final RegistryObject<FrozenAxeItem> FROZEN_DIAMOND_AXE = registerItem(new FrozenAxeItem(FMToolTiers.FROZEN_DIAMOND,5, -3F,new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES),ProgressionState.FIVE),"frozen_diamond_axe");
    public static final RegistryObject<FrozenAxeItem> FROZEN_NETHERITE_AXE = registerItem(new FrozenAxeItem(FMToolTiers.FROZEN_NETHERITE,5, -3F,new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES).fireResistant(),ProgressionState.SEVEN),"frozen_netherite_axe");


    public static final RegistryObject<FrozenHoeItem> FROZEN_IRON_HOE = registerItem(new FrozenHoeItem(FMToolTiers.FROZEN_IRON,-3, -1F,new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES), ProgressionState.THREE),"frozen_iron_hoe");
    public static final RegistryObject<FrozenHoeItem> FROZEN_DIAMOND_HOE = registerItem(new FrozenHoeItem(FMToolTiers.FROZEN_DIAMOND,-3, -1F,new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES),ProgressionState.FIVE),"frozen_diamond_hoe");
    public static final RegistryObject<FrozenHoeItem> FROZEN_NETHERITE_HOE = registerItem(new FrozenHoeItem(FMToolTiers.FROZEN_NETHERITE,-3, -1F,new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES).fireResistant(),ProgressionState.SEVEN),"frozen_netherite_hoe");
//    public static final RegistryObject<FrozenBlockItem> HOT_CHOCOLATE = registerItem(new HotChocolateItem(DEFAULT_STACKS_1.get().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1F).effect(()->new MobEffectInstance(MobEffects.MOVEMENT_SPEED,1200,0),1).build())),"hot_chocolate");

    public static final RegistryObject<Item> MUG = registerItem(new Item(DEFAULT.get()),"mug");
    public static final RegistryObject<FrozenBlockItem> HOT_CHOCOLATE = ITEMS.register("hot_chocolate",
            ()->new HotChocolateItem(BlocksRegistry.HOT_CHOCOLATE_BLOCK.get(),
                    DEFAULT_STACKS_1.get().food(new FoodProperties.Builder().alwaysEat().nutrition(6).saturationMod(0.1F).effect(()->new MobEffectInstance(MobEffects.MOVEMENT_SPEED,1200,0),1).build())));

    public static final RegistryObject<BlockItem> MEMORY_WALL = registerBlockItem(new Item.Properties(),
            BlocksRegistry.MEMORY_WALL,"memory_wall");
    public static final RegistryObject<BlockItem> FROZEN_ZOMBIE_TRAP = registerBlockItem(new Item.Properties().tab(FrozenMemories.FROZEN_MEMORIES),
            BlocksRegistry.FROZEN_ZOMBIE_TRAP,"frozen_zombie_trap");
    public static final RegistryObject<BlockItem> MEMORY_TOWER = registerBlockItem(new Item.Properties(),
            BlocksRegistry.MEMORY_TOWER,"memory_tower");
    public static final RegistryObject<BlockItem> MEMORY_CRACK = registerBlockItem(new Item.Properties(),
            BlocksRegistry.MEMORY_CRACK,"memory_crack");


    public static <T extends Item> RegistryObject<T> registerItem(T item,String name){
        return ITEMS.register(name,()->item);
    }

    public static RegistryObject<BlockItem> registerBlockItem(Item.Properties props, Supplier<Block> block, String name){
        return ITEMS.register(name,()->new BlockItem(block.get(),props));
    }

}
