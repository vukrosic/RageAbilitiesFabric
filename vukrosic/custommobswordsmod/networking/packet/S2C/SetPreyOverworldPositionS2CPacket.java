package net.vukrosic.custommobswordsmod.networking.packet.S2C;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class SetPreyOverworldPositionS2CPacket {
    static BlockPos prayPos = new BlockPos(0, 0, 0);
    public static void receive(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler,
                               PacketByteBuf packetByteBuf, PacketSender packetSender) {
        BlockPos pos = packetByteBuf.readBlockPos();
        prayPos = pos;
    }
    
    public static BlockPos getPos(){
        return prayPos;
    }
}
