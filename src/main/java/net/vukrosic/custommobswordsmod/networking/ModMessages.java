package net.vukrosic.custommobswordsmod.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.vukrosic.custommobswordsmod.CustomMobSwordsMod;
import net.vukrosic.custommobswordsmod.networking.packet.C2S.*;
import net.vukrosic.custommobswordsmod.networking.packet.S2C.*;

public class ModMessages {
    public static final Identifier CONVERT_INVENTORY = new Identifier(CustomMobSwordsMod.MOD_ID, "convert_inventory");
    public static final Identifier DAMAGE_TOOLS = new Identifier(CustomMobSwordsMod.MOD_ID, "damage_tools");
    public static final Identifier SUPER_JUMP = new Identifier(CustomMobSwordsMod.MOD_ID, "frog_king_jump");
    public static final Identifier THROW_MOB = new Identifier(CustomMobSwordsMod.MOD_ID, "shoot_mob");
    public static final Identifier SHOOT_PLAYER = new Identifier(CustomMobSwordsMod.MOD_ID, "shoot_player");
    public static final Identifier ACTIVATE_FIRE_ABILITY = new Identifier(CustomMobSwordsMod.MOD_ID, "play_summoner_animation");
    public static final Identifier PICK_MOB = new Identifier(CustomMobSwordsMod.MOD_ID, "frog_king_tongue");
    public static final Identifier SET_OVERWORLD_PREY_POS = new Identifier(CustomMobSwordsMod.MOD_ID, "set_overworld_prey_pos");
    public static final Identifier SET_NETHER_PREY_POS = new Identifier(CustomMobSwordsMod.MOD_ID, "set_nether_prey_pos");
    public static final Identifier SET_THEEND_PREY_POS = new Identifier(CustomMobSwordsMod.MOD_ID, "set_theend_prey_pos");
    public static final Identifier SET_PREY = new Identifier(CustomMobSwordsMod.MOD_ID, "set_prey");
    public static final Identifier EVERY_TICK = new Identifier(CustomMobSwordsMod.MOD_ID, "every_tick");
    public static final Identifier PREY_HEALTH_HUD = new Identifier(CustomMobSwordsMod.MOD_ID, "prey_health_hud");
    public static final Identifier SPAWN_LIGHTNING = new Identifier(CustomMobSwordsMod.MOD_ID, "tier1runfastandfiretrail");

    public static void registerC2SPacket(){
        ServerPlayNetworking.registerGlobalReceiver(SUPER_JUMP, SuperJumpC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(THROW_MOB, ThrowMobC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ACTIVATE_FIRE_ABILITY, ActivateFireAbilityC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PICK_MOB, PickMobS2CPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(SPAWN_LIGHTNING, SpawnLightningS2CPacket::receive);
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
