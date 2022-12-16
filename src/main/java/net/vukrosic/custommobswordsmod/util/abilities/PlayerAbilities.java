package net.vukrosic.custommobswordsmod.util.abilities;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerAbilities {
    public static int AbilityTier = 0;
    // make an array of picked entities
    public static ArrayList<LivingEntity> pickedEntities = new ArrayList<LivingEntity>();
    public static void levelUp() {
        AbilityTier++;
        // clear all status effects
        if(SetHunterCommand.pray != null){
            SetHunterCommand.pray.clearStatusEffects();
        }
        if(MinecraftClient.getInstance() != null) {
            MinecraftClient.getInstance().player.sendMessage(Text.of("Your ability level is now " + AbilityTier), false);
        }

    }

    public static void tick() {
        if(SetHunterCommand.pray != null && pickedEntities.size() > 0) {
            //set position to above the player for each pickedEntity
            for(LivingEntity entity : pickedEntities){
                // set entities above the pray but not at the same place, so they don't stack, use index to offset them
                int index = pickedEntities.indexOf(entity);
                SetHunterCommand.pray.sendMessage(Text.of("index = " + index), false);
                entity.setPos(SetHunterCommand.pray.getX(), SetHunterCommand.pray.getY() + index*2, SetHunterCommand.pray.getZ());
            }
        }
    }
}
