package net.vukrosic.custommobswordsmod.entity.custom;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.vukrosic.custommobswordsmod.entity.custom.chunken.SpittingChickenEntity;
import org.jetbrains.annotations.Nullable;

public class NateDimSpiderEntity extends SpiderEntity {
    int maxSpittingTimer = 40;
    int spittingTimer = maxSpittingTimer;
    boolean spitting = false;
    PlayerEntity spittingTarget = null;
    public NateDimSpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
    }


    public static DefaultAttributeContainer.Builder setAttributes(){

        return SpiderEntity.createSpiderAttributes().
                add(EntityAttributes.GENERIC_MAX_HEALTH, 55.0D).
                add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0D);
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if(target instanceof PlayerEntity){
            return;
        }
        super.setTarget(target);
    }

    @Override
    public void tick() {
        // agro all mobs
        if(getTarget() == null){
            setTarget(world.getClosestEntity(PandaEntity.class, TargetPredicate.DEFAULT, this, getX(), getY(), getZ(), getBoundingBox().expand(16.0D, 16.0D, 16.0D)));
        }
        //PandaEntity panda = this.world.getClosestEntity(PandaEntity.class, this.getBoundingBox().expand(10), this);
        if(spitting) {
            spitAt(spittingTarget);
            spittingTimer--;
            if(spittingTimer <= 0) {
                spitting = false;
                spittingTimer = maxSpittingTimer;
            }
        }
        super.tick();
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        // if damage source is fall damage
        if(source == DamageSource.FALL){
            return false;
        }
        if(source.getAttacker() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) source.getAttacker();
            this.spittingTarget = player;
            this.world.getEntitiesByClass(NateDimSpiderEntity.class, this.getBoundingBox().expand(10), (entity) -> {
                return entity != this;
            }).forEach((entity) -> {
                entity.spittingTarget = player;
                entity.spitting = true;
            });
            spitting = true;
        }
        return super.damage(source, amount);
    }

    @Override
    public boolean tryAttack(Entity target) {
        if(Math.random() < 0.15){
            world.setBlockState(target.getBlockPos(), Blocks.COBWEB.getDefaultState());
        }
        return super.tryAttack(target);
    }


    private void spitAt(LivingEntity target) {
        LlamaSpitEntity llamaSpitEntity = new LlamaSpitEntity(this.world, new LlamaEntity(EntityType.LLAMA, this.world));
        Vec3d direction = target.getPos().subtract(this.getPos()).normalize();
        llamaSpitEntity.refreshPositionAndAngles(this.getX(), this.getY() + (double)this.getStandingEyeHeight() * 0.5D, this.getZ(), this.getYaw(), this.getPitch());
        llamaSpitEntity.setVelocity(direction.x, direction.y, direction.z, 1.5F, 10.0F);
        if (!this.isSilent()) {
            this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_LLAMA_SPIT, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        }
        this.world.spawnEntity(llamaSpitEntity);
    }
}
