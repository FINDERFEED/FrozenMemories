package com.finderfeed.frozenmemories.misc;

import com.finderfeed.frozenmemories.FrozenMemories;
import com.finderfeed.frozenmemories.registries.ItemsRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public enum FMToolTiers implements Tier {
    FROZEN_IRON(2, 300, 6.5F, 2.5F, 15, () -> {
        return Ingredient.of(ItemsRegistry.FROZEN_IRON_INGOT.get());
    },FrozenMemories.FROZEN_IRON_TAG),
    FROZEN_DIAMOND(3, 1761, 8.5F, 3.5F, 12, () -> {
        return Ingredient.of(ItemsRegistry.FROZEN_DIAMOND.get());
    },FrozenMemories.FROZEN_DIAMOND_TAG),
    FROZEN_NETHERITE(4, 2531, 9.5F, 4.5F, 18, () -> {
        return Ingredient.of(ItemsRegistry.FROZEN_NETHERITE.get());
    },FrozenMemories.FROZEN_NETHERITE_TAG);



    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;
    private final Tag<Block> blocktag;

    FMToolTiers(int p_43332_, int p_43333_, float p_43334_, float p_43335_, int p_43336_, Supplier<Ingredient> p_43337_, Tag<Block> blocktag) {
        this.level = p_43332_;
        this.uses = p_43333_;
        this.speed = p_43334_;
        this.damage = p_43335_;
        this.enchantmentValue = p_43336_;
        this.repairIngredient = new LazyLoadedValue<>(p_43337_);
        this.blocktag = blocktag;
    }

    public int getUses() {
        return this.uses;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getAttackDamageBonus() {
        return this.damage;
    }

    public int getLevel() {
        return this.level;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Nullable
    @Override
    public Tag<Block> getTag() {
        return blocktag;
    }
}
