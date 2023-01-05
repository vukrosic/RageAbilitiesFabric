package net.vukrosic.custommobswordsmod.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.vukrosic.custommobswordsmod.CustomMobSwordsMod;
import net.vukrosic.custommobswordsmod.networking.packet.C2S.*;
import net.vukrosic.custommobswordsmod.networking.packet.S2C.*;

public class ModMessages {
    public static final Identifier USE_ACTIVE_ABILITY = new Identifier(CustomMobSwordsMod.MOD_ID, "frog_king_jump");
    public static final Identifier THROW_MOB = new Identifier(CustomMobSwordsMod.MOD_ID, "shoot_mob");
    public static final Identifier PICK_MOB = new Identifier(CustomMobSwordsMod.MOD_ID, "frog_king_tongue");
    public static final Identifier SET_PREY = new Identifier(CustomMobSwordsMod.MOD_ID, "set_prey");
    public static final Identifier SET_BOUNCING = new Identifier(CustomMobSwordsMod.MOD_ID, "set_bouncing");
    public static final Identifier EVERY_TICK = new Identifier(CustomMobSwordsMod.MOD_ID, "every_tick");
    public static final Identifier SPAWN_LIGHTNING = new Identifier(CustomMobSwordsMod.MOD_ID, "tier1runfastandfiretrail");
    public static final Identifier LEVEL_UP_ABILITY = new Identifier(CustomMobSwordsMod.MOD_ID, "level_up_ability");
    public static final Identifier ENABLE_FLYING_ABILITY = new Identifier(CustomMobSwordsMod.MOD_ID, "enable_flying_ability");
    public static final Identifier LIFT_MOBS_ANIMATION = new Identifier(CustomMobSwordsMod.MOD_ID, "lift_mobs_animation");
    public static final Identifier THROW_MOBS_ANIMATION = new Identifier(CustomMobSwordsMod.MOD_ID, "throw_mobs_animation");
    public static final Identifier SHOOT_GHAST_FIREBALL = new Identifier(CustomMobSwordsMod.MOD_ID, "shoot_ghast_fireball");
    public static final Identifier BOUNCE_BLOCKS = new Identifier(CustomMobSwordsMod.MOD_ID, "bounce_blocks");
    public static final Identifier SET_HUNTERS_COMMAND = new Identifier(CustomMobSwordsMod.MOD_ID, "set_hunters_command");
    public static final Identifier TEST_ITEM_ENLARGEMENT = new Identifier(CustomMobSwordsMod.MOD_ID, "test_item_enlargement");


    public static void registerC2SPacket(){
        ServerPlayNetworking.registerGlobalReceiver(USE_ACTIVE_ABILITY, UseActiveAbilityC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(THROW_MOB, ThrowMobC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PICK_MOB, PickMobC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(SPAWN_LIGHTNING, SpawnLightningC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(LEVEL_UP_ABILITY, LevelUpAbilityC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ENABLE_FLYING_ABILITY, EnableFlyingAbilityC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(SHOOT_GHAST_FIREBALL, ShootGhastFireballC2S::receive);
    }

    public static void registerS2CPacket(){
        ClientPlayNetworking.registerGlobalReceiver(LIFT_MOBS_ANIMATION, LiftMobsAnimationS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(THROW_MOBS_ANIMATION, ThrowMobsAnimationS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SET_PREY, SetHunterCommandS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SET_BOUNCING, SetBouncingS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(EVERY_TICK, EveryTickS2C::receive);
        ClientPlayNetworking.registerGlobalReceiver(BOUNCE_BLOCKS, BounceBlocksS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SET_HUNTERS_COMMAND, SetHunterCommandS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(TEST_ITEM_ENLARGEMENT, TestItemEnlargementS2CPacket::receive);
    }
}
