package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import net.vukrosic.custommobswordsmod.util.ThrowingAnimationManager;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> extends AnimalModel<T> implements ModelWithArms, ModelWithHead {
    @Shadow
    private ModelPart leftArm;
    @Shadow
    private ModelPart rightArm;
    float throwAnimationAngleDelta = 0.3f;
    float throwAnimationAngle = -1.5F;
    boolean throwing = false;
    float holdingAnimationAngle = -2.8f;



    @Inject(method = "positionRightArm", at = @At("HEAD"), cancellable = true)
    private void positionRightArm(T entity, CallbackInfo ci) {
        // make arm point upwards
        if (entity instanceof PlayerEntity player) {
            if (PlayerAbilities.pickedEntities.size() > 0) {
                this.rightArm.pitch = holdingAnimationAngle;
                this.leftArm.pitch = throwAnimationAngle;
                if (ThrowingAnimationManager.throwingPlayer != null &&
                        ThrowingAnimationManager.throwingPlayer.getUuid().equals(player.getUuid())) {
                    this.rightArm.pitch += throwAnimationAngleDelta;
                    if (throwAnimationAngleDelta > throwAnimationAngle) {
                        ThrowingAnimationManager.throwingPlayer = null;
                        // get uuid of this entity
                    }
                }
                ci.cancel();
            }
        }
    }
}
