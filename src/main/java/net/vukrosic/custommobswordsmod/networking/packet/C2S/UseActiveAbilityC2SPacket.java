package net.vukrosic.custommobswordsmod.networking.packet.C2S;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import net.vukrosic.custommobswordsmod.util.abilities.*;

import java.util.Objects;

public class UseActiveAbilityC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){
        if(!checkIfThisIsPrey(player)){
            return;
        }
        if(PlayerAbilities.ActiveAbility){
            deactivateAbility(player);
            PlayerAbilities.ActiveAbility = false;
            return;
        }
        PlayerAbilities.ActiveAbility = true;
        switch (PlayerAbilities.AbilityTier) {
            case 1:
                PlayerAbilityTier1.activateActiveAbility(player);
                break;
            case 2:
                PlayerAbilityTier2.activateActiveAbility(player);
                break;
            case 3:
                PlayerAbilityTier3.activateActiveAbility(player);
                break;
            case 4:
                PlayerAbilityTier4.activateActiveAbility(player);
                break;
            default:
                break;
        }

/*
        if(((PlayerEntityExt) player).getActiveAbilityActive()) {
            ((PlayerEntityExt) player).setActiveAbilityActive(false);
            PlayerAbilityTier1.applyStatusEffects(player);
        }
        else {
            ((PlayerEntityExt) player).setActiveAbilityActive(true);
        }*/
        /*
        if(PlayerAbilities.AbilityTier == 1){
            giveSpeedAndSetFireBehind(player);
        }
        else if(PlayerAbilities.AbilityTier == 2){
            ServerCommandSource commandSource = server.getCommandSource();
            CommandManager commandManager = Objects.requireNonNull(player.getServer()).getCommandManager();
            if (commandManager != null) {
                // remove effects
                player.removeStatusEffect(StatusEffects.SPEED);
                commandManager.executeWithPrefix(commandSource, "/scale set 2");
                // set jump mboost to 3
                commandManager.executeWithPrefix(commandSource, "/effect give @s minecraft:jump_boost 100 3");
                // give resitance 5
                commandManager.executeWithPrefix(commandSource, "/effect give @s minecraft:resistance 100 5");
            }
        }
        else if(PlayerAbilities.AbilityTier == 3){
            player.addVelocity(0, 10, 0);
            ((PlayerEntityExt) player).setActiveAbilityActive(true);
        }
        else if(PlayerAbilities.AbilityTier == 4){
            // create ghast fireball entity
            // set velocity to players rotation
            Vec3d vec3d = player.getRotationVector().normalize().multiply(2);
            FireballEntity fireball = new FireballEntity(player.world, player, vec3d.x, vec3d.y, vec3d.z, 1);
        }*/
    }

    private static void deactivateAbility(PlayerEntity player) {
        // clear all effects
        player.clearStatusEffects();
        ServerCommandSource commandSource = player.getCommandSource();
        CommandManager commandManager = Objects.requireNonNull(player.getServer()).getCommandManager();
        if (commandManager != null) {
            commandManager.executeWithPrefix(commandSource, "/scale set 1");
            PlayerAbilities.preyScaled = false;
            //increasedSize = false;
        }/*
        if(fireOrb != null){
            fireOrb.discard();
            fireOrb = null;
        }*/
    }

    private static boolean checkIfThisIsPrey(ServerPlayerEntity player) {
        if(SetHunterCommand.pray != null && !SetHunterCommand.pray.getUuid().equals(player.getUuid())){
            return false;
        }
        return true;
    }
}
