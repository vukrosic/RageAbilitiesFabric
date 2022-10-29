package net.vukrosic.custommobswordsmod.networking.packet.S2C;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.vukrosic.custommobswordsmod.util.custom.InGameHudMixinExt;
import net.vukrosic.custommobswordsmod.util.manhunt.PreyHealthbarHudManager;

public class PreyHealthS2CPacket {
    public static void receive(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler,
                               PacketByteBuf packetByteBuf, PacketSender packetSender) {
        ((InGameHudMixinExt) MinecraftClient.getInstance().inGameHud).setPreyHealth(packetByteBuf.readFloat());
    }
}
