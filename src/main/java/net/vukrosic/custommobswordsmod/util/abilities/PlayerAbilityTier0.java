package net.vukrosic.custommobswordsmod.util.abilities;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;

public class PlayerAbilityTier0 {
    public static void activateActiveAbility(PlayerEntity player){
        clearStatusEffects(player);
        applyStatusEffects(player);
    }
    static void applyStatusEffects(PlayerEntity player){
        int duration = 20 * 50000;
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, duration, 6));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, duration, 6));
    }
    static void clearStatusEffects(PlayerEntity player){
        player.clearStatusEffects();
    }

    public static void onPreyTakeDamage(DamageSource damageSource1, float float1, PlayerEntity playerEntity) {
    }

    public static void tick() {
        if(preyIsNull())
            return;
    }

    static boolean preyIsNull(){
        return SetHunterCommand.pray == null;
    }
}
