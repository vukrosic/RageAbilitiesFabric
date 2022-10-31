package net.vukrosic.custommobswordsmod.entity.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.WardenEntityRenderer;
import net.minecraft.client.render.entity.feature.WardenFeatureRenderer;
import net.minecraft.client.render.entity.model.WardenEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.vukrosic.custommobswordsmod.CustomMobSwordsMod;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.effect.ModEffects;
import net.vukrosic.custommobswordsmod.entity.custom.BlinkingWardenEntity;
import net.vukrosic.custommobswordsmod.entity.custom.chunken.ChunkenEntityGL;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BlinkingWardenEntityRenderer extends /*GeoEntityRenderer<BlinkingWardenEntity>*/ WardenEntityRenderer {
    public BlinkingWardenEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
/*
    private static final Identifier TEXTURE = new Identifier("textures/entity/warden/warden.png");
    private static final Identifier BIOLUMINESCENT_LAYER_TEXTURE = new Identifier("textures/entity/warden/warden_bioluminescent_layer.png");
    private static final Identifier HEART_TEXTURE = new Identifier("textures/entity/warden/warden_heart.png");
    private static final Identifier PULSATING_SPOTS_1_TEXTURE = new Identifier("textures/entity/warden/warden_pulsating_spots_1.png");
    private static final Identifier PULSATING_SPOTS_2_TEXTURE = new Identifier("textures/entity/warden/warden_pulsating_spots_2.png");*/
/*
    public BlinkingWardenEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BlinkingWardenEntityModel());
        this.addFeature(new WardenFeatureRenderer(this, BIOLUMINESCENT_LAYER_TEXTURE, (warden, tickDelta, animationProgress) -> {
            return 1.0F;
        }, WardenEntityModel::getHeadAndLimbs));
        this.addFeature(new WardenFeatureRenderer(this, PULSATING_SPOTS_1_TEXTURE, (warden, tickDelta, animationProgress) -> {
            return Math.max(0.0F, MathHelper.cos(animationProgress * 0.045F) * 0.25F);
        }, WardenEntityModel::getBodyHeadAndLimbs));
        this.addFeature(new WardenFeatureRenderer(this, PULSATING_SPOTS_2_TEXTURE, (warden, tickDelta, animationProgress) -> {
            return Math.max(0.0F, MathHelper.cos(animationProgress * 0.045F + 3.1415927F) * 0.25F);
        }, WardenEntityModel::getBodyHeadAndLimbs));
        this.addFeature(new WardenFeatureRenderer(this, TEXTURE, (warden, tickDelta, animationProgress) -> {
            return warden.getTendrilPitch(tickDelta);
        }, WardenEntityModel::getTendrils));
        this.addFeature(new WardenFeatureRenderer(this, HEART_TEXTURE, (warden, tickDelta, animationProgress) -> {
            return warden.getHeartPitch(tickDelta);
        }, WardenEntityModel::getBody));
    }*/


/*
    @Override
    public Identifier getTexture(WardenEntity wardenEntity) {
        if(MinecraftClient.getInstance().player.hasStatusEffect(StatusEffects.INVISIBILITY)) {
            MinecraftClient.getInstance().player.sendMessage(Text.of("Rendering warden"), false);
            return super.getTexture(wardenEntity);
        }
        else {
            MinecraftClient.getInstance().player.sendMessage(Text.of("Not rendering warden"), false);
            return new Identifier(CustomMobSwordsMod.MOD_ID,"textures/empty.png");
        }
    }*/
}
