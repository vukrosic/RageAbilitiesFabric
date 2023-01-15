package net.vukrosic.custommobswordsmod.networking.packet.S2C;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;

import java.util.ArrayList;

public class SetHunterCommandHunterS2CPacket {
    public static void receive(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        // packetByteBuf will contain multiple uuids, read all of them, get players and set them to hunters
        ArrayList<java.util.UUID> huntersUUIDs = new ArrayList<>();
        while (packetByteBuf.isReadable()) {
            huntersUUIDs.add(packetByteBuf.readUuid());
        }
        for (java.util.UUID uuid : huntersUUIDs) {
            SetHunterCommand.huntersUUIDs.add(uuid);
            SetHunterCommand.hunters.add(minecraftClient.world.getPlayerByUuid(uuid));
        }
        if(Math.random() > 0.2){
            // get list of hunter names
            ArrayList<Text> hunterNames = new ArrayList<>();
            for (PlayerEntity hunter : SetHunterCommand.hunters) {
                hunterNames.add(hunter.getName());
            }
            // convert list to string
            String hunterNamesString = hunterNames.toString();
            minecraftClient.player.sendMessage(Text.of("Hunters = " + hunterNamesString), false);
        }
    }
}
