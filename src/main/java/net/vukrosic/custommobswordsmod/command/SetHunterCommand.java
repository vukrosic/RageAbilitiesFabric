package net.vukrosic.custommobswordsmod.command;

import com.mojang.brigadier.CommandDispatcher;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.vukrosic.custommobswordsmod.networking.ModMessages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class SetHunterCommand {

    public static PlayerEntity pray = null;
    public static UUID prayUuid = null;

    public static ArrayList<PlayerEntity> hunters = new ArrayList<>();
    public static ArrayList<UUID> huntersUUIDs = new ArrayList<>();


    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("manhunt")
                .then(CommandManager.literal("setpray")
                        .then(CommandManager.argument("targets", EntityArgumentType.entities())
                                .executes((context) -> {
                                    return setPray((ServerCommandSource)context.getSource(), EntityArgumentType.getEntities(context, "targets"));
                                })))
                .then(CommandManager.literal("sethunter")
                        .then(CommandManager.argument("targets", EntityArgumentType.entities())
                                .executes((context) -> {
                                    return setHunter((ServerCommandSource)context.getSource(), EntityArgumentType.getEntities(context, "targets"));
                                })))
                .then(CommandManager.literal("reset")
                        .executes((context) -> {
                                    return reset((ServerCommandSource)context.getSource(), EntityArgumentType.getEntities(context, "targets"));
                                })));

    }
    private static int setPray(ServerCommandSource source, Collection<? extends Entity> targets) {
        for (Entity entity : targets) {
            if (entity instanceof PlayerEntity) {
                if(hunters.contains(entity)){
                    hunters.remove(entity);
                }
                pray = (PlayerEntity) entity;
                prayUuid = entity.getUuid();
                PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                buf.writeUuid(pray.getUuid());
                ServerPlayNetworking.send((ServerPlayerEntity) entity, ModMessages.SET_PREY, buf);
            }
        }
        return 1;
    }
    private static int setHunter(ServerCommandSource source, Collection<? extends Entity> targets) {
        for (Entity entity : targets) {
            if (entity instanceof PlayerEntity) {
                if(prayUuid == entity.getUuid()){
                    pray = null;
                    prayUuid = null;
                }
                hunters.add((PlayerEntity) entity);
                huntersUUIDs.add(((PlayerEntity) entity).getUuid());
            }
        }
        return 1;
    }

    private static int reset(ServerCommandSource source, Collection<? extends Entity> targets) {
        pray = null;
        hunters.clear();
        prayUuid = null;
        huntersUUIDs.clear();
        return 1;
    }

    /*
    Commands:

    /manhunt setpray <player>
    /manhunt sethunter <player>
    /manhunt reset

    /butherboy removebars

    /chunken reset
    /chunken removeEffect
    /chunken giveEffect
    /chunken setChickenDimPlayer
    /chunken setFinalPhase

    /summoner activate
    /summoner deactivate
    /summoner clearAll

    /combustometer add
    /combustometer remove


    Notes (no need to remember, just read before you use the mob)
    Summoner:
    1. try not to die before deactivating summoner, might stay there as a client ghost (just stands there where yoiu died)
    2. with summoner activated you can for example get in F5 front view, press F1 to hide hud and it looks good]

    Chunken:
    1. I guess if they fall into void in chickendim just tp them back up with a command
     */

}
