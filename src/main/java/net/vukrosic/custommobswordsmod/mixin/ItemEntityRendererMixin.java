package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.item.custom.ItemEntityMixinExt;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public abstract class ItemEntityRendererMixin extends EntityRenderer<ItemEntity> {
    public boolean isBouncing = false;
    protected ItemEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }
    // inject into render
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(ItemEntity itemEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        // get position of itemEntity
/*
        if(SetHunterCommand.pray != null) {
            BlockPos pos = SetHunterCommand.pray.getBlockPos();
            // if itemEntity position is above pos and distance to pos is less than 2
            if (itemEntity.getBlockPos().getY() > pos.getY() && itemEntity.getBlockPos().getSquaredDistance(pos) < 36) {
                // if itemEntity is a fire sword

                float scale = 3.74f;
                matrixStack.scale(scale, scale, scale);
            }
        }*/
        // if velocity of itemEntity is more than 0.2 also set scale
        if(itemEntity.getVelocity().length() > 0.2){
            // if itemEntity is not grass or stick
            if(itemEntity.getStack().getItem() != Items.STICK && itemEntity.getStack().getItem() != Items.TALL_GRASS &&
            itemEntity.getStack().getItem() != Items.GRASS){
                float scale = 3.74f;
                matrixStack.scale(scale, scale, scale);
            }
            /*
            float scale = 3.74f;
            matrixStack.scale(scale, scale, scale);*/
        }

    }
        /*
        if(SetHunterCommand.pray != null){
            // if distance to pray is less than 5, float scale = 3.74f;
            if (itemEntity.distanceTo(SetHunterCommand.pray) < 5) {
                float scale = 3.74f;
                matrixStack.scale(scale, scale, scale);
            }
        }*/
    /*
        MinecraftClient.getInstance().player.sendMessage(Text.of("++++++++++++++++++++++++++++++++ = " + PlayerAbilities.blockEntitiesScaled), false);
        if(PlayerAbilities.bigBlocksTest){
            //MinecraftClient.getInstance().player.sendMessage(Text.of("++++++++++++++++++++++++++++++++ = " + PlayerAbilities.blockEntitiesScaled), false);
            float scale = 3.74f;
            matrixStack.scale(scale, scale, scale);
        }
    }*/
}
