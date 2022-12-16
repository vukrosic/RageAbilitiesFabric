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
import net.minecraft.text.Text;
import net.minecraft.world.explosion.Explosion;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;

import java.util.ArrayList;

public class SpawnLightningS2CPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        player.sendMessage(Text.of("Lightning strike!"), false);
        if(PlayerAbilities.AbilityTier != 4){
            return;
        }

        ArrayList<LivingEntity> mobs = new ArrayList<LivingEntity>();
        player.world.getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand(60), (entity) -> {
            mobs.add(entity);
            return true;
        });

        for(LivingEntity mob : mobs){
            // get 5 closest mobs
            LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, player.world);
            lightning.refreshPositionAfterTeleport(mob.getX(), mob.getY(), mob.getZ());
            player.world.spawnEntity(lightning);
            // create explosion
            player.world.createExplosion(player, mob.getX(), mob.getY(), mob.getZ(), 5, true, Explosion.DestructionType.DESTROY);
            // create explosion particles
            player.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, mob.getX(), mob.getY(), mob.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }
}
