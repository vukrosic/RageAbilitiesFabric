package net.vukrosic.custommobswordsmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;
import net.vukrosic.custommobswordsmod.util.custom.ChestsLootedByHuntersManager;

import java.util.ArrayList;

public class AbilitiesCommand {

    public static ArrayList<ServerBossBar> serverBossBars = new ArrayList();
    public static int mobThrowForce = 3;
    public static int mobThrowTowardsGround = 3;
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("abilities")
            .then(CommandManager.literal("chests")
                .then(CommandManager.literal("resetCounter")
                    .executes((context) -> {
                        return resetChestCounter(context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                    })))
            .then(CommandManager.literal("setAbilityLevel") // int argument
                    .then(CommandManager.argument("level", IntegerArgumentType.integer(-1, 10))
                            .executes((context) -> {
                                return setAbilityLevel((ServerCommandSource)context.getSource(), IntegerArgumentType.getInteger(context, "level"));
                            })))
            .then(CommandManager.literal("hitBossBar")
                    .then(CommandManager.literal("reset")
                            .executes((context) -> {
                                return hitBossBarDisable(context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                            })))
            .then(CommandManager.literal("setThrowForce") // int argument
                    .then(CommandManager.argument("force", IntegerArgumentType.integer(-20, 100))
                            .executes((context) -> {
                                return setThrowForce((ServerCommandSource)context.getSource(), IntegerArgumentType.getInteger(context, "force"));
                            })))
            .then(CommandManager.literal("setThrowTowardsGround") // int argument
                    .then(CommandManager.argument("towardsGround", IntegerArgumentType.integer(-20, 100))
                            .executes((context) -> {
                                return setThrowTowardsGround((ServerCommandSource)context.getSource(), IntegerArgumentType.getInteger(context, "towardsGround"));
                            })))
        );
    }

    private static int setThrowTowardsGround(ServerCommandSource source, int level) {
        mobThrowTowardsGround = level;
        return 1;
    }

    private static int setThrowForce(ServerCommandSource source, int level) {
        mobThrowForce = level;
        return 1;
    }

    private static int setAbilityLevel(ServerCommandSource source, int level) {
        PlayerAbilities.AbilityTier = level;
        return 1;
    }

    public static int hitBossBarDisable(ServerCommandSource source) {
        PlayerAbilities.rageBarProgress = 0;

        for (ServerBossBar serverBossBar : serverBossBars) {
            serverBossBar.removePlayer(source.getPlayer());
            serverBossBar.setVisible(false);
        }
        return 1;
    }






    private static int resetChestCounter(ServerCommandSource source){
        ChestsLootedByHuntersManager.numberOfChestsLootedByHunters = 0;
        ChestsLootedByHuntersManager.lootedChests.clear();
        return 1;
    }
}