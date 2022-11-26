package net.vukrosic.custommobswordsmod.networking.packet.C2S;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.vukrosic.custommobswordsmod.entity.custom.LivingEntityExt;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import net.vukrosic.custommobswordsmod.entity.custom.frogking.FrogKingEntity;
import net.vukrosic.custommobswordsmod.util.custom.ChestsLootedByHuntersManager;

public class PickMobS2CPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){

        if(ChestsLootedByHuntersManager.numberOfChestsLootedByHunters < 5){
            return;
        }


        float raycastDistance = 20;

        Vec3d cameraPos = player.getCameraPosVec(0);
        //Vec3d cameraPos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        Vec3d cameraDirection = player.getRotationVec(0);
        Vec3d vec3d3 = cameraPos.add(cameraDirection.multiply(raycastDistance));
        Box box = player
                .getBoundingBox()
                .stretch(player.getRotationVec(1.0F).multiply(raycastDistance))
                .expand(100.0D, 100.0D, 100.0D);

        EntityHitResult entityHitResult = ProjectileUtil.raycast(
                player,
                cameraPos,
                vec3d3,
                box,
                (entityx) -> !entityx.isSpectator(),
                raycastDistance
        );


        if (entityHitResult != null && entityHitResult.getEntity() instanceof LivingEntity entity) {
            // disable entity movement
            entityHitResult.getEntity().setVelocity(0, 0, 0);
            // set entity position 2 blocks above the player
            entityHitResult.getEntity().setPos(player.getX(), player.getY() + 2, player.getZ());
            // disable gravity
            entityHitResult.getEntity().setNoGravity(true);
            ((PlayerEntityExt) player).setPickedEntity(entity);
        }
    }
}
