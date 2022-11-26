package net.vukrosic.custommobswordsmod.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.vukrosic.custommobswordsmod.CustomMobSwordsMod;
import net.vukrosic.custommobswordsmod.networking.packet.C2S.*;
import net.vukrosic.custommobswordsmod.networking.packet.S2C.*;

public class ModMessages {
    public static final Identifier FIRE_INFECTED_ID = new Identifier(CustomMobSwordsMod.MOD_ID, "fire_infected");
    public static final Identifier FIRE_INFECTED_SYNC_ID = new Identifier(CustomMobSwordsMod.MOD_ID, "fire_infected_sync");
    public static final Identifier EXAMPLE_ID = new Identifier(CustomMobSwordsMod.MOD_ID, "example");
    public static final Identifier CHICKEN_EFFECT_POST_ID = new Identifier(CustomMobSwordsMod.MOD_ID, "chicken_effect_post");
    public static final Identifier NATE_DIM_ID = new Identifier(CustomMobSwordsMod.MOD_ID, "nate_dim");

    public static final Identifier CONVERT_INVENTORY = new Identifier(CustomMobSwordsMod.MOD_ID, "convert_inventory");
    public static final Identifier DAMAGE_TOOLS = new Identifier(CustomMobSwordsMod.MOD_ID, "damage_tools");
    public static final Identifier SUPER_JUMP = new Identifier(CustomMobSwordsMod.MOD_ID, "frog_king_jump");
    public static final Identifier THROW_MOB = new Identifier(CustomMobSwordsMod.MOD_ID, "shoot_mob");
    public static final Identifier SHOOT_PLAYER = new Identifier(CustomMobSwordsMod.MOD_ID, "shoot_player");
    public static final Identifier PLAY_SUMMONER_ANIMATION = new Identifier(CustomMobSwordsMod.MOD_ID, "play_summoner_animation");
    public static final Identifier PICK_MOB = new Identifier(CustomMobSwordsMod.MOD_ID, "frog_king_tongue");
    public static final Identifier SET_OVERWORLD_PREY_POS = new Identifier(CustomMobSwordsMod.MOD_ID, "set_overworld_prey_pos");
    public static final Identifier SET_NETHER_PREY_POS = new Identifier(CustomMobSwordsMod.MOD_ID, "set_nether_prey_pos");
    public static final Identifier SET_THEEND_PREY_POS = new Identifier(CustomMobSwordsMod.MOD_ID, "set_theend_prey_pos");
    public static final Identifier SET_PREY_ON_CLIENT = new Identifier(CustomMobSwordsMod.MOD_ID, "set_prey_on_client");
    public static final Identifier CHUNKEN_PHASE_INCREMENT = new Identifier(CustomMobSwordsMod.MOD_ID, "chunken_phase_increment");
    public static final Identifier CHUNKEN_PHASE_RESET = new Identifier(CustomMobSwordsMod.MOD_ID, "chunken_phase_reset");
    public static final Identifier CHUNKEN_PHASE_FINAL = new Identifier(CustomMobSwordsMod.MOD_ID, "chunken_phase_final");
    public static final Identifier SET_PREY = new Identifier(CustomMobSwordsMod.MOD_ID, "set_prey");
    public static final Identifier PREY_HEALTH_HUD = new Identifier(CustomMobSwordsMod.MOD_ID, "prey_health_hud");
    //public static final Identifier SET_CHICKEN_EFFECT = new Identifier(CustomMobSwordsMod.MOD_ID, "prey_health_hud");

    public static void registerC2SPacket(){
        ServerPlayNetworking.registerGlobalReceiver(EXAMPLE_ID, ExampleC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(CONVERT_INVENTORY, RemoveItemsC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(DAMAGE_TOOLS, DamageToolsC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(SUPER_JUMP, SuperJumpC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(THROW_MOB, ThrowMobC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(SHOOT_PLAYER, FrogKingShootPlayerC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PLAY_SUMMONER_ANIMATION, PlaySummonerAnimationC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PICK_MOB, PickMobS2CPacket::receive);
        //ServerPlayNetworking.registerGlobalReceiver(GET_PREY_POS, GetPreyPositionC2SPacket::receive);
    }

    public static void registerS2CPacket(){
        ClientPlayNetworking.registerGlobalReceiver(FIRE_INFECTED_ID, FireInfectedS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(CHICKEN_EFFECT_POST_ID, HasChickenEffectS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(NATE_DIM_ID, IsInNateDim::receive);
        ClientPlayNetworking.registerGlobalReceiver(SET_OVERWORLD_PREY_POS, SetPreyOverworldPositionS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SET_NETHER_PREY_POS, SetPreyNetherPositionS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SET_THEEND_PREY_POS, SetPreyTheEndPositionS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(CHUNKEN_PHASE_INCREMENT, ChunkenPhaseIncrementS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(CHUNKEN_PHASE_RESET, ChunkenPhaseResetS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(CHUNKEN_PHASE_FINAL, ChunkenPhaseFinalS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SET_PREY, SetHunterCommandS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(PREY_HEALTH_HUD, PreyHealthS2CPacket::receive);
        //ClientPlayNetworking.registerGlobalReceiver(SET_CHICKEN_EFFECT, SetChickenEffectS2CPacket::receive);
    }
}
