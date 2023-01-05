package net.vukrosic.custommobswordsmod.util.abilities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;

import java.util.Arrays;
import java.util.Objects;

public class PlayerAbilityTier4 {
    static BlockState[] blockStates = new BlockState[]{Blocks.NETHERRACK.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(),
            Blocks.NETHER_WART_BLOCK.getDefaultState()};
    public static void activateActiveAbility(PlayerEntity player){
        clearStatusEffects(player);
        applyStatusEffects(player);
    }
    static void applyStatusEffects(PlayerEntity player){
        int duration = 20 * 50000;
        player.clearStatusEffects();
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, duration, 6));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, duration, 6));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, duration, 6));
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

    public static void tick() {
        if(preyIsNull() || abilityTierIsNot4())
            return;
        //turnFloorIntoNetherrack();
        flyIfEnabled();
    }

    private static void flyIfEnabled() {
        if(!PlayerAbilities.isFlyingEnabled) {
            return;
        }
        net.minecraft.entity.player.PlayerAbilities abilities = SetHunterCommand.pray.getAbilities();
        if (!SetHunterCommand.pray.isCreative()) {
            abilities.flying = true;
            abilities.allowFlying = true;
            SetHunterCommand.pray.sendAbilitiesUpdate();
        }
        else {
            abilities.flying = false;
            abilities.allowFlying = false;
            SetHunterCommand.pray.sendAbilitiesUpdate();
        }

    }

    private static boolean preyIsNull() {
        return SetHunterCommand.pray == null;
    }

    private static boolean abilityTierIsNot4(){
        return PlayerAbilities.AbilityTier != 4;
    }

    private static void turnFloorIntoNetherrack() {
        if (!SetHunterCommand.pray.isOnGround()){
            return;
        }
        for (int x = -5; x < 5; x++) {
            for (int z = -5; z < 5; z++) {
                //if (!Arrays.asList(blockStates).contains(SetHunterCommand.pray.world.getBlockState(new BlockPos(SetHunterCommand.pray.getX() + x, SetHunterCommand.pray.getY() - 1, SetHunterCommand.pray.getZ() + z)))) {
                    if (!SetHunterCommand.pray.world.getBlockState(new BlockPos(SetHunterCommand.pray.getX() + x, SetHunterCommand.pray.getY() - 1, SetHunterCommand.pray.getZ() + z)).isAir()){
                        //SetHunterCommand.pray.world.setBlockState(new BlockPos(SetHunterCommand.pray.getX() + x, SetHunterCommand.pray.getY() - 1, SetHunterCommand.pray.getZ() + z), blockStates[(int) (Math.random() * blockStates.length) % blockStates.length]);
                        //SetHunterCommand.pray.world.setBlockState(new BlockPos(SetHunterCommand.pray.getX() + x, SetHunterCommand.pray.getY() - 1, SetHunterCommand.pray.getZ() + z), blockStates[((int) (Math.random() * blockStates.length)) % blockStates.length]);
                        // get a random index in blockStates array
                        int randomIndex = (int) (Math.random() * blockStates.length);
                        SetHunterCommand.pray.world.setBlockState(new BlockPos(SetHunterCommand.pray.getX() + x, SetHunterCommand.pray.getY() - 1, SetHunterCommand.pray.getZ() + z), blockStates[randomIndex]);

                    }
                //}
            }
        }
    }

}
