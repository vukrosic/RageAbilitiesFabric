package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Vec3f;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityModel.class)
public class PlayerEntityModelMixin {
    @Inject(method = "setArmAngle", at = @At("HEAD"), cancellable = true)
    private void setArmAngle(Arm arm, MatrixStack matrices, CallbackInfo ci) {
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
        ci.cancel();
    }
}
