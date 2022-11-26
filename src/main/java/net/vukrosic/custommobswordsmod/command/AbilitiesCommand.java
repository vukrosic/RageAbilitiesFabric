package net.vukrosic.custommobswordsmod.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.vukrosic.custommobswordsmod.util.custom.ChestsLootedByHuntersManager;

import java.util.ArrayList;

public class AbilitiesCommand {

    public static ArrayList<ServerBossBar> serverBossBars = new ArrayList();

    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("abilities")
            .then(CommandManager.literal("chests")
                .then(CommandManager.literal("resetCounter")
                    .executes((context) -> {
                        return resetChestCounter((ServerCommandSource) context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                    }))));

    }
    private static int resetChestCounter(ServerCommandSource source){
        ChestsLootedByHuntersManager.numberOfChestsLootedByHunters = 0;
        ChestsLootedByHuntersManager.lootedChests.clear();
        return 1;
    }
}