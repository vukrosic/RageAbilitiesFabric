package net.vukrosic.custommobswordsmod.networking.packet.C2S;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.entity.custom.LivingEntityExt;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import net.vukrosic.custommobswordsmod.item.custom.ItemEntityMixinExt;
import net.vukrosic.custommobswordsmod.networking.ModMessages;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;

import java.util.ArrayList;
import java.util.List;

public class PickMobC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){
        if(SetHunterCommand.pray != null && player.getUuid() == SetHunterCommand.pray.getUuid()) {
            //bounceUpBlocksAround();
            //((PlayerEntityExt) player).pickUpBlocksInXBlockRadius(5);
            //pickupBlocksInXBlockRadius(player, 9,5);

            if (PlayerAbilities.AbilityTier == 1) {
                pickupAllEntitiesInXBlockRadius(player, 10);
            } else if (PlayerAbilities.AbilityTier == 2) {
                pickupAllEntitiesInXBlockRadius(player, 20);
                pickupBlocksInXBlockRadius(player, 10, 5);
            } else if (PlayerAbilities.AbilityTier == 3) {
                pickupAllEntitiesInXBlockRadius(player, 60);
                pickupBlocksInXBlockRadius(player, 10, 15);
            } else if (PlayerAbilities.AbilityTier == 4) {
                pickupAllEntitiesInXBlockRadius(player, 100);
                pickupBlocksInXBlockRadius(player, 10, 30);
            }
        }
    }

    static void sendPlayerAnimationPacket(ServerPlayerEntity player){
        if(SetHunterCommand.pray != null) {
            PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());
            buffer.writeUuid(SetHunterCommand.pray.getUuid());
            //ServerPlayNetworking.send(SetHunterCommand.pray, ModMessages.LIFT_MOBS_ANIMATION, buffer);

        }
    }

    static void pickupAllEntitiesInXBlockRadius(ServerPlayerEntity player, int radius) {
        ArrayList<LivingEntity> entities = new ArrayList<>();
        for (LivingEntity entity : player.world.getEntitiesByClass(LivingEntity.class, new Box(player.getBlockPos()).expand(radius), entity -> true)) {
            if (entity instanceof PlayerEntity || entity instanceof CreeperEntity) {
                continue;
            }
            entities.add(entity);
            entity.setVelocity(0, 0, 0);
            entity.setPos(player.getX(), player.getY() + 2, player.getZ());
            ((LivingEntityExt) entity).setBeingPickedByPlayer(true);
            entity.setNoGravity(true);
            PlayerAbilities.pickedEntities.add(entity);
            ((LivingEntityExt) entity).setPicker(player);
            if(PlayerAbilities.AbilityTier == 1){
                break;
            }
        }
        if(entities.size() != 0){
            sendPlayerAnimationPacket(player);
        }
    }

    static void pickupBlocksInXBlockRadius(ServerPlayerEntity player, int radius, int numOfBlocks) {
        ArrayList<BlockPos> blocks = new ArrayList<>();
        // get 5 random position around dthe player in 5 block radius
        for (int i = 0; i < numOfBlocks; i++) {
            BlockPos pos = player.getBlockPos().add(player.world.random.nextInt(radius), 0, player.world.random.nextInt(radius));
            if (player.world.getBlockState(pos).getBlock() == Blocks.AIR) {
                continue;
            }
            blocks.add(pos);
        }

        List<BlockState> blockStateList = new ArrayList<>();
        for (BlockPos blockPos : blocks) {
            blockStateList.add(SetHunterCommand.pray.world.getBlockState(blockPos));
        }
        // spawn item entity for all blocks
        for (int i = 0; i < blockStateList.size(); i++) {
            ItemEntity itemEntity = new ItemEntity(SetHunterCommand.pray.world, SetHunterCommand.pray.getX(), SetHunterCommand.pray.getY(), SetHunterCommand.pray.getZ(),
                    new ItemStack(blockStateList.get(i).getBlock()));
            itemEntity.refreshPositionAndAngles(blocks.get(i).getX(), blocks.get(i).getY() + 1, blocks.get(i).getZ(), 0, 0);
            float force = 0.25F;
            // set position above the player
            itemEntity.setPos(SetHunterCommand.pray.getX(), SetHunterCommand.pray.getY() + 3, SetHunterCommand.pray.getZ());
            itemEntity.setNoGravity(true);

            SetHunterCommand.pray.world.spawnEntity(itemEntity);
            SetHunterCommand.pray.sendMessage(Text.of("position size: " + itemEntity.getPos()), false);
            // remove block
            SetHunterCommand.pray.world.setBlockState(blocks.get(i), Blocks.AIR.getDefaultState());
            //((ItemEntityMixinExt) itemEntity).setPickedUp(true);
        }



        //getBlockPositionsInRadius(player, radius, numOfBlocks, blocks);
        //spawnItemEntityForBlocks(player, radius, blocks);
    }

    private static void spawnItemEntityForBlocks(ServerPlayerEntity player, int radius, ArrayList<BlockPos> blocks) {
        // get block state of all blocks
        List<BlockState> blockStateList = new ArrayList<>();
        for (BlockPos blockPos : blocks) {
            blockStateList.add(SetHunterCommand.pray.world.getBlockState(blockPos));
        }
        // spawn item entity for all blocks
        for (int i = 0; i < blockStateList.size(); i++) {
            // get itemEntityies around the player
            List<ItemEntity> itemEntityList = new ArrayList<>();
            for (ItemEntity itemEntity : SetHunterCommand.pray.world.getEntitiesByClass(ItemEntity.class, SetHunterCommand.pray.getBoundingBox().expand(radius), itemEntity -> true)) {
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

    private static void getBlockPositionsInRadius(ServerPlayerEntity player, int radius, int numOfBlocks, ArrayList<BlockPos> blocks) {
        int counter = 0;
        while(blocks.size() < numOfBlocks && counter < 400) {
            int x = (int) (player.getX() + (Math.random() * radius * 2) - radius);
            int z = (int) (player.getZ() + (Math.random() * radius * 2) - radius);
            // y is from -1 to 1 player position
            int y = (int) (player.getY() + (Math.random() * 2) - 1);
            BlockPos pos = new BlockPos(x, y, z);
            // if this is solid blocks, add it to the list
            if (player.world.getBlockState(pos).isSolidBlock(player.world, pos)) {
                blocks.add(pos);
            }
            counter++;
        }
    }

    static void bounceUpBlocksAround() {
        // make a list of all blocks around the player
        List<BlockPos> blockPosList = new ArrayList<>();
        for (int x = -3; x < 4; x++) {
            for (int y = -1; y < 1; y++) {
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
            ItemEntity itemEntity = new ItemEntity(SetHunterCommand.pray.world, SetHunterCommand.pray.getX(), SetHunterCommand.pray.getY(), SetHunterCommand.pray.getZ(),
                    new ItemStack(blockStateList.get(i).getBlock()));
            // place it on the ground around randomly
            itemEntity.refreshPositionAndAngles(blockPosList.get(i).getX(), blockPosList.get(i).getY() + 1, blockPosList.get(i).getZ(), 0, 0);
            //((ItemEntityMixinExt) itemEntity).setBouncing(true);
            //ServerPlayNetworking.send((ServerPlayerEntity) (Object) SetHunterCommand.pray, ModMessages.BOUNCE_BLOCKS, new PacketByteBuf(Unpooled.buffer()));
            // add velocity away from the player
            float force = 0.25F;
            // set position above the player
            itemEntity.setPos(SetHunterCommand.pray.getX(), SetHunterCommand.pray.getY() + 3, SetHunterCommand.pray.getZ());
            itemEntity.setNoGravity(true);

            SetHunterCommand.pray.world.spawnEntity(itemEntity);
            SetHunterCommand.pray.sendMessage(Text.of("position size: " + itemEntity.getPos()), false);
            // remove block
            SetHunterCommand.pray.world.setBlockState(blockPosList.get(i), Blocks.AIR.getDefaultState());
        }
    }
}
