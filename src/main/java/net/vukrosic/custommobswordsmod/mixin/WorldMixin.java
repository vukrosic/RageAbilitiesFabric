package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import net.vukrosic.custommobswordsmod.effect.ModEffects;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin implements WorldAccess, AutoCloseable {


    @Shadow public abstract void playSound(double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch, boolean useDistance);




    @Inject(method = "breakBlock", at = @At("HEAD"), cancellable = true)
    public void breakBlock(BlockPos pos, boolean drop, @Nullable Entity breakingEntity, int maxUpdateDepth, CallbackInfoReturnable info) {
        /*
        if(breakingEntity instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) breakingEntity;

            if(!player.world.isClient){
                player.world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_CHICKEN_AMBIENT, SoundCategory.PLAYERS, 10.0F, 1.0F);
            }else
                MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.ENTITY_CHICKEN_AMBIENT, 1.0F));
            playSound(player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_CHICKEN_AMBIENT, SoundCategory.PLAYERS, 10.0F, 1.0F, false);
            player.world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_CHICKEN_AMBIENT, SoundCategory.PLAYERS, 5.0F, 1.0F + (player.world.random.nextFloat() - player.world.random.nextFloat()) * 0.2F);


            if(((PlayerEntityExt)player).hasChickenEffect()){
                // play chicken bawk sound
                if(!player.world.isClient){
                    player.world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_CHICKEN_AMBIENT, SoundCategory.PLAYERS, 10.0F, 1.0F);
                }else
                    MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.ENTITY_CHICKEN_AMBIENT, 1.0F));
                playSound(player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_CHICKEN_AMBIENT, SoundCategory.PLAYERS, 10.0F, 1.0F, false);
                player.world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_CHICKEN_AMBIENT, SoundCategory.PLAYERS, 5.0F, 1.0F + (player.world.random.nextFloat() - player.world.random.nextFloat()) * 0.2F);

            }
        }*/
        // FIRFST CHECK CALLBACKINFORETURNBALE THEN VOID INTO BOOLEAN EZ GAM EXZ LIFE
        /*System.out.println("BREAKING BLOCK");
        System.out.println("breakingEntity != null: " + (breakingEntity != null));
        System.out.println("breakingEntity instanceof PlayerEntity: " + (breakingEntity instanceof PlayerEntity));*/


        /*
        if(breakingEntity != null && breakingEntity instanceof PlayerEntity){
            ((PlayerEntity)breakingEntity).sendMessage(Text.of("IS PLAYER"), false);

            if(((PlayerEntity)breakingEntity).getStatusEffect(ModEffects.CHICKEN) != null){
                ((PlayerEntity)breakingEntity).sendMessage(Text.of("Playing Chicken Sound"), false);
                ((PlayerEntity)breakingEntity).playSound(SoundEvents.ENTITY_CHICKEN_HURT, 10.0f, 1.0f);
                // spawn chicken particles
                ((PlayerEntity)breakingEntity).sendMessage(Text.of("Spawning Chicken Particles"), false);
                // add chicken particles
                MinecraftClient.getInstance().particleManager.addParticle(new BlockStateParticleEffect((ParticleType)ParticleTypes.WITCH, Blocks.DIRT.getDefaultState()), (double)breakingEntity.getX() + 0.5, (double)breakingEntity.getY() + 0.5, (double)breakingEntity.getZ() + 0.5, 0.0, 0.0, 0.0);;

                //((World)(Object)this).spawnParticles(((PlayerEntity)breakingEntity), GameEvent.BLOCK_DESTROY, pos, 10);

                MinecraftClient.getInstance().getSoundManager().play(new PositionedSoundInstance(SoundEvents.ENTITY_CHICKEN_HURT, SoundCategory.PLAYERS, 10.0f, 1.0f, getRandom(), new BlockPos(breakingEntity.getPos()) ));
            }
        }*/
    }
}
