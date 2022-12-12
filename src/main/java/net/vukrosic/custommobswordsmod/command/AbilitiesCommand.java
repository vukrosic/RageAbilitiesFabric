package net.vukrosic.custommobswordsmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import net.vukrosic.custommobswordsmod.util.custom.ChestsLootedByHuntersManager;

import java.util.ArrayList;
import java.util.Objects;

public class AbilitiesCommand {

    public static ArrayList<ServerBossBar> serverBossBars = new ArrayList();

    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("abilities")
            .then(CommandManager.literal("chests")
                .then(CommandManager.literal("resetCounter")
                    .executes((context) -> {
                        return resetChestCounter((ServerCommandSource) context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                    })))
            .then(CommandManager.literal("spawnLava") // boolean argument
                    .then(CommandManager.literal("true")
                            .executes((context) -> {
                                return spawnLavaTrue((ServerCommandSource) context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                            }))
                    .then(CommandManager.literal("false")
                            .executes((context) -> {
                                return spawnLavaFalse((ServerCommandSource) context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                            })))
            .then(CommandManager.literal("bedAbilityEnabled") // boolean argument
                    .then(CommandManager.literal("true")
                            .executes((context) -> {
                                return bedAbilityEnabledTrue((ServerCommandSource) context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                            }))
                    .then(CommandManager.literal("false")
                            .executes((context) -> {
                                return bedAbilityEnabledFalse((ServerCommandSource) context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                            })))
            .then(CommandManager.literal("hitBossBar")
                    .then(CommandManager.literal("disable")
                            .executes((context) -> {
                                return hitBossBarDisable((ServerCommandSource) context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                            })))
        );

    }

    private static int hitBossBarDisable(ServerCommandSource source) {
        for (ServerBossBar serverBossBar : serverBossBars) {
            serverBossBar.removePlayer(source.getPlayer());
            serverBossBar.setVisible(false);
        }
        serverBossBars.clear();
        return 1;
    }

    private static int bedAbilityEnabledFalse(ServerCommandSource source) {
        ((PlayerEntityExt) Objects.requireNonNull(source.getPlayer())).setBedAbilityActive(false);
        return 1;
    }

    private static int bedAbilityEnabledTrue(ServerCommandSource source) {
        ((PlayerEntityExt) Objects.requireNonNull(source.getPlayer())).setBedAbilityActive(true);
        return 1;
    }

    private static int spawnLavaFalse(ServerCommandSource source) {
        ((PlayerEntityExt) Objects.requireNonNull(source.getPlayer())).setSpawningLavaAround(false);
        return 1;
    }

    private static int spawnLavaTrue(ServerCommandSource source) {
        ((PlayerEntityExt) Objects.requireNonNull(source.getPlayer())).setSpawningLavaAround(true);
        return 1;
    }

    private static int resetChestCounter(ServerCommandSource source){
        ChestsLootedByHuntersManager.numberOfChestsLootedByHunters = 0;
        ChestsLootedByHuntersManager.lootedChests.clear();
        return 1;
    }
}