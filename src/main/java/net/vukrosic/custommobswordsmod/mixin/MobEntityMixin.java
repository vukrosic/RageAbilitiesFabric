package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.particle.ModParticles;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {


    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public void setTarget(@Nullable LivingEntity target) {

    }
    @Shadow
    public void setPositionTarget(BlockPos target, int range) {

    }
/*
    @Shadow
    private static final TrackedData<Byte> MOB_FLAGS = null;*/

    @Shadow
    protected MoveControl moveControl;

    //@Shadow public abstract boolean tryAttack(Entity target);



    //inject into tick
    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo info) {
/*
        if(SetHunterCommand.prayUuid != null){
            spawnEnragedParticles();
            // mob moves towards pray
            // get player by uuid
            PlayerEntity player = world.getPlayerByUuid(SetHunterCommand.prayUuid);

            if(player != null)
                moveControl.moveTo(player.getX(), player.getY(), player.getZ(), 1.3D);
        }
            // if distance to prey is less than 2, attack

*/
        if(PlayerAbilities.ActiveAbility){
            if(Math.random() < 0.1) {
                for (int i = 0; i < 14; i++) {
                    //this.world.addParticle(ModParticles.FEATHER_PARTICLE, this.getX() + (Math.random() - 0.5f) * 2, this.getY() + (Math.random() - 0.5f) * 2 + 1, this.getZ() + (Math.random() - 0.5f) * 2, Math.random(), Math.random(), Math.random());
                    this.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (Math.random() - 0.5f) * 2, this.getY() + (Math.random() - 0.5f) * 2 + 1, this.getZ() + (Math.random() - 0.5f) * 2, Math.random(), Math.random(), Math.random());
                }
            }
        }

        if(!SetHunterCommand.hunters.isEmpty()){
            if(Math.random() < 0.1) {
                for (int i = 0; i < 14; i++) {
                    //this.world.addParticle(ModParticles.FEATHER_PARTICLE, this.getX() + (Math.random() - 0.5f) * 2, this.getY() + (Math.random() - 0.5f) * 2 + 1, this.getZ() + (Math.random() - 0.5f) * 2, Math.random(), Math.random(), Math.random());
                    this.world.addParticle(ParticleTypes.ANGRY_VILLAGER, this.getX() + (Math.random() - 0.5f) * 2, this.getY() + (Math.random() - 0.5f) * 2 + 1, this.getZ() + (Math.random() - 0.5f) * 2, Math.random(), Math.random(), Math.random());
                }
            }
        }

        if(PlayerAbilities.AbilityTier == 3){
            if(Math.random() < 0.1) {
                for (int i = 0; i < 14; i++) {
                    //this.world.addParticle(ModParticles.FEATHER_PARTICLE, this.getX() + (Math.random() - 0.5f) * 2, this.getY() + (Math.random() - 0.5f) * 2 + 1, this.getZ() + (Math.random() - 0.5f) * 2, Math.random(), Math.random(), Math.random());
                    this.world.addParticle(ParticleTypes.WITCH, this.getX() + (Math.random() - 0.5f) * 2, this.getY() + (Math.random() - 0.5f) * 2 + 1, this.getZ() + (Math.random() - 0.5f) * 2, Math.random(), Math.random(), Math.random());
                }
            }
        }

        if(PlayerAbilities.ActiveAbility && PlayerAbilities.AbilityTier == 3 && !SetHunterCommand.hunters.isEmpty()) {
            // get the closest hunter to this
            PlayerEntity closestHunter = SetHunterCommand.hunters.get(0);
            for (PlayerEntity hunter : SetHunterCommand.hunters) {
                if (this.distanceTo(hunter) < this.distanceTo(closestHunter)) {
                    closestHunter = hunter;
                }
            }
            // move towards the closest hunter
            moveControl.moveTo(closestHunter.getX(), closestHunter.getY(), closestHunter.getZ(), 1.3D);
            spawnEnragedParticles();
        }
    }



    private void spawnEnragedParticles() {
        if(Math.random() < 0.1) {
            for (int i = 0; i < 14; i++) {
                //this.world.addParticle(ModParticles.FEATHER_PARTICLE, this.getX() + (Math.random() - 0.5f) * 2, this.getY() + (Math.random() - 0.5f) * 2 + 1, this.getZ() + (Math.random() - 0.5f) * 2, Math.random(), Math.random(), Math.random());
                this.world.addParticle(ParticleTypes.ANGRY_VILLAGER, this.getX() + (Math.random() - 0.5f) * 2, this.getY() + (Math.random() - 0.5f) * 2 + 1, this.getZ() + (Math.random() - 0.5f) * 2, Math.random(), Math.random(), Math.random());
            }
        }
    }
}
