package net.vukrosic.custommobswordsmod.entity.custom.chunken;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.vukrosic.custommobswordsmod.mixin.ClientWorldMixin;
import software.bernie.example.ClientListener;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


import java.util.List;

public class ChunkenRocketEntity extends PersistentProjectileEntity implements IAnimatable {

    protected int timeInAir;
    protected boolean inAir;
    private int ticksInAir;
    private LivingEntity shooter;

    private AnimationFactory factory = new AnimationFactory(this);


    public ChunkenRocketEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.pickupType = PickupPermission.DISALLOWED;
        CowEntity cowEntity = new CowEntity(EntityType.COW, world);
        cowEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
        cowEntity.setVelocity(this.getVelocity());
    }

    

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<ChunkenRocketEntity>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return ClientListener.EntityPacket.createPacket(this);
    }

    @Override
    protected void age() {
        ++this.ticksInAir;
        if (this.ticksInAir >= 40) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    protected void onHit(LivingEntity living) {
        super.onHit(living);
        if (!(living instanceof PlayerEntity)) {
            living.setVelocity(0, 0, 0);
            living.timeUntilRegen = 0;
        }
    }

    @Override
    public void setVelocity(double x, double y, double z, float speed, float divergence) {
        super.setVelocity(x, y, z, speed, divergence);
        this.ticksInAir = 0;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putShort("life", (short) this.ticksInAir);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.ticksInAir = tag.getShort("life");
    }

    @Override
    public void tick() {
        super.tick();
        boolean bl = this.isNoClip();
        Vec3d vec3d = this.getVelocity();
        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            double f = vec3d.horizontalLength();
            this.setYaw((float) (MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875D));
            this.setPitch((float) (MathHelper.atan2(vec3d.y, (double) f) * 57.2957763671875D));
            this.prevYaw = this.getYaw();
            this.prevPitch = this.getPitch();
        }
        if (this.age >= 100) {
            this.remove(RemovalReason.DISCARDED);
        }
        if (this.inAir && !bl) {
            this.age();
            ++this.timeInAir;
        } else {
            this.timeInAir = 0;
            Vec3d vec3d3 = this.getPos();
            Vec3d vector3d3 = vec3d3.add(vec3d);
            HitResult hitResult = this.world.raycast(new RaycastContext(vec3d3, vector3d3,
                    RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
            if (((HitResult) hitResult).getType() != HitResult.Type.MISS) {
                vector3d3 = ((HitResult) hitResult).getPos();
            }
            while (!this.isRemoved()) {
                EntityHitResult entityHitResult = this.getEntityCollision(vec3d3, vector3d3);
                if (entityHitResult != null) {
                    hitResult = entityHitResult;
                }
                if (hitResult != null && ((HitResult) hitResult).getType() == HitResult.Type.ENTITY) {
                    Entity entity = ((EntityHitResult) hitResult).getEntity();
                    Entity entity2 = this.getOwner();
                    if (entity instanceof PlayerEntity && entity2 instanceof PlayerEntity
                            && !((PlayerEntity) entity2).shouldDamagePlayer((PlayerEntity) entity)) {
                        hitResult = null;
                        entityHitResult = null;
                    }
                }
                if (hitResult != null && !bl) {
                    this.onCollision((HitResult) hitResult);
                    this.velocityDirty = true;
                }
                if (entityHitResult == null || this.getPierceLevel() <= 0) {
                    break;
                }
                hitResult = null;
            }
            vec3d = this.getVelocity();
            double d = vec3d.x;
            double e = vec3d.y;
            double g = vec3d.z;
            double h = this.getX() + d;
            double j = this.getY() + e;
            double k = this.getZ() + g;
            double l = vec3d.horizontalLength();
            if (bl) {
                this.setYaw((float) (MathHelper.atan2(-d, -g) * 57.2957763671875D));
            } else {
                this.setYaw((float) (MathHelper.atan2(d, g) * 57.2957763671875D));
            }
            this.setPitch((float) (MathHelper.atan2(e, (double) l) * 57.2957763671875D));
            this.setPitch(updateRotation(this.prevPitch, this.getPitch()));
            this.setYaw(updateRotation(this.prevYaw, this.getYaw()));
            float m = 0.99F;

            this.setVelocity(vec3d.multiply((double) m));
            if (!this.hasNoGravity() && !bl) {
                Vec3d vec3d5 = this.getVelocity();
                this.setVelocity(vec3d5.x, vec3d5.y - 0.05000000074505806D, vec3d5.z);
            }
            this.updatePosition(h, j, k);
            this.checkBlockCollision();
        }
        
        
        spawnParticles();
    }
    
    
    public void spawnParticles(){
        if (!this.world.isClient) {
            for (int i = 0; i < 15; ++i) {
                double d = this.random.nextGaussian() * 0.02D;
                double e = this.random.nextGaussian() * 0.02D;
                double f = this.random.nextGaussian() * 0.02D;
                double g = 10.0D;

                //((ServerWorld) this.world).spawnParticles(ParticleTypes.WITCH, getX(), getY(), getZ(), 1, 0, 0, 0, 1);
                ((ServerWorld) this.world).spawnParticles(ParticleTypes.POOF, getX(), getY(), getZ(), 1, 0, 0, 0, 1);
                if( i % 2 == 0){
                    ((ServerWorld) this.world).spawnParticles(ParticleTypes.SMOKE, getX(), getY(), getZ(), 1, 0, 0, 0, 1);
                    ((ServerWorld) this.world).spawnParticles(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 1, 0, 0, 0, 1);
                }
                //this.world.addParticle(ParticleTypes.POOF, this.getX() + (double) (this.random.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth() - d * 10.0D, this.getY() + (double) (this.random.nextFloat() * this.getHeight()) - e * 10.0D, this.getZ() + (double) (this.random.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth() - f * 10.0D, d, e, f);
            }
        }
    }

    public void initFromStack(ItemStack stack) {
        if (stack.getItem() == Items.TNT) {
        }
    }

    @Override
    public boolean hasNoGravity() {
        if (this.isSubmergedInWater()) {
            return false;
        } else {
            return true;
        }
    }

    public SoundEvent hitSound = this.getHitSound();

    @Override
    public void setSound(SoundEvent soundIn) {
        this.hitSound = soundIn;
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.ENTITY_GENERIC_EXPLODE;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.world.isClient) {
            this.doDamage();
            this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 1.0F, false,
                    Explosion.DestructionType.BREAK);
            this.remove(RemovalReason.DISCARDED);
        }
        this.setSound(SoundEvents.ENTITY_GENERIC_EXPLODE);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (!this.world.isClient) {
            this.doDamage();
            this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 1.0F, false,
                    Explosion.DestructionType.BREAK);
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public ItemStack asItemStack() {
        return new ItemStack(Items.TNT);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldRender(double distance) {
        return true;
    }



    public void doDamage() {
        float q = 4.0F;
        int k = MathHelper.floor(this.getX() - (double) q - 1.0D);
        int l = MathHelper.floor(this.getX() + (double) q + 1.0D);
        int t = MathHelper.floor(this.getY() - (double) q - 1.0D);
        int u = MathHelper.floor(this.getY() + (double) q + 1.0D);
        int v = MathHelper.floor(this.getZ() - (double) q - 1.0D);
        int w = MathHelper.floor(this.getZ() + (double) q + 1.0D);
        List<Entity> list = this.world.getOtherEntities(this,
                new Box((double) k, (double) t, (double) v, (double) l, (double) u, (double) w));
        Vec3d vec3d = new Vec3d(this.getX(), this.getY(), this.getZ());
        for (int x = 0; x < list.size(); ++x) {
            Entity entity = (Entity) list.get(x);
            double y = (double) (MathHelper.sqrt((float) entity.squaredDistanceTo(vec3d)) / q);
            if (y <= 1.0D) {
                if (entity instanceof LivingEntity) {
                    if (this.getOwner() instanceof LivingEntity) {
                        entity.damage(DamageSource.mob((LivingEntity) this.getOwner()), 8);
                    }
                    this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 0.0F,
                            Explosion.DestructionType.NONE);
                }
            }
        }
    }



}
