package net.vukrosic.custommobswordsmod.util.abilities;



import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.item.custom.ItemEntityMixinExt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerAbilityTier2 {
    static int jumpTimer = 0;
    public static void activateActiveAbility(PlayerEntity player){
        clearStatusEffects(player);
        applyStatusEffects(player);
    }
    static void applyStatusEffects(PlayerEntity player){
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
    static void clearStatusEffects(PlayerEntity player){
        player.clearStatusEffects();
    }

    public static void dropHunterInventoryWhenTheyDamagePrey(DamageSource damageSource1, float float1, PlayerEntity playerEntity) {
        if(damageSource1.getAttacker() instanceof PlayerEntity attacker){
            if(Math.random() < 0.1){
                attacker.setOnFireFor(5);
            }
            int dropped = 0;
            while (true) {
                // get inventory and randomly drop 1-3 random itemstacks
                Inventory inventory = attacker.getInventory();
                // get a random stack
                int index = (int) (Math.random() * inventory.size());
                ItemStack stack = inventory.getStack(index);
                if (stack.getItem() != null) {
                    ItemEntity item = attacker.dropStack(stack);
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
            }
        }
    }

    public static void tick() {
    }
}
