package net.vukrosic.custommobswordsmod.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Vec3d;

import java.util.Collection;

public class RestoreDeathCommand {

    public static PlayerEntity speedruner = SetHunterCommand.pray;
    // save player inventory so we can restore it later
    static PlayerInventory inventory = null;
    // save hunger, xp and health
    static int hunger = 0;
    static int xp = 0;
    static float health = 0;
    static long time = 0;
    // save hunter location
    static Vec3d hunterLocation = null;
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("restore")
                .then(CommandManager.literal("death")
                        .executes((context) -> {
                            return restore(context.getSource(), EntityArgumentType.getEntities(context, "targets"));
                        })));

    }


    public static void saveState(){
        inventory = speedruner.getInventory();
        hunger = speedruner.getHungerManager().getFoodLevel();
        xp = speedruner.experienceLevel;
        health = speedruner.getHealth();
        time = speedruner.world.getTimeOfDay();
        hunterLocation = speedruner.getPos();
    }

    private static int restore(ServerCommandSource source, Collection<? extends Entity> targets) {
        // put speedrunner in creative mode
        speedruner.getServer().getCommandManager().executeWithPrefix(speedruner.getCommandSource(), "/gamemode creative");
        // clear dropped items in 5 block radius around
        speedruner.getServer().getCommandManager().executeWithPrefix(speedruner.getCommandSource(), "/clear @e[type=item,r=5]");
        speedruner.setPosition(hunterLocation);
        // restore speedrunner's inventory
        speedruner.getInventory().clone(inventory);
        // restore hunger, xp and health
        speedruner.getHungerManager().setFoodLevel(hunger);
        speedruner.experienceLevel = xp;
        speedruner.setHealth(health);
        // restore time
        speedruner.getServer().getWorld(speedruner.world.getRegistryKey()).setTimeOfDay(time);
        return 1;
    }
}
