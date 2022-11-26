package net.vukrosic.custommobswordsmod.effect.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;
import net.vukrosic.custommobswordsmod.entity.ModEntities;
import net.vukrosic.custommobswordsmod.entity.custom.BlinkingWardenEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class CarbonPoisoningEffect extends StatusEffect {
    public CarbonPoisoningEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    static ArrayList<BlinkingWardenEntity> blinkingWardenEntities = new ArrayList<>();
    int wardenTimer = 10;



    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        applyWardenEffect(entity);
        super.onApplied(entity, attributes, amplifier);
    }

    void applyWardenEffect(LivingEntity entity) {

        //wardenTimer = duration * 20;

        //entity.playSound(SoundEvents.ENTITY_WARDEN_EMERGE, 10, 1);

        if(entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            int duration = 50;
            wardenTimer = 9;
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, duration, 0, false, true, false));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, duration, 0, false, true, false));
            // on player status effect end, remove warden


            for (int i = 0; i < 16; i++) {
                /*
                int x = (int) (Math.random() * 15) + 15;
                int z = (int) (Math.random() * 15) + 15;

                if(Math.random() <= 0.25f){
                    teleportTo(player, player.getX() - x, player.getY(), player.getZ() + z);
                } else if (Math.random() <= 0.5f) {
                    teleportTo(player, player.getX() + x, player.getY(), player.getZ() + z);
                } else if (Math.random() <= 0.75f) {
                    teleportTo(player, player.getX() - x, player.getY(), player.getZ() - z);
                } else {
                    teleportTo(player, player.getX() + x, player.getY(), player.getZ() - z);
                }*/


                int x = (int) (Math.random() * 20) - 10;
                int z = (int) (Math.random() * 20) - 10;
                //spawnAt(player, player.getX() + x, player.getY(), player.getZ() + z);
                if((x > 4 || x < -4) && (z > 4 || z < -4)) {
                    teleportTo(player, player.getX() + x, player.getY(), player.getZ() + z);
                }


            }
        }
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        if(!entity.world.isClient()) {
            if(entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                if (player.hasStatusEffect(StatusEffects.DARKNESS)) {
                    wardenTimer--;
                    if (wardenTimer <= 5) {
                        for (BlinkingWardenEntity warden : blinkingWardenEntities) {
                            // discard warden entity
                            warden.discard();
                        }
                        blinkingWardenEntities.clear();
                    }
                }
                if (entity.world.random.nextDouble() < 0.025 && wardenTimer <= 5) {
                    applyWardenEffect(entity);
                }
            }
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }




    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if(!entity.world.isClient() && blinkingWardenEntities.size() > 0) {
            wardenTimer = 0;
            for (BlinkingWardenEntity warden : blinkingWardenEntities) {
                // discard warden entity
                warden.discard();
            }
            blinkingWardenEntities.clear();
        }

        super.onRemoved(entity, attributes, amplifier);
    }



    private boolean spawnAt(PlayerEntity player, double x, double y, double z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);
        while (mutable.getY() > player.world.getBottomY() && !player.world.getBlockState(mutable).getMaterial().blocksMovement()) {
            mutable.move(Direction.DOWN);
        }
        BlockState blockState = player.world.getBlockState(mutable);
        boolean bl = blockState.getMaterial().blocksMovement();
        boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
        if (!bl || bl2) {
            return false;
        }
        Vec3d vec3d = player.getPos();
        boolean bl3 = this.spawnWardenAt(x, y, z, true, player);
        if (bl3) {
            //player.world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(this));
            if (!player.isSilent()) {
                //player.playSound(SoundEvents.ENTITY_WARDEN_HEARTBEAT , 10.0f, 1.0f);
            }
        }
        return bl3;
    }

    public boolean spawnWardenAt(double x, double y, double z, boolean particleEffects, PlayerEntity player) {
        return true;
    }


    private boolean teleportTo(PlayerEntity player, double x, double y, double z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);

        while(mutable.getY() > player.world.getBottomY() && !player.world.getBlockState(mutable).getMaterial().blocksMovement()) {
            mutable.move(Direction.DOWN);
        }
        // get y coordinate of top of the ground
        // player.world.getBlockState(mutable).getCollisionShape(player.world, mutable).getBoundingBox().getMax(Direction.Axis.Y);
        // the previous line will

        BlockState blockState = player.world.getBlockState(mutable);
        boolean bl = blockState.getMaterial().blocksMovement();
        boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
        if (bl && !bl2) {
            Vec3d vec3d = player.getPos();
            // get position of top of the ground
            double d = (double)mutable.getY() + 0.5D;
            boolean bl3 = this.spawnWardenAt(x, d, z, true, player);
            if (bl3) {
                player.world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(player));
                if (!player.isSilent()) {
                    //player.world.playSound((PlayerEntity)null, player.prevX, player.prevY, player.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, player.getSoundCategory(), 1.0F, 1.0F);
                    //player.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
                }
            }

            return bl3;
        } else {
            return false;
        }
    }



}
