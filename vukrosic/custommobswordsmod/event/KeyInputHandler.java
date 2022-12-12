package net.vukrosic.custommobswordsmod.event;

import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
//import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.CustomMobSwordsMod;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.networking.ModMessages;
import net.vukrosic.custommobswordsmod.util.custom.ChestsLootedByHuntersManager;
import net.vukrosic.custommobswordsmod.util.custom.IExampleAnimatedPlayer;
import net.vukrosic.custommobswordsmod.util.custom.InGameHudMixinExt;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String ABILITIES_CATEGORY = "key.category.custommobswordsmod.custommobs";
    public static final String KEY_PICK_MOB = "key.custommobswordsmod.pick_mob";
    public static final String KEY_THROW_MOB = "key.custommobswordsmod.shoot_mob";
    public static final String KEY_USE_ACTIVE_ABILITY = "key.custommobswordsmod.use_active_ability";

    public static KeyBinding pickMob;
    public static KeyBinding throwMob;
    public static KeyBinding useActiveAbility;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (pickMob.wasPressed()) {

                ClientPlayNetworking.send(ModMessages.PICK_MOB, passedData);
                playPickMobAnimation();
            }
            if(throwMob.wasPressed()){
                ClientPlayNetworking.send(ModMessages.THROW_MOB, new PacketByteBuf(Unpooled.buffer()));
                playThrowMobAnimation();
            }
            if(useActiveAbility.wasPressed()){
                ClientPlayNetworking.send(ModMessages.SUPER_JUMP, new PacketByteBuf(Unpooled.buffer()));
            }
        });
    }



    void playPickMobAnimation(){
        // get player who pressed the key
        PlayerEntity player = client.player;
        if(ChestsLootedByHuntersManager.numberOfChestsLootedByHunters < 5 &&
            SetHunterCommand.pray.getUuid() != null &&
            SetHunterCommand.pray.getUuid() != player.getUuid()){
            return;
        }
        //If we want to play the animation, get the animation container
        var animationContainer = ((IExampleAnimatedPlayer)player).custommobswordsmod_getModAnimation();
        //Use setAnimation to set the current animation. It will be played automatically.
        KeyframeAnimation anim = PlayerAnimationRegistry.getAnimation(new Identifier(CustomMobSwordsMod.MOD_ID, "lift"));
        animationContainer.setAnimation(new KeyframeAnimationPlayer(anim));
    }

    void playThrowMobAnimation(){
        //If we want to play the animation, get the animation container
        var animationContainer = ((IExampleAnimatedPlayer)client.player).custommobswordsmod_getModAnimation();
        //Use setAnimation to set the current animation. It will be played automatically.
        KeyframeAnimation anim = PlayerAnimationRegistry.getAnimation(new Identifier(CustomMobSwordsMod.MOD_ID, "throw"));
        animationContainer.setAnimation(new KeyframeAnimationPlayer(anim));
    }





    public static void register() {
            pickMob = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    KEY_PICK_MOB,
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_G,
                    KEY_CATEGORY_CUSTOMMOBS
            ));


            throwMob = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    KEY_THROW_MOB,
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_L,
                    KEY_CATEGORY_CUSTOMMOBS
            ));



            useActiveAbility = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    KEY_USE_ACTIVE_ABILITY,
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_V,
                    KEY_CATEGORY_CUSTOMMOBS
            ));

            registerKeyInputs();
        }





}
