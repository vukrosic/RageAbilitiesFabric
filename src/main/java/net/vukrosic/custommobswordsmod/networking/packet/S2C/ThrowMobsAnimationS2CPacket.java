package net.vukrosic.custommobswordsmod.networking.packet.S2C;

import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.vukrosic.custommobswordsmod.CustomMobSwordsMod;
import net.vukrosic.custommobswordsmod.util.custom.IExampleAnimatedPlayer;

import java.util.UUID;

public class ThrowMobsAnimationS2CPacket {
    public static void receive(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler,
                               PacketByteBuf packetByteBuf, PacketSender packetSender) {
        playThrowMobAnimation(packetByteBuf.readUuid());
    }

    static void playThrowMobAnimation(UUID uuid){
        PlayerEntity player = MinecraftClient.getInstance().world.getPlayerByUuid(uuid);
        //If we want to play the animation, get the animation container
        var animationContainer = ((IExampleAnimatedPlayer)player).custommobswordsmod_getModAnimation();
        //Use setAnimation to set the current animation. It will be played automatically.
        KeyframeAnimation anim = PlayerAnimationRegistry.getAnimation(new Identifier(CustomMobSwordsMod.MOD_ID, "throw"));
        animationContainer.setAnimation(new KeyframeAnimationPlayer(anim));
    }
}
