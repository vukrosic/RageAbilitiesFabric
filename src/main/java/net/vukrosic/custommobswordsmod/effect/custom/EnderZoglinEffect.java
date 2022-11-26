package net.vukrosic.custommobswordsmod.effect.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.ZoglinEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.vukrosic.custommobswordsmod.entity.ModEntities;
import net.vukrosic.custommobswordsmod.entity.custom.EnderZoglinEntity;

public class EnderZoglinEffect extends StatusEffect {
    public EnderZoglinEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if(entity instanceof PiglinEntity){
            BlockPos pos = new BlockPos(entity.getX(), entity.getY(), entity.getZ());


            entity.discard();
        }
        else {
            // remove this effect
            ServerWorld serverWorld = (ServerWorld) entity.world;
            serverWorld.getServer().getCommandManager().executeWithPrefix(serverWorld.getServer().getCommandSource(), "effect clear " + entity.getName().getString() + " custommobswordsmod:ender_zoglin");
        }
        //super.onApplied(entity, attributes, amplifier);
    }
}
