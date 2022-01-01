package com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity;

import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.LoreProgram;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.LoreProgramStage;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.Offset;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.objectives.HarvestBlocksObjective;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.objectives.PlayerInventoryCheck;
import com.finderfeed.frozenmemories.blocks.tileentities.lore_tile_entity.lore_system.objectives.SurviveForTicksObjective;
import com.finderfeed.frozenmemories.entities.FrozenZombieMeteorite;
import com.finderfeed.frozenmemories.helpers.Helpers;
import com.finderfeed.frozenmemories.misc.ItemWithQuantity;
import com.finderfeed.frozenmemories.registries.BlocksRegistry;
import com.finderfeed.frozenmemories.registries.ItemsRegistry;
import com.finderfeed.frozenmemories.registries.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Locale;


public class LoreTileEntity extends BlockEntity {

    public static final AABB PLAYER_SEEK_AABB = new AABB(-50,-50,-50,50,50,50);
    private int playerProgressionState = 1;
    private LoreProgram currentLoreProgram = null;

    private LoreProgram STAGE_1 = LoreProgram.Builder.start("stage_1",this)
            .addStage(
                    LoreProgramStage.Builder.start("stage_11",this)
                    .addMessages("[Jake(You)]That was a good day!","[Jake]But before going back home i need some iron to make a new sword.","[SYSTEM]After every objective is completed move forward.",
                            "[SYSTEM]When ALL objectives will be completed you need to find and step into the special portal to return back.",
                            "[SYSTEM]If you think something went wrong use \"/fm error\" command to return back")
                            .addObjectives(new PlayerInventoryCheck("Harvest 5 iron.",this, ItemWithQuantity.of(Items.RAW_IRON,5)))
                    .build())
            .addStage(
                    LoreProgramStage.Builder.start("stage_12",this)
                            .addMessages("[Jake]Okay time to go home and drink some good hot chocolate!")
                            .addObjectives(new PlayerInventoryCheck("Drink hot chocolate.",this,ItemWithQuantity.of(ItemsRegistry.MUG.get(),1)))
                            .build()
            )
            .build();
    private LoreProgram STAGE_2 = LoreProgram.Builder.start("stage_2",this)
            .addStage(LoreProgramStage.Builder.start("stage_21",this)
                    .addMessages("[Jake]Hmmm its night time...","[Jake]Time to test out the new skates that were delivered today!","[Jake]If no one sees you then everything legal!",
                            "[Jake]Of course i will return them back!")
                    .addObjectives(new PlayerInventoryCheck("\"Steal\" the skates from skates rental station and test them out",this,ItemWithQuantity.of(ItemsRegistry.SKATES.get(),1)))
                    .build())
            .build();
    private LoreProgram STAGE_3 = LoreProgram.Builder.start("stage_3",this)
            .addStage(LoreProgramStage.Builder.start("stage_31",this)
                    .addMessages("[Jake]Time to smelt my iron!")
                    .addObjectives(new PlayerInventoryCheck("Smelt the iron in public forge",this,ItemWithQuantity.of(Items.IRON_INGOT,5)))
                    .build())
            .addStage(LoreProgramStage.Builder.start("stage_32",this)
                    .addMessages("[Jake]Time to turn it into frozen iron!","[Jake]I hope someone left some icicles inside the chest.")
                    .addObjectives(new PlayerInventoryCheck("Go to the left side of the forge and throw iron and icicles into the water pool",this,ItemWithQuantity.of(ItemsRegistry.FROZEN_IRON_INGOT.get(),5)))
                    .build())
            .build();
    private LoreProgram STAGE_4 = LoreProgram.Builder.start("stage_4",this)
            .addStage(LoreProgramStage.Builder.start("stage_41",this)
                    .addMessages("[Jake]I have slept all day again...","[Jake]I think i should normalize my sleep schedule.","[Jake]Anyway time to craft a brand new sword!")
                    .addObjectives(new PlayerInventoryCheck("Craft a frozen iron sword",this,ItemWithQuantity.of(ItemsRegistry.FROZEN_IRON_SWORD.get(),1)))
                    .build())
            .addStage(LoreProgramStage.Builder.start("stage_42",this)
                    .addMessages("[Jake]Wait...What happened while i was asleep?!","[Jake]Why there are strange meteorites everywhere?","[???]*Zombie growls*",
                            "[Jake]Oh i have a bad feeling about this...")
                    .addObjectives(new HarvestBlocksObjective("Find and kill all zombies",this, BlocksRegistry.FROZEN_ZOMBIE_TRAP.get(),
                            Offset.of(43,12,33),Offset.of(37,12,44),Offset.of(30,12,36),Offset.of(32,12,25),
                            Offset.of(32,12,18)))
                    .build())
            .build();
    private LoreProgram STAGE_5 = LoreProgram.Builder.start("stage_5",this)
            .addStage(LoreProgramStage.Builder.start("stage_51",this)
                    .addMessages("[Jake]The city is completely destroyed!","[Jake]Where are the guards?!","[Jake]I hope someone will tell me what happened?")
                    .addObjectives(new PlayerInventoryCheck("Find a note and read it",this,ItemWithQuantity.of(Items.WRITABLE_BOOK,1)))
                    .build())
            .build();
    private LoreProgram STAGE_6 = LoreProgram.Builder.start("stage_6",this)
            .addStage(LoreProgramStage.Builder.start("stage_61",this)
                    .addMessages("[Jake]The castle is near.","[Jake]I hope i will find answers.")
                    .addObjectives(new PlayerInventoryCheck("Find the castle and a note",this,ItemWithQuantity.of(Items.WRITABLE_BOOK,1)))
                    .build())
            .build();
    private LoreProgram STAGE_7 = LoreProgram.Builder.start("stage_7",this)
            .addStage(LoreProgramStage.Builder.start("stage_71",this)
                    .addMessages("[Jake]I have wasted a lot of time searching for this archives","[Jake]I need to take some books and go.")
                    .addObjectives(new PlayerInventoryCheck("Find 4 books",this,ItemWithQuantity.of(Items.WRITTEN_BOOK,4)))
                    .build())
            .build();

