package net.vukrosic.custommobswordsmod.util.abilities;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;

import java.util.ArrayList;
import java.util.Objects;

public class PlayerAbilityTier3 {

    public static ArrayList <PlayerEntity> playersWithBurningInventory = new ArrayList<>();
    public static void activateActiveAbility(PlayerEntity player){
        clearStatusEffects(player);
        applyStatusEffects(player);
        spawnFireOrb();
    }




    public static void applyStatusEffects(PlayerEntity player){
        int duration = 20 * 50000;
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, duration, 6));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, duration, 6));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, duration, 1));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, duration, 6));
        ServerCommandSource commandSource = player.getCommandSource();
        CommandManager commandManager = Objects.requireNonNull(player.getServer()).getCommandManager();
        if (commandManager != null) {
            commandManager.executeWithPrefix(commandSource, "/scale set 2");
            PlayerAbilities.preyScaled = true;
        }
    }

    public static void clearStatusEffects(PlayerEntity player){
        player.clearStatusEffects();
    }

    static void spawnFireOrb(){
        /*
        if(fireOrb == null) {
            PlayerEntity user = (PlayerEntity) (Object) this;
            FireOrbEntity enderPearlEntity = new FireOrbEntity(world, user);
            enderPearlEntity.refreshPositionAndAngles(player.getX(), player.getY() + 2, player.getZ(), player.getYaw(), player.getPitch());
            enderPearlEntity.setItem(ModItems.FIRE_ORB.getDefaultStack());
            enderPearlEntity.setNoGravity(true);
            enderPearlEntity.thrower = user;
            player.world.spawnEntity(enderPearlEntity);
            fireOrb = enderPearlEntity;
        }*/
    }

    public static void dropHunterInventoryWhenTheyDamagePrey(DamageSource damageSource1, float float1, PlayerEntity playerEntity) {
        if(damageSource1.getAttacker() instanceof PlayerEntity attacker){
            if(Math.random() < 0.1){
                attacker.getInventory().dropAll();
                attacker.setOnFireFor(5);
            }
        }
    }

    public static void tick() {
        if(preyIsNull() || abilityTierIsNot3())
            return;
    }

    static boolean preyIsNull(){
        return SetHunterCommand.pray == null;
    }

    private static boolean abilityTierIsNot3() {
        return PlayerAbilities.AbilityTier != 3;
    }
}
