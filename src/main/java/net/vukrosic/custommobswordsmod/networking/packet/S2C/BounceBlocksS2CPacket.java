package net.vukrosic.custommobswordsmod.networking.packet.S2C;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.item.custom.ItemEntityMixinExt;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;

import java.util.ArrayList;
import java.util.List;

public class BounceBlocksS2CPacket {


    public static void receive(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        PlayerAbilities.blockEntitiesScaled = true;
    }

    static void bounceUpBlocksAround() {
        // make a list of all blocks around the player
        List<BlockPos> blockPosList = new ArrayList<>();
        for (int x = -3; x < 4; x++) {
            for (int y = -3; y < 4; y++) {
                for (int z = -3; z < 4; z++) {
                    blockPosList.add(SetHunterCommand.pray.getBlockPos().add(x, y, z));
                }
            }
        }
        // get block state of all blocks
        List<BlockState> blockStateList = new ArrayList<>();
        for (BlockPos blockPos : blockPosList) {
            blockStateList.add(SetHunterCommand.pray.world.getBlockState(blockPos));
        }
        // spawn item entity for all blocks
        for (int i = 0; i < blockStateList.size(); i++) {
            // get itemEntityies around the player
            List<ItemEntity> itemEntityList = new ArrayList<>();
            for (ItemEntity itemEntity : SetHunterCommand.pray.world.getEntitiesByClass(ItemEntity.class, SetHunterCommand.pray.getBoundingBox().expand(3), itemEntity -> true)) {
                itemEntityList.add(itemEntity);
            }
            //SetHunterCommand.pray.sendMessage(Text.of("itemEntityList size: " + itemEntityList.size()), false);
            for (ItemEntity itemEntity : itemEntityList) {
                // if itemEntity is a block
                //SetHunterCommand.pray.sendMessage(Text.of("DOES IT EVEN GET TO BounceBlocksS2CPacket "), false);
                ((ItemEntityMixinExt) itemEntity).setBouncing(true);
            }
        }
    }
}
