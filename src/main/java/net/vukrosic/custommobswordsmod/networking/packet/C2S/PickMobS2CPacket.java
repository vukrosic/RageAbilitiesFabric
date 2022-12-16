package net.vukrosic.custommobswordsmod.networking.packet.C2S;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.vukrosic.custommobswordsmod.entity.custom.LivingEntityExt;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;

public class PickMobS2CPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){
        player.sendMessage(Text.of("PlayerAbilities.AbilityTier = " + PlayerAbilities.AbilityTier), false);
        if(PlayerAbilities.AbilityTier == 1){
            pickupAllEntitiesInXBlockRadius(player, 10);
        }
        else if(PlayerAbilities.AbilityTier == 2){
            pickupAllEntitiesInXBlockRadius(player, 10);
        }
        else if(PlayerAbilities.AbilityTier == 3){
            pickupAllEntitiesInXBlockRadius(player, 50);
        }
        else if(PlayerAbilities.AbilityTier == 4){
            pickupAllEntitiesInXBlockRadius(player, 100);
        }



    }

    static void pickupEntityWithRaycast(ServerPlayerEntity player){
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
            player.sendMessage(Text.of("You picked up 1 entity: " + entity.getName()), false);

            // disable entity movement
            entityHitResult.getEntity().setVelocity(0, 0, 0);
            // set entity position 2 blocks above the player
            //entityHitResult.getEntity().setPos(player.getX(), player.getY() + 2, player.getZ());
            // disable gravity
            entityHitResult.getEntity().setNoGravity(true);
            //((PlayerEntityExt) player).setPickedEntity(entity);
            PlayerAbilities.pickedEntities.add(entity);

        }
    }

    static void pickupAllEntitiesInXBlockRadius(ServerPlayerEntity player, int radius) {
        for (LivingEntity entity : player.world.getEntitiesByClass(LivingEntity.class, new Box(player.getBlockPos()).expand(radius), entity -> true)) {
            if (entity instanceof PlayerEntity || entity instanceof CreeperEntity) {
                continue;
            }
            entity.setVelocity(0, 0, 0);
            entity.setPos(player.getX(), player.getY() + 3, player.getZ());
            ((LivingEntityExt) entity).setBeingPickedByPlayer(true);
            entity.setNoGravity(true);
            //((PlayerEntityExt) player).setPickedEntity(entity);
            player.sendMessage(Text.of("You picked up multiple entities: " + entity.getName()), false);
            PlayerAbilities.pickedEntities.add(entity);
            ((LivingEntityExt) entity).setPicker(player);
            if(PlayerAbilities.AbilityTier == 1){
                break;
            }
        }
    }
}
