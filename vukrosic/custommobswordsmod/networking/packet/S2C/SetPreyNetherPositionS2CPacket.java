package net.vukrosic.custommobswordsmod.networking.packet.S2C;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class SetPreyNetherPositionS2CPacket {
    static BlockPos prayNetherPos = new BlockPos(0, 0, 0);
    public static void receive(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler,
                               PacketByteBuf packetByteBuf, PacketSender packetSender) {
        BlockPos pos = packetByteBuf.readBlockPos();
        prayNetherPos = pos;
    }

    public static BlockPos getPrayNetherPos(){
        return prayNetherPos;
    }
}
