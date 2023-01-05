package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.entity.custom.FireOrbEntity;
import net.vukrosic.custommobswordsmod.entity.custom.FirePearlEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderPearlEntity.class)
public abstract class EnderPearlEntityMixin extends ThrownItemEntity {

    public EnderPearlEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    // inject into onCollision method
    @Inject(method = "onCollision", at = @At("HEAD"), cancellable = true)
    public void onCollision(CallbackInfo ci) {
        if((EnderPearlEntity)(Object)this instanceof FirePearlEntity) {
            this.world.setBlockState(this.getBlockPos(), net.minecraft.block.Blocks.LAVA.getDefaultState());
            ci.cancel();
        }
        if((EnderPearlEntity)(Object)this instanceof FireOrbEntity) {
            ci.cancel();
        }
    }

    // inject into onBlockHit method
    @Inject(method = "onEntityHit", at = @At("HEAD"), cancellable = true)
    public void onBlockHit(CallbackInfo ci) {
        if((EnderPearlEntity)(Object)this instanceof FirePearlEntity) {
            this.world.setBlockState(this.getBlockPos(), net.minecraft.block.Blocks.LAVA.getDefaultState());
            ci.cancel();
        }
        if((EnderPearlEntity)(Object)this instanceof FireOrbEntity) {
            this.world.setBlockState(this.getBlockPos(), Blocks.OBSIDIAN.getDefaultState());
            ci.cancel();
        }
    }

}
