package net.vukrosic.custommobswordsmod.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;

public class FireOrbEntity extends EnderPearlEntity {

    // define thrower
    public PlayerEntity thrower;
    int positionCounter = 0;
    public FireOrbEntity(World world, LivingEntity owner) {
        super(world, owner);
    }


    @Override
    public void tick() {
        super.tick();
        // set position to 2 above player
        //this.setPos(this.thrower.getX(), this.thrower.getY() + 2, this.thrower.getZ());

        positionCounter+=1;
        if(positionCounter== 360){
            positionCounter = 0;
        }
        //translatePosition();

        // get a random 20%
        if(Math.random() < 0.2){
            setCircularVelocity();
        }
        addVelocityTowardsOrAwayFromPlayer();

        //shootFireball();
    }

    private void shootFireball() {
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
            }
        }
    }

    private void addVelocityTowardsOrAwayFromPlayer() {
        // calcualte distance to palyer
        double distance = this.getPos().distanceTo(this.thrower.getPos());
        // if it's too close
        if(distance > 3){
            // get velocity towards hunter
            Vec3d throwerPosition = this.thrower.getPos();
            // move up by 2
            throwerPosition = throwerPosition.add(0, 2, 0);
            Vec3d velocity = throwerPosition.subtract(this.getPos()).normalize().multiply(0.1);
            // set velocity
            this.addVelocity(velocity.x, velocity.y, velocity.z);
        } else if(distance < 3){
            // get velocity towards hunter
            Vec3d velocity = this.getPos().subtract(this.thrower.getPos()).normalize().multiply(0.1);
            // set velocity
            this.addVelocity(velocity.x, velocity.y, velocity.z);
        }
    }

    private void setCircularVelocity() {
        // get closest distance to player
        // get player to this vector
        Vec3d playerToThis = this.getPos().subtract(this.thrower.getPos());
        // give velocity with a normal vector
        Vec3d normal = playerToThis.crossProduct(new Vec3d(0, 1, 0)).normalize();
        // get velocity
        Vec3d velocity = normal.multiply(0.3);
        // set velocity
        this.setVelocity(velocity);
    }

    private void translatePosition() {
        int radius = 2;
        double x = thrower.getX();
        double z = thrower.getZ();
        double angle = positionCounter * Math.PI / 180;
        double dx = x + radius * Math.cos(angle);
        double dz = z + radius * Math.sin(angle);
        this.setPos(dx, thrower.getY() + 2, dz);
        thrower.sendMessage(Text.of("x: " + dx + " z: " + dz), false);

    }
}
