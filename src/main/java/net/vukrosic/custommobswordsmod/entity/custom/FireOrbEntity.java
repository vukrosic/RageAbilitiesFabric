package net.vukrosic.custommobswordsmod.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;

public class FireOrbEntity extends EnderPearlEntity {

    // define thrower
    public PlayerEntity thrower;

    public FireOrbEntity(EntityType<? extends EnderPearlEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    public void tick() {
        super.tick();
        // make a circular motion around y axis
        // get current position
        Vec3d pos = this.getPos();
        // get current rotation
        Vec3d rot = this.getRotationVector();
        // calculate new position
        double x = pos.x + rot.x * 0.1;
        double y = pos.y + rot.y * 0.1;
        double z = pos.z + rot.z * 0.1;
        // set new position
        this.setPos(x, y, z);

/*
        if (Math.random() < 0.03) {
            boolean shotHunter = false;
            for (LivingEntity livingEntity : this.world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(40), livingEntity -> livingEntity != this.getOwner())) {
                // if it's a hunter
                if (SetHunterCommand.hunters.contains(livingEntity)) {
                    // get velocity towards hunter
                    Vec3d velocity = livingEntity.getPos().subtract(this.getPos()).normalize().multiply(2);
                    FirePearlEntity firePearlEntity = new FirePearlEntity(this.world, (LivingEntity) this.getOwner());
                    firePearlEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                    // set velocity
                    firePearlEntity.setVelocity(velocity);
                    // spawn entity
                    this.world.spawnEntity(firePearlEntity);
                    // break
                    shotHunter = true;
                    break;
                }
            }
            if (!shotHunter) {
                // get closest entity
                for (LivingEntity livingEntity : this.world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(40), livingEntity -> livingEntity != this.getOwner())) {
                    Vec3d velocity = livingEntity.getPos().subtract(this.getPos()).normalize();
                    FirePearlEntity firePearlEntity = new FirePearlEntity(this.world, (LivingEntity) this.getOwner());
                    firePearlEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                    // get velocity towards hunter
                    Vec3d velocity1 = livingEntity.getPos().subtract(this.getPos()).normalize().multiply(2);
                    // set velocity
                    firePearlEntity.setVelocity(velocity1);
                    // spawn entity
                    this.world.spawnEntity(firePearlEntity);
                    // break
                    break;
                }



                // make it float around the owner
                if (this.getOwner() != null) {
                    Vec3d ownerPos = this.getOwner().getPos();
                    Vec3d thisPos = this.getPos();
                    Vec3d diff = ownerPos.subtract(thisPos);
                    Vec3d diffNormalized = diff.normalize();
                    Vec3d diffNormalizedScaled = diffNormalized.multiply(0.1);
                    this.setVelocity(diffNormalizedScaled);
                }
            }
        }*/
    }
}
