package net.vukrosic.custommobswordsmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;
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
                        return resetChestCounter(context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                    })))
            .then(CommandManager.literal("bedAbilityEnabled") // boolean argument
                    .then(CommandManager.literal("true")
                            .executes((context) -> {
                                return bedAbilityEnabledTrue(context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                            }))
                    .then(CommandManager.literal("false")
                            .executes((context) -> {
                                return bedAbilityEnabledFalse(context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                            })))
            .then(CommandManager.literal("setAbilityLevel") // int argument
                    .then(CommandManager.argument("level", IntegerArgumentType.integer(0, 3))
                            .executes((context) -> {
                                return setAbilityLevel((ServerCommandSource)context.getSource(), IntegerArgumentType.getInteger(context, "level"));
                            }))
            .then(CommandManager.literal("hitBossBar")
                    .then(CommandManager.literal("disable")
                            .executes((context) -> {
                                return hitBossBarDisable(context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                            })))
        ));

    }

    private static int setAbilityLevel(ServerCommandSource source, int level) {
        PlayerAbilities.AbilityTier = level;
        return 1;
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
        ((PlayerEntityExt) Objects.requireNonNull(source.getPlayer())).setSuperjumping(false);
        return 1;
    }

    private static int bedAbilityEnabledTrue(ServerCommandSource source) {
        ((PlayerEntityExt) Objects.requireNonNull(source.getPlayer())).setSuperjumping(true);
        return 1;
    }



    private static int resetChestCounter(ServerCommandSource source){
        ChestsLootedByHuntersManager.numberOfChestsLootedByHunters = 0;
        ChestsLootedByHuntersManager.lootedChests.clear();
        return 1;
    }
}