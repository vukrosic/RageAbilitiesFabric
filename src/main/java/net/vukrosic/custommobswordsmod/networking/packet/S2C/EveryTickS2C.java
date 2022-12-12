package net.vukrosic.custommobswordsmod.networking.packet.S2C;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.vukrosic.custommobswordsmod.util.ThrowingAnimationManager;

import java.util.UUID;

public class EveryTickS2C {
    public static void receive(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        // get uuid from buffer
        // get entity from uuid
        // set throwing player
        // check if packetByteBuf.readBoolean() is true
        if(packetByteBuf.readBoolean()){
            UUID uuid = packetByteBuf.readUuid();
            ThrowingAnimationManager.throwingPlayer = minecraftClient.player;
        }

    }
}
