package net.vukrosic.custommobswordsmod.networking.packet.C2S;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.entity.custom.LivingEntityExt;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import net.vukrosic.custommobswordsmod.item.custom.ItemEntityMixinExt;
import net.vukrosic.custommobswordsmod.networking.ModMessages;
import net.vukrosic.custommobswordsmod.util.ThrowingAnimationManager;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;

import java.util.ArrayList;
import java.util.Random;

public class ThrowMobC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        if (SetHunterCommand.pray != null && player.getUuid() == SetHunterCommand.pray.getUuid()) {
            //throwBlocks(player);
            //((PlayerEntityExt) player).throwBlocks();
            if (PlayerAbilities.pickedEntities != null) {
                float throwForce = 1;
                Vec3d direction = getLookDirection(player, throwForce);
                // throw all mobs in PlayerAbilities.pickedEntities
                for (LivingEntity entity : PlayerAbilities.pickedEntities) {
                    entity.setNoGravity(false);
                    entity.setVelocity(direction.multiply(7));
                    ((LivingEntityExt) entity).setPicker(null);
                    ((LivingEntityExt) entity).setBeingThrownByPrey(true);
                }
                PlayerAbilities.pickedEntities.clear();
                sendPlayerAnimationPacket(player);
            } else {

            }
        }
    }

    private static Vec3d getLookDirection(PlayerEntity player, float throwForce) {
        Vec3d cameraPos = player.getCameraPosVec(0);
        //Vec3d cameraPos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        Vec3d cameraDirection = player.getRotationVec(0);
        Vec3d vec3d3 = cameraDirection.subtract(cameraPos).multiply(throwForce);
        Vec3d entityPos = player.getPos();
        Vec3d lookRotation = player.getRotationVector();
        Vec3d cameraPos1 = player.getCameraPosVec(0);
        Vec3d crossPos = cameraPos1.add(lookRotation.multiply(100));
        Vec3d direction = crossPos.subtract(entityPos).normalize();
        return direction;
    }

    static void sendPlayerAnimationPacket(ServerPlayerEntity player){
        if(SetHunterCommand.pray != null) {
            PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());
            buffer.writeUuid(SetHunterCommand.pray.getUuid());
            //ServerPlayNetworking.send((ServerPlayerEntity) SetHunterCommand.pray, ModMessages.THROW_MOBS_ANIMATION, buffer);

        }
    }

}
