package com.finderfeed.frozenmemories.registries;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.entities.FrostedZombie;
import com.finderfeed.frozenmemories.entities.FrozenZombieMeteorite;
import com.finderfeed.frozenmemories.entities.IcicleProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntitiesRegistry {


    public static final DeferredRegister<EntityType<?>> DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, FrozenMemories.MOD_ID);

    public static final RegistryObject<EntityType<FrostedZombie>> FROSTED_ZOMBIE = DEFERRED_REGISTER.register("frozen_zombie",()->
            EntityType.Builder.<FrostedZombie>of(FrostedZombie::new, MobCategory.AMBIENT).sized(0.6F, 1.95F).clientTrackingRange(8).build("frozen_zombie"));

    public static final RegistryObject<EntityType<FrozenZombieMeteorite>> FROZEN_ZOMBIE_METEORITE = DEFERRED_REGISTER.register("frozen_zombie_meteorite",()->
            EntityType.Builder.<FrozenZombieMeteorite>of(FrozenZombieMeteorite::new, MobCategory.MISC).sized(3,3).build("frozen_zombie_meteorite"));

    public static final RegistryObject<EntityType<IcicleProjectile>> ICICLE = DEFERRED_REGISTER.register("icicle",()->
            EntityType.Builder.<IcicleProjectile>of(IcicleProjectile::new, MobCategory.MISC).sized(0.25f,0.25f).build("icicle"));
}
