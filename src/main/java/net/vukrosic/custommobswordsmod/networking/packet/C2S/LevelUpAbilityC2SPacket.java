package net.vukrosic.custommobswordsmod.networking.packet.C2S;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.vukrosic.custommobswordsmod.command.AbilitiesCommand;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.util.abilities.*;

public class LevelUpAbilityC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // remove boss bar

        if (SetHunterCommand.pray != null && player.getUuid() == SetHunterCommand.pray.getUuid()) {
            AbilitiesCommand.hitBossBarDisable(player.getCommandSource());
            PlayerAbilities.levelUp();
            if(PlayerAbilities.ActiveAbility){
                upgradeActiveAbility(player);
            }
            if (PlayerAbilities.AbilityTier == 4) {
                // enable flying for the player
                player.getAbilities().allowFlying = true;
            }
        }
    }

    private static void upgradeActiveAbility(ServerPlayerEntity player) {
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
    }
}
