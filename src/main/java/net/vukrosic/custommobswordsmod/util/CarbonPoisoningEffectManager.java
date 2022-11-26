package net.vukrosic.custommobswordsmod.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.EffectCommand;
import net.vukrosic.custommobswordsmod.effect.ModEffects;
import net.vukrosic.custommobswordsmod.effect.custom.CarbonPoisoningEffect;
import net.vukrosic.custommobswordsmod.entity.ModEntities;
import net.vukrosic.custommobswordsmod.entity.client.BlinkingWardenEntityRenderer;
import net.vukrosic.custommobswordsmod.entity.custom.BlinkingWardenEntity;

import java.util.ArrayList;

public class CarbonPoisoningEffectManager {
    
    // make an array list of players who are carbon posioned
    public static ArrayList<PlayerEntity> players = new ArrayList<PlayerEntity>();
    public static ArrayList<Integer> ticksLeft = new ArrayList<>();
    static ArrayList<BlinkingWardenEntity> blinkingWardenEntities = new ArrayList<>();

    static int emptyWardensCounter = 0;

    public static int maxticksLeft = 1000;

    public static void addPlayer(PlayerEntity player) {
        if(!players.contains(player)) {
            players.add(player);
            ticksLeft.add(maxticksLeft);
            player.addStatusEffect(new StatusEffectInstance(ModEffects.CARBONPOISONING, maxticksLeft, 0, false, false, false));
        }
    }


    public static boolean isPlayerInList(PlayerEntity player) {
        return players.contains(player);
    }

    public static void tick() {
        if(emptyWardensCounter == 1) {
            emptyBlinkingWardens();
        }
        emptyWardensCounter--;




        // randomly spawn a warden
        if(Math.random() < 0.01) {
            // spawn a warden
            randomlySpawnBlinkingWardens();
        }



        // decrement every player's fire ticks
        for(int i = 0; i < ticksLeft.size(); i++) {
            ticksLeft.set(i, ticksLeft.get(i) - 1);
            if(ticksLeft.get(i) == maxticksLeft/3) {
                players.get(i).addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, maxticksLeft/2, 1, false, false, false));
                players.remove(i);
                ticksLeft.remove(i);
            }
        }
/*
        // remove players with 0 fire ticks
        for(int i = 0; i < ticksLeft.size(); i++) {
            if(ticksLeft.get(i) <= 0) {
                convertInventoryItems(players.get(i));
                players.remove(i);
                ticksLeft.remove(i);
            }
        }*/

    }

    private static void randomlySpawnBlinkingWardens() {
        emptyWardensCounter = 20;
        // randomly spawn blinking wardens around the player

    }

    private static void emptyBlinkingWardens() {
        blinkingWardenEntities.forEach(entity -> entity.remove(Entity.RemovalReason.DISCARDED));
    }
}
