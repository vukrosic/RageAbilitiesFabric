package net.vukrosic.custommobswordsmod.networking.packet.S2C;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.vukrosic.custommobswordsmod.entity.custom.chunken.ChunkenPhaseManager;
import net.vukrosic.custommobswordsmod.util.FireInfectedPlayers;

public class ChunkenPhaseIncrementS2CPacket {
    public static void receive(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler,
                               PacketByteBuf packetByteBuf, PacketSender packetSender) {
        // everything here happens only on the client
        ChunkenPhaseManager.chunkenPhase = packetByteBuf.readInt();
    }
}
