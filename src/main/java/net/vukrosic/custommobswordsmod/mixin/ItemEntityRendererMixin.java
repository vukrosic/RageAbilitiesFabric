package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.vukrosic.custommobswordsmod.item.custom.ItemEntityMixinExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public abstract class ItemEntityRendererMixin extends EntityRenderer<ItemEntity> {
    protected ItemEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }
    // inject into render
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(ItemEntity itemEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if(((ItemEntityMixinExt)itemEntity).getBouncing()){
            float scale = 3.74f;
            matrixStack.scale(scale, scale, scale);
        }

    }
}
