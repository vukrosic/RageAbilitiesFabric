package net.vukrosic.custommobswordsmod.networking.packet.C2S;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;

import java.util.Objects;

public class SuperJumpC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){
        if(PlayerAbilities.AbilityTier == 1){
            // give 4x speed effect to the player
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 100, 6));
            ((PlayerEntityExt) player).setSuperJumpActive(true);
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
            ((PlayerEntityExt) player).setSuperJumpActive(true);
        }
        else if(PlayerAbilities.AbilityTier == 4){
            // create ghast fireball entity
            // set velocity to players rotation
            Vec3d vec3d = player.getRotationVector().normalize().multiply(2);
            FireballEntity fireball = new FireballEntity(player.world, player, vec3d.x, vec3d.y, vec3d.z, 1);
        }
    }
}
