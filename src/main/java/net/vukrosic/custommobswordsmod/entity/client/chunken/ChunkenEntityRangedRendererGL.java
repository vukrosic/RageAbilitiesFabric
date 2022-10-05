package net.vukrosic.custommobswordsmod.entity.client.chunken;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.vukrosic.custommobswordsmod.CustomMobSwordsMod;
import net.vukrosic.custommobswordsmod.entity.custom.chunken.ChunkenEntityGL;
import net.vukrosic.custommobswordsmod.entity.custom.chunken.ChunkenEntityRangedGL;
import net.vukrosic.custommobswordsmod.entity.custom.chunken.ChunkenPhaseManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ChunkenEntityRangedRendererGL extends GeoEntityRenderer<ChunkenEntityRangedGL> {


    public ChunkenEntityRangedRendererGL(EntityRendererFactory.Context ctx) {
        super(ctx, new ChunkenEntityRangedModelGL());
        this.shadowRadius = .8f;
    }

    @Override
    public Identifier getTextureResource(ChunkenEntityRangedGL instance) {
        return new Identifier(CustomMobSwordsMod.MOD_ID, "textures/entity/chicken_robot_phase_5_gold.png");
    }

    @Override
    public RenderLayer getRenderType(ChunkenEntityRangedGL animatable, float partialTicks,
                                     MatrixStack stack, VertexConsumerProvider renderTypeBuffer,
                                     VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        int scale = 5;
        stack.scale(scale, scale, scale);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }


}