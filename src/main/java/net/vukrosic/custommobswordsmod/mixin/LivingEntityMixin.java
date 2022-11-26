package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import net.minecraft.world.explosion.Explosion;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.entity.custom.LivingEntityExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Nameable, EntityLike, CommandOutput, LivingEntityExt {

    boolean beingThrownByPrey = false;


    public boolean getBeingThrownByPrey() {
        return beingThrownByPrey;
    }
    public void setBeingThrownByPrey(boolean beingThrownByPrey) {
        this.beingThrownByPrey = beingThrownByPrey;
    }
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    protected abstract float getJumpVelocity();

    @Shadow
    public abstract double getJumpBoostVelocityModifier();



    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(CallbackInfo ci) {
        if(beingThrownByPrey){
            // get velocity
            Vec3d velocity = this.getVelocity();
            float magnitude = (float) Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y + velocity.z * velocity.z);
            if(magnitude < 0.3f){
                beingThrownByPrey = false;
                // spawn a cloud of particles
                for(int i = 0; i < 10; i++){
                    this.world.addParticle(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
                }
                particles();
            }
            world.getOtherEntities(this, this.getBoundingBox().expand(2), (entity) -> {
                return entity instanceof LivingEntity;
            }).forEach((entity) -> {
                entity.damage(DamageSource.MAGIC, 4);
            });
            world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 3, Explosion.DestructionType.BREAK);
        }
    }

    void particles(){
        ServerWorld serverWorld = (ServerWorld) this.world;
        for (int i = 0; i < 50; i++) {
            Random rand = new Random();
            double x = this.getX() + (rand.nextDouble() - 0.5) * 2;
            double y = this.getY() + (rand.nextDouble() - 0.5) * 2;
            double z = this.getZ() + (rand.nextDouble() - 0.5) * 2;
            serverWorld.spawnParticles(ParticleTypes.DRAGON_BREATH, x, y + 1, z, 1, 0, 0, 0, 1);
            serverWorld.spawnParticles(ParticleTypes.LAVA, x, y + 3, z, 1, 0, 0, 0, 1);
        }
        //thrower.kill();
    }


}
