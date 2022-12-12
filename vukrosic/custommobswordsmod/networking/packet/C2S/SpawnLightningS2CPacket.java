package net.vukrosic.custommobswordsmod.networking.packet.C2S;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.explosion.Explosion;

public class SpawnLightningS2CPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // get the closest mob to the player
        // spawn lightning on that mob
        LivingEntity closestMob = player.world.getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT, player, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().expand(10, 10, 10));
        // strike the clasestMob with a lightning bolt
        if (closestMob != null) {
            LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, player.world);
            lightning.refreshPositionAfterTeleport(closestMob.getX(), closestMob.getY(), closestMob.getZ());
            player.world.spawnEntity(lightning);
            // create explosion
            player.world.createExplosion(player, closestMob.getX(), closestMob.getY(), closestMob.getZ(), 5, true, Explosion.DestructionType.DESTROY);
            // create explosion particles
            player.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, closestMob.getX(), closestMob.getY(), closestMob.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }
}
