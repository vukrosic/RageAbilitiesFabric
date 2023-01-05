package net.vukrosic.custommobswordsmod.event;

import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
//import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.CustomMobSwordsMod;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.networking.ModMessages;
import net.vukrosic.custommobswordsmod.util.custom.ChestsLootedByHuntersManager;
import net.vukrosic.custommobswordsmod.util.custom.IExampleAnimatedPlayer;
import org.lwjgl.glfw.GLFW;

import java.util.UUID;

public class KeyInputHandler {
    public static final String ABILITIES_CATEGORY = "key.category.custommobswordsmod.custommobs";
    public static final String KEY_PICK_MOB = "key.custommobswordsmod.pick_mob";
    public static final String KEY_THROW_MOB = "key.custommobswordsmod.shoot_mob";
    public static final String KEY_USE_ACTIVE_ABILITY = "key.custommobswordsmod.use_active_ability";
    public static final String KEY_LEVEL_UP_ABILITY = "key.custommobswordsmod.level_up_ability";
    public static final String KEY_SPAWN_LIGHTNING = "key.custommobswordsmod.spawn_lightning";
    private static final String KEY_ENABLE_FLYING = "key.custommobswordsmod.enable_flying";
    private static final String KEY_SHOOT_GHAST_FIREBALL = "key.custommobswordsmod.shoot_ghast_fireball";

    public static KeyBinding pickMob;
    public static KeyBinding throwMob;
    public static KeyBinding useActiveAbility;
    public static KeyBinding levelUpAbility;
    public static KeyBinding lightningAbility;
    public static KeyBinding enableFlyingAbility;
    public static KeyBinding shootGhastFireball;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (pickMob.wasPressed()) {
                ClientPlayNetworking.send(ModMessages.PICK_MOB, new PacketByteBuf(Unpooled.buffer()));
                playPickMobAnimation(client);
            }
            if(throwMob.wasPressed()){
                ClientPlayNetworking.send(ModMessages.THROW_MOB, new PacketByteBuf(Unpooled.buffer()));
                playThrowMobAnimation(client);
            }
            if(useActiveAbility.wasPressed()){
                ClientPlayNetworking.send(ModMessages.USE_ACTIVE_ABILITY, new PacketByteBuf(Unpooled.buffer()));
            }
            if(levelUpAbility.wasPressed()){
                ClientPlayNetworking.send(ModMessages.LEVEL_UP_ABILITY, new PacketByteBuf(Unpooled.buffer()));
            }
            if(lightningAbility.wasPressed()){
                ClientPlayNetworking.send(ModMessages.SPAWN_LIGHTNING, new PacketByteBuf(Unpooled.buffer()));
            }
            if(enableFlyingAbility.wasPressed()){
                ClientPlayNetworking.send(ModMessages.ENABLE_FLYING_ABILITY, new PacketByteBuf(Unpooled.buffer()));
            }
            if(shootGhastFireball.wasPressed()){
                ClientPlayNetworking.send(ModMessages.SHOOT_GHAST_FIREBALL, new PacketByteBuf(Unpooled.buffer()));
            }
        });
    }



    static void playPickMobAnimation(MinecraftClient client){
        // get player who pressed the key

        PlayerEntity player = MinecraftClient.getInstance().player;
        //If we want to play the animation, get the animation container
        var animationContainer = ((IExampleAnimatedPlayer)player).custommobswordsmod_getModAnimation();
        //Use setAnimation to set the current animation. It will be played automatically.
        KeyframeAnimation anim = PlayerAnimationRegistry.getAnimation(new Identifier(CustomMobSwordsMod.MOD_ID, "lift"));
        animationContainer.setAnimation(new KeyframeAnimationPlayer(anim));
/*
        //If we want to play the animation, get the animation container
        var animationContainer = ((IExampleAnimatedPlayer)player).custommobswordsmod_getModAnimation();
        //Use setAnimation to set the current animation. It will be played automatically.
        KeyframeAnimation anim = PlayerAnimationRegistry.getAnimation(new Identifier(CustomMobSwordsMod.MOD_ID, "lift"));
        animationContainer.setAnimation(new KeyframeAnimationPlayer(anim));*/
    }

    static void playThrowMobAnimation(MinecraftClient client){
        PlayerEntity player = MinecraftClient.getInstance().player;
        //If we want to play the animation, get the animation container
        var animationContainer = ((IExampleAnimatedPlayer)player).custommobswordsmod_getModAnimation();
        //Use setAnimation to set the current animation. It will be played automatically.
        KeyframeAnimation anim = PlayerAnimationRegistry.getAnimation(new Identifier(CustomMobSwordsMod.MOD_ID, "throw"));
        animationContainer.setAnimation(new KeyframeAnimationPlayer(anim));
    }





    public static void register() {
            pickMob = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    KEY_PICK_MOB,
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_G,
                    ABILITIES_CATEGORY
            ));


            throwMob = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    KEY_THROW_MOB,
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_L,
                    ABILITIES_CATEGORY
            ));

            useActiveAbility = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    KEY_USE_ACTIVE_ABILITY,
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_V,
                    ABILITIES_CATEGORY
            ));


            levelUpAbility = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    KEY_LEVEL_UP_ABILITY,
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_M,
                    ABILITIES_CATEGORY
            ));

             lightningAbility = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    KEY_SPAWN_LIGHTNING,
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_B,
                    ABILITIES_CATEGORY
            ));
             enableFlyingAbility = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_ENABLE_FLYING,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                ABILITIES_CATEGORY
            ));
            shootGhastFireball = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SHOOT_GHAST_FIREBALL,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                ABILITIES_CATEGORY
        ));
                registerKeyInputs();
        }





}
