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
    public static final Identifier SET_OVERWORLD_PREY_POS = new Identifier(CustomMobSwordsMod.MOD_ID, "set_overworld_prey_pos");
    public static final Identifier SET_NETHER_PREY_POS = new Identifier(CustomMobSwordsMod.MOD_ID, "set_nether_prey_pos");
    public static final Identifier SET_THEEND_PREY_POS = new Identifier(CustomMobSwordsMod.MOD_ID, "set_theend_prey_pos");
    public static final Identifier SET_PREY = new Identifier(CustomMobSwordsMod.MOD_ID, "set_prey");
    public static final Identifier EVERY_TICK = new Identifier(CustomMobSwordsMod.MOD_ID, "every_tick");
    public static final Identifier PREY_HEALTH_HUD = new Identifier(CustomMobSwordsMod.MOD_ID, "prey_health_hud");
    public static final Identifier SPAWN_LIGHTNING = new Identifier(CustomMobSwordsMod.MOD_ID, "tier1runfastandfiretrail");
    public static final Identifier LEVEL_UP_ABILITY = new Identifier(CustomMobSwordsMod.MOD_ID, "level_up_ability");
    public static final Identifier ENABLE_FLYING_ABILITY = new Identifier(CustomMobSwordsMod.MOD_ID, "enable_flying_ability");

    public static void registerC2SPacket(){
        ServerPlayNetworking.registerGlobalReceiver(USE_ACTIVE_ABILITY, UseActiveAbilityC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(THROW_MOB, ThrowMobC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PICK_MOB, PickMobS2CPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(SPAWN_LIGHTNING, SpawnLightningS2CPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(LEVEL_UP_ABILITY, LevelUpAbilityS2CPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ENABLE_FLYING_ABILITY, EnableFlyingAbilityS2CPacket::receive);
    }

    public static void registerS2CPacket(){
        ClientPlayNetworking.registerGlobalReceiver(SET_OVERWORLD_PREY_POS, SetPreyOverworldPositionS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SET_NETHER_PREY_POS, SetPreyNetherPositionS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SET_THEEND_PREY_POS, SetPreyTheEndPositionS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SET_PREY, SetHunterCommandS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(PREY_HEALTH_HUD, PreyHealthS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(EVERY_TICK, EveryTickS2C::receive);
        //ClientPlayNetworking.registerGlobalReceiver(SET_CHICKEN_EFFECT, SetChickenEffectS2CPacket::receive);
    }
}
