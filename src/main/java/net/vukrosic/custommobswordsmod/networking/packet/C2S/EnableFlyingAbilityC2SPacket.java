package net.vukrosic.custommobswordsmod.networking.packet.C2S;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;

public class EnableFlyingAbilityC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){
        if(SetHunterCommand.pray != null && player.getUuid() == SetHunterCommand.pray.getUuid() && PlayerAbilities.AbilityTier == 4) {
            boolean flying = PlayerAbilities.isFlyingEnabled;
            if(flying){
                PlayerAbilities.isFlyingEnabled = false;
            }
            else {
                PlayerAbilities.isFlyingEnabled = true;
            }
        }
    }
}
