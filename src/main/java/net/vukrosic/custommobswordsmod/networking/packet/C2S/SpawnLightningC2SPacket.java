package net.vukrosic.custommobswordsmod.networking.packet.C2S;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;

import java.util.ArrayList;

public class SpawnLightningC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        if(!checkIfThisIsPrey(player)){
            return;
        }
        if (PlayerAbilities.AbilityTier != 4) {
            return;
        }

        ArrayList<LivingEntity> mobs = getMobsWithinRange(player);
        strikeLightningOnMobs(player, mobs);
        spawnStalactites(player, mobs);

        /*
        ArrayList<LivingEntity> mobs = new ArrayList<LivingEntity>();
        player.world.getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand(60), (entity) -> {
            if(entity.getUuid() != SetHunterCommand.pray.getUuid()) {
                mobs.add(entity);
            }
            return true;
        });
        int mobsStruck = 0;
        for (LivingEntity mob : mobs) {
            // get 5 closest mobs
            LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, player.world);
            lightning.refreshPositionAfterTeleport(mob.getX(), mob.getY(), mob.getZ());
            player.world.spawnEntity(lightning);
            // create explosion
            player.world.createExplosion(player, mob.getX(), mob.getY(), mob.getZ(), 1, true, Explosion.DestructionType.DESTROY);
            mobsStruck++;
            if (mobsStruck >= 5) {
                break;
            }
        }*/
    }

    static boolean checkIfThisIsPrey(ServerPlayerEntity player){
        return SetHunterCommand.pray != null && player.getUuid() == SetHunterCommand.pray.getUuid();
    }

    private static ArrayList<LivingEntity> getMobsWithinRange(ServerPlayerEntity player) {
        ArrayList<LivingEntity> mobs = new ArrayList<LivingEntity>();
        player.world.getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand(20), (entity) -> {
            if(entity.getUuid() != SetHunterCommand.pray.getUuid()) {
                mobs.add(entity);
            }
            return true;
        });
        return mobs;
    }

    private static int strikeLightningOnMobs(ServerPlayerEntity player, ArrayList<LivingEntity> mobs) {
        int mobsStruck = 0;
        for (LivingEntity mob : mobs) {
            // get 5 closest mobs
            LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, player.world);
            lightning.refreshPositionAfterTeleport(mob.getX(), mob.getY(), mob.getZ());
            player.world.spawnEntity(lightning);
            // create explosion
            //player.world.createExplosion(player, mob.getX(), mob.getY(), mob.getZ(), 1, true, Explosion.DestructionType.DESTROY);
            mobsStruck++;
            if (mobsStruck >= 5) {
                break;
            }
        }
        //spawnStalactites(player);
        return mobsStruck;
    }

    private static void spawnStalactites(ServerPlayerEntity player, ArrayList<LivingEntity> mobs) {
        for (LivingEntity mod : mobs) {
            SetHunterCommand.pray.sendMessage(Text.of("SpawnLightingC2S first spawning works on mobs!"), false);
            //player.world.setBlockState(mod.getBlockPos().add(0,12,0), Blocks.ANVIL.getDefaultState());
            //player.world.setBlockState(mod.getBlockPos().add(0,15,0), Blocks.TNT.getDefaultState());
            player.world.setBlockState(mod.getBlockPos().add(0,7,0), Blocks.POINTED_DRIPSTONE.getDefaultState());
            ItemEntity dripStone = new ItemEntity(player.world, mod.getBlockPos().getX(), mod.getBlockPos().getY(), mod.getBlockPos().getZ(), Blocks.POINTED_DRIPSTONE.asItem().getDefaultStack());
            //ItemEntity dripStone1 = new ItemEntity(player.world, mod.getBlockPos().getX(), mod.getBlockPos().getY(), mod.getBlockPos().getZ(), Blocks.POINTED_DRIPSTONE.asItem().getDefaultStack());
            dripStone.refreshPositionAndAngles(mod.getBlockPos().getX(), mod.getBlockPos().getY() + 5, mod.getBlockPos().getZ(), 0, 0);
            mod.world.spawnEntity(dripStone);


        }
        /*
        for(int i = 0; i < 15; i++){
            SetHunterCommand.pray.sendMessage(Text.of("Stalactite spawned!"), false);
            // choose a random location within a radius of 60 blocks around the player
            double x = player.getX() + 60 * (Math.random() - 0.5);
            double z = player.getZ() + 60 * (Math.random() - 0.5);
            // get random y above the player 20 - 40 blocks
            double y = player.getY() + 20 + 20 * Math.random();
            // spawn Blocks.STONE at that location

            player.world.setBlockState(player.getBlockPos().add(x, y, z), Blocks.ANVIL.getDefaultState());
            player.world.setBlockState(player.getBlockPos().add(x, y, z), Blocks.POINTED_DRIPSTONE.getDefaultState());
            AnvilBlock anvilBlock = (AnvilBlock) Blocks.ANVIL;
        }*/
    }
}
