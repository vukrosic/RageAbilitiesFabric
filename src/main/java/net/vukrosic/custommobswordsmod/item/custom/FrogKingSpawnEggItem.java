package net.vukrosic.custommobswordsmod.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.vukrosic.custommobswordsmod.entity.ModEntities;
import net.vukrosic.custommobswordsmod.entity.custom.chunken.ChunkenEntityGL;
import net.vukrosic.custommobswordsmod.entity.custom.frogking.FrogKingEntity;

public class FrogKingSpawnEggItem extends Item {
    public FrogKingSpawnEggItem(Settings settings) {
        super(settings);
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity user = context.getPlayer();
        World world = context.getWorld();
        assert user != null;
        ItemStack itemStack = user.getStackInHand(user.getActiveHand());
        world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.ENTITY_EGG_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        user.getItemCooldownManager().set(this, 20);
        if (!world.isClient) {
            FrogKingEntity frogKingEntity = new FrogKingEntity(ModEntities.FROG_KING, world);
            frogKingEntity.refreshPositionAndAngles(context.getBlockPos().getX(),
                    context.getBlockPos().getY() + 1, context.getBlockPos().getZ(), 0, 0);
            world.spawnEntity(frogKingEntity);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        return super.useOnBlock(context);
    }
}
