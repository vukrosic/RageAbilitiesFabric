package net.vukrosic.custommobswordsmod.networking.packet.C2S;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;

public class ShootGhastFireballC2S {
    public static void receive(MinecraftServer minecraftServer, ServerPlayerEntity serverPlayerEntity, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        if(SetHunterCommand.pray != null
                && serverPlayerEntity.getUuid() == SetHunterCommand.pray.getUuid()
                && PlayerAbilities.AbilityTier == 4) {
            SpawnFireBall(serverPlayerEntity);
        }
    }



    private static void SpawnFireBall(ServerPlayerEntity serverPlayerEntity) {
        // get players looking forward vector
        Vec3d lookVec = serverPlayerEntity.getRotationVector();

        // position in front of the player
        Vec3d pos = serverPlayerEntity.getPos().add(lookVec.x, lookVec.y, lookVec.z).multiply(2);
        Vec3d pos2 = pos.normalize().multiply(3);

        FireballEntity fireball = new FireballEntity(serverPlayerEntity.world, serverPlayerEntity, lookVec.x, lookVec.y, lookVec.z, 9);
        serverPlayerEntity.sendMessage(Text.of("lookVec: " + lookVec.toString()), false);
        //fireball.updatePosition(serverPlayerEntity.getX() + pos2.getX() ,serverPlayerEntity.getY() + pos2.getX(), serverPlayerEntity.getZ() + pos2.getX());
        fireball.refreshPositionAndAngles(serverPlayerEntity.getX() + lookVec.getX()*2,serverPlayerEntity.getY() + lookVec.getY()*2, serverPlayerEntity.getZ() + lookVec.getZ()*2, serverPlayerEntity.getYaw(), serverPlayerEntity.getPitch());
        // spawn fireball
        serverPlayerEntity.world.spawnEntity(fireball);
        // set forward velocity
        //fireball.setVelocity(lookVec.x, lookVec.y, lookVec.z);
/*
        Vec3d pos3 = serverPlayerEntity.getPos().add(lookVec.x * 2, lookVec.y * 2, lookVec.z * 2);
        FireballEntity fireball3 = new FireballEntity(serverPlayerEntity.world, serverPlayerEntity, lookVec.x, lookVec.y, lookVec.z, 9);
        // get player head looking forward vector
        Vec3d lookVec2 = serverPlayerEntity.getRotationVector();
        serverPlayerEntity.world.spawnEntity(fireball3);*/

    }


}
