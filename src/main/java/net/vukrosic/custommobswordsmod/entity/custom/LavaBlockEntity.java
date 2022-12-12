package net.vukrosic.custommobswordsmod.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class LavaBlockEntity extends EnderPearlEntity {
    public LavaBlockEntity(EntityType<? extends EnderPearlEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        // spawn lava 1 block above
        this.world.setBlockState(blockHitResult.getBlockPos().up(), net.minecraft.block.Blocks.LAVA.getDefaultState());
    }
}
