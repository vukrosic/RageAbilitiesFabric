package net.vukrosic.custommobswordsmod.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import net.vukrosic.custommobswordsmod.entity.custom.fireenderman.CombustometerManager;

import java.util.Objects;

public class CombustometerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("combustometer")
                .then(CommandManager.literal("add")
                        .executes((context) -> {
                            return add((ServerCommandSource)context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                        }))
                .then(CommandManager.literal("remove")
                        .executes((context) -> {
                            return remove((ServerCommandSource)context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                        }))

        );

    }

    private static int add(ServerCommandSource source) {
        //((PlayerEntityExt) Objects.requireNonNull(source.getPlayer())).setCombusometerEffect(true);
        CombustometerManager.players.add(source.getPlayer().getUuid());
        return 1;
    }
    private static int remove (ServerCommandSource source){
        //((PlayerEntityExt) Objects.requireNonNull(source.getPlayer())).setCombusometerEffect(false);
        CombustometerManager.players.remove(source.getPlayer().getUuid());
        return 1;
    }
}
