package net.vukrosic.custommobswordsmod.item.custom;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.vukrosic.custommobswordsmod.entity.ModEntities;
import net.vukrosic.custommobswordsmod.entity.custom.FireOrbEntity;
import net.vukrosic.custommobswordsmod.entity.custom.FirePearlEntity;
import net.vukrosic.custommobswordsmod.item.ModItems;

import java.util.Random;

public class FireOrbItem extends Item {
    public FireOrbItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        //world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        user.getItemCooldownManager().set(this, 20);
        if (!world.isClient) {
            FireOrbEntity enderPearlEntity = new FireOrbEntity(world,user);
            user.sendMessage(Text.of("FireOrbEntity: " + enderPearlEntity), false);
            enderPearlEntity.setItem(ModItems.FIRE_ORB.getDefaultStack());
            enderPearlEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 0);
            enderPearlEntity.thrower = user;
            world.spawnEntity(enderPearlEntity);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }

}
