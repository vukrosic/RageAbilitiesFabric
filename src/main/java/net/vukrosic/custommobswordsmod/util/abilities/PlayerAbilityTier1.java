package net.vukrosic.custommobswordsmod.util.abilities;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;

public class PlayerAbilityTier1 {
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

    public static void dropHunterInventoryWhenTheyDamagePrey(DamageSource damageSource1, float float1, PlayerEntity playerEntity) {
        if(damageSource1.getAttacker() instanceof PlayerEntity attacker) {
            int dropped = 0;
            while (true) {
                // get inventory and randomly drop 1-3 random itemstacks
                Inventory inventory = attacker.getInventory();
                // get a random stack
                int index = (int) (Math.random() * inventory.size());
                ItemStack stack = inventory.getStack(index);
                if (stack.getItem() != null) {
                    ItemEntity item = attacker.dropStack(stack);
                    // remove stack from inventory at index
                    attacker.getInventory().removeStack(index);
                    item.setPickupDelay(3);
                    item.resetPickupDelay();
                    item.setToDefaultPickupDelay();
                    // teleport item 2 blocks in random x or z direction
                    item.teleport(attacker.getX() + (Math.random() * 2 - 1), attacker.getY(), attacker.getZ() + (Math.random() * 2 - 1));
                    item.refreshPositionAndAngles(attacker.getX() + (Math.random() * 2 - 1), attacker.getY(), attacker.getZ() + (Math.random() * 2 - 1), 0, 0);
                    dropped++;
                    if (dropped >= 3)
                    break;
                }
                // drop it

                /*
                if (Math.random() < 0.1) {
                    attacker.getInventory().dropAll();
                }*/
            }
        }
    }

    public static void tick() {
        if(preyIsNull() || abilityTierIsNot1())
            return;
    }

    static boolean preyIsNull(){
        return SetHunterCommand.pray == null;
    }

    private static boolean abilityTierIsNot1() {
        return PlayerAbilities.AbilityTier != 1;
    }
}
