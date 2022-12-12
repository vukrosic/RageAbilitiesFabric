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
    public static final String KEY_CATEGORY_CUSTOMMOBS = "key.category.custommobswordsmod.custommobs";
    public static final String KEY_SHOOT_TONGUE = "key.custommobswordsmod.shoot_tongue";
    public static final String KEY_SHOW_PREY_HP = "key.custommobswordsmod.show_prey_hp";
    //public static final String KEY_SUMMON_SHULKER = "key.custommobswordsmod.summon_shulker";
    public static final String KEY_SHOOT_MOB = "key.custommobswordsmod.shoot_mob";
    public static final String KEY_SHOOT_PLAYER = "key.custommobswordsmod.shoot_player";
    public static final String KEY_ACTIVATE_FIRE_ABILITY = "key.custommobswordsmod.spawn_summoner_mob";
    //public static final String KEY_ACTIVATE_CHUNKEN_4 = "key.custommobswordsmod.set_chunken_4";
    public static final String KEY_FROG_KING_JUMP = "key.custommobswordsmod.frog_king_jump";

    public static KeyBinding pickMob;
    public static KeyBinding showPreyHP;
    public static KeyBinding summonShulker;
    public static KeyBinding throwMob;
    public static KeyBinding shootPlayer;
    public static KeyBinding activateFireAbility;
    //public static KeyBinding setChunkenTo4;
    public static KeyBinding superJump;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (pickMob.wasPressed()) {
                Vec3d cameraPos = client.player.getCameraPosVec(0);
                // create byte buffer
                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                // write data to buffer
                passedData.writeDouble(cameraPos.x);
                passedData.writeDouble(cameraPos.y);
                passedData.writeDouble(cameraPos.z);
                ClientPlayNetworking.send(ModMessages.PICK_MOB, passedData);
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



            if(showPreyHP.wasPressed()){
                ((InGameHudMixinExt)client.inGameHud).setShowPreyHealthbar(!((InGameHudMixinExt)client.inGameHud).getShowPreyHealthbar());
            }
            /*
            if (summonShulker.wasPressed()) {
                if(SetHunterCommand.pray != null){
                    ((PlayerEntityExt)SetHunterCommand.pray).SummonShieldingShulker();
                }
            }*/

            if(throwMob.wasPressed()){
                ClientPlayNetworking.send(ModMessages.THROW_MOB, new PacketByteBuf(Unpooled.buffer()));
                //If we want to play the animation, get the animation container
                var animationContainer = ((IExampleAnimatedPlayer)client.player).custommobswordsmod_getModAnimation();
                //Use setAnimation to set the current animation. It will be played automatically.
                KeyframeAnimation anim = PlayerAnimationRegistry.getAnimation(new Identifier(CustomMobSwordsMod.MOD_ID, "throw"));
                animationContainer.setAnimation(new KeyframeAnimationPlayer(anim));
            }

            if(shootPlayer.wasPressed()){

            }

            if(activateFireAbility.wasPressed()){
                //ServerWorld serverWorld = client.getServer().getWorld(client.player.world.getRegistryKey());
                //PlayerEntity player = serverWorld.getPlayerByUuid(client.player.getUuid());
                ClientPlayNetworking.send(ModMessages.ACTIVATE_FIRE_ABILITY, new PacketByteBuf(Unpooled.buffer()));
            }

            //if(setChunkenTo4.wasPressed()){
                //ChunkenPhaseManager.set4Phase();
            //}

            if(superJump.wasPressed()){
                    ClientPlayNetworking.send(ModMessages.SUPER_JUMP, new PacketByteBuf(Unpooled.buffer()));
            }
        });
    }






    public static void register() {
            pickMob = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    KEY_SHOOT_TONGUE,
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_G,
                    KEY_CATEGORY_CUSTOMMOBS
            ));

            showPreyHP = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    KEY_SHOW_PREY_HP,
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_O,
                    KEY_CATEGORY_CUSTOMMOBS
            ));
/*
            summonShulker = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    KEY_SUMMON_SHULKER,
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_P,
                    KEY_CATEGORY_CUSTOMMOBS
            ));*/

            throwMob = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    KEY_SHOOT_MOB,
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_L,
                    KEY_CATEGORY_CUSTOMMOBS
            ));

            shootPlayer = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    KEY_SHOOT_PLAYER,
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_P,
                    KEY_CATEGORY_CUSTOMMOBS
            ));

            activateFireAbility = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    KEY_ACTIVATE_FIRE_ABILITY,
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_K,
                    KEY_CATEGORY_CUSTOMMOBS
            ));
/*
            setChunkenTo4 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    KEY_ACTIVATE_CHUNKEN_4,
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_N,
                    KEY_CATEGORY_CUSTOMMOBS
            ));*/

            superJump = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    KEY_FROG_KING_JUMP,
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_V,
                    KEY_CATEGORY_CUSTOMMOBS
            ));

            registerKeyInputs();
        }
}
