package net.vukrosic.custommobswordsmod.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Random;

public class FirePearlEntity extends EnderPearlEntity {

    // define thrower
    public PlayerEntity thrower;
    public FirePearlEntity(World world, LivingEntity owner) {
        super(world, owner);
    }



    // on hit create explosion

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        // set block above to lava
        this.world.setBlockState(blockHitResult.getBlockPos(), net.minecraft.block.Blocks.LAVA.getDefaultState());

    }

    // prevent particles from spawning



    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        // set block to lava
        this.world.setBlockState(new BlockPos(entityHitResult.getPos()), net.minecraft.block.Blocks.LAVA.getDefaultState());
    }




}
