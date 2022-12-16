package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import net.minecraft.world.explosion.Explosion;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.entity.custom.LivingEntityExt;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import net.vukrosic.custommobswordsmod.particle.ModParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Nameable, EntityLike, CommandOutput, LivingEntityExt {

    boolean beingThrownByPrey = false;
    boolean beingPickedByPlayer = false;
    PlayerEntity picker = null;
    @Override
    public void getPicker(){
        picker = null;
    }
    @Override
    public void setPicker(PlayerEntity picker){
        this.picker = picker;
    }

    public void setBeingPickedByPlayer(boolean beingPickedByPlayer) {
        this.beingPickedByPlayer = beingPickedByPlayer;
    }

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
        if(picker != null){
            // set posiiton to above the picker 2 blocks
            if(((PlayerEntityExt)picker).getIncreasedSize()){
                this.setPos(picker.getX(), picker.getY() + 4, picker.getZ());
            }
            else {
                this.setPos(picker.getX(), picker.getY() + 2, picker.getZ());
            }
        }
        if(beingThrownByPrey){
            // get velocity
            Vec3d velocity = this.getVelocity();
            float magnitude = (float) Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y + velocity.z * velocity.z);
            if(magnitude < 0.1f){
                // spawn a cloud of particles
                for(int i = 0; i < 280; i++){
                    this.world.addParticle(ModParticles.FEATHER_PARTICLE, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
                }
                particles();
                beingThrownByPrey = false;
                world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 4, Explosion.DestructionType.DESTROY);
                this.discard();
            }
            world.getOtherEntities(this, this.getBoundingBox().expand(2), (entity) -> {
                return entity instanceof LivingEntity;
            }).forEach((entity) -> {
                if(entity != this && entity != picker){
                    entity.damage(DamageSource.MAGIC, 4);
                }
            });
            //world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 3, Explosion.DestructionType.BREAK);
        }
        // get velocity
        Vec3d velocity = this.getVelocity();
        float magnitude = (float) Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y + velocity.z * velocity.z);

        /*
        if (beingPickedByPlayer) {
            // get velocity
            if(SetHunterCommand.pray == null){
                sendMessage(Text.of("Please set prey"));
            }
            else {
                Vec3d velocity = this.getVelocity();
                float magnitude = (float) Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y + velocity.z * velocity.z);
                if (magnitude < 0.6f) {
                    beingPickedByPlayer = false;
                    // set velocity from this to above prey
                    Vec3d preyPos = SetHunterCommand.pray.getPos();
                    Vec3d thisPos = this.getPos();
                    Vec3d direction = preyPos.subtract(thisPos);
                    direction = direction.normalize();
                    direction = direction.multiply(0.6);
                    this.setVelocity(direction);
                }
            }
        }*/
    }

    void particles(){
        ServerWorld serverWorld = (ServerWorld) this.world;
        for (int i = 0; i < 50; i++) {
            Random rand = new Random();
            double x = this.getX() + (rand.nextDouble() - 0.5) * 2;
            double y = this.getY() + (rand.nextDouble() - 0.5) * 2;
            double z = this.getZ() + (rand.nextDouble() - 0.5) * 2;
            //serverWorld.spawnParticles(ParticleTypes.DRAGON_BREATH, x, y + 1, z, 1, 0, 0, 0, 1);
            //serverWorld.spawnParticles(ParticleTypes.LAVA, x, y + 3, z, 1, 0, 0, 0, 1);
            serverWorld.spawnParticles(ModParticles.FEATHER_PARTICLE, x, y + 3, z, 1, 0, 0, 0, 1);
        }
        //thrower.kill();
    }


}
