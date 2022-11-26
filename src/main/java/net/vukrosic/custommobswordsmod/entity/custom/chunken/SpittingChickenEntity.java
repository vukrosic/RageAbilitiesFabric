package net.vukrosic.custommobswordsmod.entity.custom.chunken;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpittingChickenEntity extends ChickenEntity {

    int lifeTimer = 150;
    int maxSpittingTimer = 40;
    int spittingTimer = maxSpittingTimer;
    boolean spitting = false;
    public SpittingChickenEntity(EntityType<? extends ChickenEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(source.getAttacker() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) source.getAttacker();
            this.setTarget(player);
            this.world.getEntitiesByClass(SpittingChickenEntity.class, this.getBoundingBox().expand(10), (entity) -> {
                return entity != this;
            }).forEach((entity) -> {
                entity.setTarget(player);
                entity.spitting = true;
            });
            spitting = true;
        }
        return super.damage(source, amount);
    }

    @Override
    public void tick() {
        lifeTimer--;
        if(lifeTimer <= 0) {
            this.kill();
        }
        if(getTarget() != null && spitting) {
            //player.sendMessage(Text.of("YOU ARE TARGETED"), true);
            spitAt(getTarget());
            spittingTimer--;
            if(spittingTimer <= 0) {
                spitting = false;
                spittingTimer = maxSpittingTimer;
            }
        }
        super.tick();
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

    public static DefaultAttributeContainer.Builder setAttributes(){
        return ChickenEntity.createChickenAttributes();
    }

}
