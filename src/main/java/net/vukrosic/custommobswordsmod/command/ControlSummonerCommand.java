package net.vukrosic.custommobswordsmod.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.vukrosic.custommobswordsmod.entity.ModEntities;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import net.vukrosic.custommobswordsmod.entity.custom.summoner.SummonerEntityGL;
import net.vukrosic.custommobswordsmod.entity.custom.summoner.SummonerEntityRemoval;

public class ControlSummonerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher,
                                CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("summoner")
                .then(CommandManager.literal("activate")
                        .executes((context) -> {
                            return activateSummoner((ServerCommandSource)context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                        }))
                .then(CommandManager.literal("deactivate")
                        .executes((context) -> {
                            return deactivateSummoner((ServerCommandSource)context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                        }))
                .then(CommandManager.literal("clearAll")
                        .executes((context) -> {
                            return clearAll((ServerCommandSource)context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                        })));

    }

    private static int activateSummoner(ServerCommandSource source) {
        // get who executed the command
        // set controllingPlayer to that player
        // set controlling to true
        source.getPlayer().setInvisible(true);

        return 1;
    }

    private static int deactivateSummoner(ServerCommandSource source) {
        source.getPlayer().setInvisible(false);
        SummonerEntityGL summonerEntityGL = ((PlayerEntityExt)source.getPlayer()).getSummonerEntityGL();
        ((PlayerEntityExt)source.getPlayer()).setSummonerEntityGL(null);
        summonerEntityGL.controllingPlayer = null;
        summonerEntityGL.discard();
        return 1;
    }

    private static int clearAll(ServerCommandSource source) {
        SummonerEntityRemoval.clear();
        return 1;
    }

}
