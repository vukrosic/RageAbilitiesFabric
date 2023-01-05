package net.vukrosic.custommobswordsmod.networking.packet.S2C;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;

import java.util.UUID;

public class TestItemEnlargementS2CPacket {
    public static void receive(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        /*

        PlayerAbilities.bigBlocksTest = packetByteBuf.readBoolean();
        BlockPos pos = packetByteBuf.readBlockPos();
        // get entities by uuid
        ItemEntity itemEntity = (ItemEntity) minecraftClient.world.getEntityById(packetByteBuf.readInt());

        SetHunterCommand.pray.sendMessage(Text.of("PlayerAbilities isClient, bigBlocksTest = " + SetHunterCommand.pray.world.isClient() + " " + PlayerAbilities.blockEntitiesScaled), true);
    */
    }

}
