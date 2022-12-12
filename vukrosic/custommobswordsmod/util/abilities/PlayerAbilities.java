package net.vukrosic.custommobswordsmod.util.abilities;

import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerAbilities {
    public static int AbilityTier = 0;
    // make an array of picked entities
    public static ArrayList<LivingEntity> pickedEntities;
    public static void levelUp() {
        AbilityTier++;
    }

    public static void tick() {
    }
}
