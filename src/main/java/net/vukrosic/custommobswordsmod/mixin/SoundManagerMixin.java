package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.resource.SinglePreparationResourceReloader;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundManager.class)
public abstract class SoundManagerMixin{
    @Shadow
    private SoundSystem soundSystem;

    @Inject(method = "updateSoundVolume", at = @At("HEAD"), cancellable = true)
    public void updateSoundVolume(SoundCategory category, float volume, CallbackInfo ci) {
        //MinecraftClient.getInstance().player.sendMessage(Text.of("category: " + category + " volume " + volume), false);
        //MinecraftClient.getInstance().player.sendMessage(Text.of("isClient " + MinecraftClient.getInstance().player.world.isClient()), false);
        /*
        SOUNDSYSTEMMIXIN
         */


        if (category == SoundCategory.MASTER && volume <= 0.6F) {
            this.soundSystem.updateSoundVolume(category, 0.6F);
        }
        else if(category == SoundCategory.PLAYERS && volume <= 0.6F) {
            this.soundSystem.updateSoundVolume(category, 0.6F);
        }
        else {
            this.soundSystem.updateSoundVolume(category, volume);
        }
        ci.cancel();
    }
}
