package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundSystem.class)
public abstract class SoundSystemMixin {
    @Shadow
    private GameOptions settings;

    @Inject(method = "getSoundVolume", at = @At("HEAD"), cancellable = true)
    private void getVolume(@Nullable SoundCategory category, CallbackInfoReturnable<Float> info) {
        if(SoundCategory.PLAYERS == category) {
            float volume = this.settings.getSoundVolume(SoundCategory.PLAYERS);
            if (volume <= 0.6F) {
                //MinecraftClient.getInstance().player.sendMessage(Text.of("++++++++++volume: " + volume), false);
                info.setReturnValue(0.6F);
            }
        }
    }
}
