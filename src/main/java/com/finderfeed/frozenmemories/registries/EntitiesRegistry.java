package com.finderfeed.frozenmemories.registries;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.entities.FrostedZombie;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntitiesRegistry {


    public static final DeferredRegister<EntityType<?>> DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, FrozenMemories.MOD_ID);

    public static final RegistryObject<EntityType<FrostedZombie>> FROSTED_ZOMBIE = DEFERRED_REGISTER.register("frozen_zombie",()->
            EntityType.Builder.<FrostedZombie>of(FrostedZombie::new, MobCategory.AMBIENT).sized(1,2).build("frozen_zombie"));




}
