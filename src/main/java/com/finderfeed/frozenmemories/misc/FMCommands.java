package com.finderfeed.frozenmemories.misc;

import com.finderfeed.frozenmemories.blocks.MemoryCrack;
import com.finderfeed.frozenmemories.events.ForgeEventHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;

public class FMCommands {


    public static void register(CommandDispatcher<CommandSourceStack> disp){
        LiteralCommandNode<CommandSourceStack> cmd = disp.register(
                Commands.literal("fm")
                .then(Commands.literal("error").executes((t)->error(t.getSource())))
        );
    }

    public static int error(CommandSourceStack stack) throws CommandSyntaxException {
        ServerPlayer player = stack.getPlayerOrException();
        if (player.level.dimension() == ForgeEventHandler.MEMORY) {
            MemoryCrack.teleportPlayerBack(player, true);
            stack.sendSuccess(new TextComponent("Teleported back"),true);
        }else{
            stack.sendFailure(new TextComponent("Not usable here"));
        }

        return 1;
    }

}
