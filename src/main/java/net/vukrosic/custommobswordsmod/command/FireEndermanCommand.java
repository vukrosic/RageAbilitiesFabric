package net.vukrosic.custommobswordsmod.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.vukrosic.custommobswordsmod.entity.custom.fireenderman.CombustometerManager;

public class FireEndermanCommand {
    public static boolean fireEndermanEnabled = false;
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("combustometer")
                .then(CommandManager.literal("enable")
                        .executes((context) -> {
                            return enable((ServerCommandSource)context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                        }))
                .then(CommandManager.literal("disable")
                        .executes((context) -> {
                            return disable((ServerCommandSource)context.getSource()/*, EntityArgumentType.getEntities(context, "targets")*/);
                        }))

        );

    }

    private static int enable(ServerCommandSource source) {
        fireEndermanEnabled = true;
        return 1;
    }
    private static int disable (ServerCommandSource source){
        fireEndermanEnabled = false;
        return 1;
    }
}