    private LoreProgram STAGE_8 = LoreProgram.Builder.start("stage_8",this)
            .addStage(LoreProgramStage.Builder.start("stage_81",this)
                    .addMessages("[Wizard 1]We cant hold any longer!","[Wizard 2]Look! We are not alone now!","[Wizard 1]Please help us! Defend the tower while we are freezing our memories!",
                            "[Jake]I think i have no other choice","[Jake]The forests are flooded with zombies, there is no way out. Only death...","[Jake]At least we will never be forgotten.")
                    .addObjectives(new SurviveForTicksObjective("Survive for 3 minutes and don't let zombies destroy the tower","obj_81",this,3600))
                    .addScheduledTask(LoreProgramStage.ScheduledTask.create(LoreProgramStage.ScheduledTask.Type.REPETITIVE,250,(tile)->{
                        Level world = tile.getLevel();
                        Offset[] offsets = {Offset.of(24,8,8),Offset.of(5,8,23),Offset.of(23,8,41),Offset.of(40,8,23)};
                        Vec3 meteoriteDestination = Helpers.blockCenter(offsets[world.random.nextInt(offsets.length)].apply(tile.getBlockPos()));
                        Vec3 randomDir = new Vec3(0,-1,0)
                                .xRot((float)(Helpers.randomPlusMinus()*Math.toRadians(world.random.nextFloat()*40)))
                                .yRot((float)Math.toRadians(world.random.nextFloat()*360));

                        FrozenZombieMeteorite meteorite = new FrozenZombieMeteorite(world);
                        meteorite.setPos(meteoriteDestination.add(randomDir.reverse().multiply(30,30,30)));
                        meteorite.setDeltaMovement(randomDir.multiply(0.5,0.5,0.5));
                        world.addFreshEntity(meteorite);
                    }))
                    .build())
            .build();
    private LoreProgram[] PROGRAMS = new LoreProgram[]{
            STAGE_1,
            STAGE_2,
            STAGE_3,
            STAGE_4,
            STAGE_5,
            STAGE_6,
            STAGE_7,
            STAGE_8
    };

    public LoreTileEntity(BlockPos pos, BlockState state) {
        super(TileEntitiesRegistry.LORE_TILE_ENTITY.get(), pos, state);
    }

    public static void tick(Level world,BlockState state,BlockPos pos,LoreTileEntity tile){
        if (!world.isClientSide){
            if (tile.getPlayerProgressionState()-1 < tile.PROGRAMS.length) {

                tile.currentLoreProgram = tile.PROGRAMS[tile.getPlayerProgressionState() - 1];
                tile.currentLoreProgram.tick();

            }
        }
    }


    @Nullable
    public LoreProgram getCurrentLoreProgram() {
        return currentLoreProgram;
    }

    public LoreProgram[] getPrograms() {
        return PROGRAMS;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return ClientboundBlockEntityDataPacket.create(this,(tile)->tag);
    }


    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        for (LoreProgram prog : PROGRAMS){
            prog.save(tag);
        }
        tag.putInt("playerProgState",playerProgressionState);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        for (LoreProgram prog : PROGRAMS){
            prog.load(tag);
        }
        this.playerProgressionState = tag.getInt("playerProgState");
        super.load(tag);
    }


    public void setPlayerProgressionState(int playerProgressionState) {
        this.playerProgressionState = playerProgressionState;
    }

    public int getPlayerProgressionState() {
        return playerProgressionState;
    }

    @Nullable
    public Player getPlayer(){
        List<Player> player = level.getEntitiesOfClass(Player.class,PLAYER_SEEK_AABB.move(worldPosition));
        return player.isEmpty() ? null : player.get(0);
    }
}
