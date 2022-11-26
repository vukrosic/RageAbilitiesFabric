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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.vukrosic.custommobswordsmod.entity.custom.fireenderman.CombustometerManager;

import java.util.Random;

public class FirePearlEntity extends EnderPearlEntity {
    public PlayerEntity thrower;
    public FirePearlEntity(World world, LivingEntity owner) {
        super(world, owner);
    }



    // on hit create explosion

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 2, Explosion.DestructionType.BREAK);
        super.onBlockHit(blockHitResult);
        explode();

    }


    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 2, Explosion.DestructionType.BREAK);
        super.onEntityHit(entityHitResult);
        explode();
    }

    void explode(){
        ServerWorld serverWorld = (ServerWorld) this.world;
        for (int i = 0; i < 50; i++) {
            Random rand = new Random();
            double x = this.getX() + (rand.nextDouble() - 0.5) * 2;
            double y = this.getY() + (rand.nextDouble() - 0.5) * 2;
            double z = this.getZ() + (rand.nextDouble() - 0.5) * 2;
            serverWorld.spawnParticles(ParticleTypes.WITCH, x, y + 1, z, 1, 0, 0, 0, 1);
            serverWorld.spawnParticles(ParticleTypes.EXPLOSION_EMITTER, x, y + 1, z, 1, 0, 0, 0, 1);
        }

        /*((PlayerEntityExt) thrower).setCombusometerEffect(true);
        ((PlayerEntityExt) thrower).addCombustomenter();*/
        if(!CombustometerManager.players.contains(thrower.getUuid())){
            CombustometerManager.players.add(thrower.getUuid());
        }
        /*
        if(((PlayerEntityExt)thrower).hasCombusometerEffect() == false){
            ((PlayerEntityExt)thrower).setCombusometerEffect(true);
        }*/

        //thrower.kill();

    }
}
