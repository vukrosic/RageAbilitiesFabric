package net.vukrosic.custommobswordsmod.event;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.*;
//import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.entity.custom.frogking.FrogKingEntity;
import net.vukrosic.custommobswordsmod.networking.ModMessages;
import net.vukrosic.custommobswordsmod.util.custom.InGameHudMixinExt;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_CUSTOMMOBS = "key.category.custommobswordsmod.custommobs";
    public static final String KEY_SHOOT_TONGUE = "key.custommobswordsmod.shoot_tongue";
    public static final String KEY_SHOW_PREY_HP = "key.custommobswordsmod.show_prey_hp";
    //public static final String KEY_SUMMON_SHULKER = "key.custommobswordsmod.summon_shulker";
    public static final String KEY_SHOOT_MOB = "key.custommobswordsmod.shoot_mob";
    public static final String KEY_SHOOT_PLAYER = "key.custommobswordsmod.shoot_player";
    public static final String KEY_SUMMON_SUMMONER_MOB = "key.custommobswordsmod.spawn_summoner_mob";
    //public static final String KEY_ACTIVATE_CHUNKEN_4 = "key.custommobswordsmod.set_chunken_4";
    public static final String KEY_FROG_KING_JUMP = "key.custommobswordsmod.frog_king_jump";

    public static KeyBinding pickMob;
    public static KeyBinding showPreyHP;
    public static KeyBinding summonShulker;
    public static KeyBinding throwMob;
    public static KeyBinding shootPlayer;
    public static KeyBinding playSummonerAnimation;
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
            }

            if(shootPlayer.wasPressed()){
                if(client.player.getVehicle() != null && client.player.getVehicle() instanceof FrogKingEntity){
                    ClientPlayNetworking.send(ModMessages.SHOOT_PLAYER, new PacketByteBuf(Unpooled.buffer()));
                }
            }

            if(playSummonerAnimation.wasPressed()){
                //ServerWorld serverWorld = client.getServer().getWorld(client.player.world.getRegistryKey());
                //PlayerEntity player = serverWorld.getPlayerByUuid(client.player.getUuid());
                ClientPlayNetworking.send(ModMessages.PLAY_SUMMONER_ANIMATION, new PacketByteBuf(Unpooled.buffer()));
            }

            //if(setChunkenTo4.wasPressed()){
                //ChunkenPhaseManager.set4Phase();
            //}

            if(superJump.wasPressed()){

                //client.player.getInventory().clear();
                //client.player.getInventory().dropAll();

                if(client.player.getVehicle() != null && client.player.getVehicle() instanceof FrogKingEntity) {
                    ClientPlayNetworking.send(ModMessages.SUPER_JUMP, new PacketByteBuf(Unpooled.buffer()));
                }
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

            playSummonerAnimation = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    KEY_SUMMON_SUMMONER_MOB,
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
                    GLFW.GLFW_KEY_SPACE,
                    KEY_CATEGORY_CUSTOMMOBS
            ));

            registerKeyInputs();
        }
}
