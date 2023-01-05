package net.vukrosic.custommobswordsmod.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.item.ModItems;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilityTier3;
import net.vukrosic.custommobswordsmod.util.custom.InGameHudMixinExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper implements InGameHudMixinExt {

    public boolean showPreyHealthbar = false;

    private static float preyHealth = 20;



    @Shadow
    private ItemRenderer itemRenderer;
    @Shadow
    private MinecraftClient client;
    @Shadow private final Random random = Random.create();



    // render hotbar
    @Shadow protected abstract void renderHealthBar(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking);



    @Inject(method = "renderHotbarItem", at = @At("HEAD"))
    private void renderHotbar(int x, int y, float tickDelta, PlayerEntity player, ItemStack stack, int seed, CallbackInfo ci) {
        if (PlayerAbilities.AbilityTier == 3 && player.isOnFire()
                && SetHunterCommand.pray != null && SetHunterCommand.pray.equals(player.getUuid())) {
            this.itemRenderer.renderInGuiWithOverrides(player, ModItems.FIRE_ITEM.getDefaultStack(), x, y, seed);
        }
    }
}
