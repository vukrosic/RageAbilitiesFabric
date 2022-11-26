package net.vukrosic.custommobswordsmod.entity.custom.frogking;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.vukrosic.custommobswordsmod.entity.custom.chunken.ChunkenPhaseManager;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class HunterFrogKingEggEntity extends EggEntity/* EnderPearlEntity */{

    public HunterFrogKingEggEntity(World world, LivingEntity owner) {
        super(world, owner);
    }


    @Override
    protected void onCollision(HitResult hitResult) {
        MinecraftServer server = Objects.requireNonNull(world.getServer());
        CommandManager commandManager = server.getPlayerManager().getServer().getCommandManager();

        ServerWorld serverWorld = (ServerWorld) this.world;
        PlayerEntity shotPlayer = null;
        List<ServerPlayerEntity> players = serverWorld.getServer().getPlayerManager().getPlayerList();
        if(shotPlayer != null) {
            Objects.requireNonNull(shotPlayer.getServer()).getCommandManager().executeWithPrefix(
                    shotPlayer.getCommandSource(), "/execute in " + this.getWorld().getRegistryKey().getValue() + " run teleport " +
                            shotPlayer.getName().getString() + " " + getX() + " " + getY() + " " + getZ());

        }
        particles();
        this.discard();
    }

    // on hit create explosion



    void particles(){
        ServerWorld serverWorld = (ServerWorld) this.world;
        for (int i = 0; i < 50; i++) {
            Random rand = new Random();
            double x = this.getX() + (rand.nextDouble() - 0.5) * 2;
            double y = this.getY() + (rand.nextDouble() - 0.5) * 2;
            double z = this.getZ() + (rand.nextDouble() - 0.5) * 2;
            serverWorld.spawnParticles(ParticleTypes.CRIMSON_SPORE, x, y + 1, z, 1, 0, 0, 0, 1);
            serverWorld.spawnParticles(ParticleTypes.ENCHANT, x, y + 1, z, 1, 0, 0, 0, 1);
        }
        //thrower.kill();
    }

}
