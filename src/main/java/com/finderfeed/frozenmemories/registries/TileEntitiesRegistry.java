package com.finderfeed.frozenmemories.registries;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.blocks.tileentities.FrozenZombieTile;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.LoreTileEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TileEntitiesRegistry {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, FrozenMemories.MOD_ID);

    public static final RegistryObject<BlockEntityType<LoreTileEntity>> LORE_TILE_ENTITY = TILE_ENTITIES.register("lore_tile_entity",()->
            BlockEntityType.Builder.of(LoreTileEntity::new,BlocksRegistry.LORE_TILE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<FrozenZombieTile>> FROZEN_ZOMBIE_TRAP = TILE_ENTITIES.register("frozen_zombie_trap",()->
            BlockEntityType.Builder.of(FrozenZombieTile::new,BlocksRegistry.FROZEN_ZOMBIE_TRAP.get()).build(null));

}
