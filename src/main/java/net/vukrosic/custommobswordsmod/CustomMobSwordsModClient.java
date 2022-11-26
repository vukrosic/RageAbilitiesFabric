package net.vukrosic.custommobswordsmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.*;
import net.vukrosic.custommobswordsmod.entity.ModEntities;
import net.vukrosic.custommobswordsmod.entity.client.*;
import net.vukrosic.custommobswordsmod.entity.client.chunken.*;
import net.vukrosic.custommobswordsmod.entity.client.corruptedallay.CorruptedAllayVexEntityRendererGL;
import net.vukrosic.custommobswordsmod.entity.client.fireenderman.FireEndermanEntityRendererGL;
import net.vukrosic.custommobswordsmod.entity.client.frogking.FrogKingEntityRenderer;
import net.vukrosic.custommobswordsmod.entity.client.shieldingshulker.ShieldingShulkerEntityRendererGL;
import net.vukrosic.custommobswordsmod.entity.client.summoner.SummonerEntityRendererGL;
import net.vukrosic.custommobswordsmod.event.KeyInputHandler;
import net.vukrosic.custommobswordsmod.networking.ModMessages;
import net.vukrosic.custommobswordsmod.particle.ModParticles;
import net.vukrosic.custommobswordsmod.particle.custom.FeatherParticle;

public class CustomMobSwordsModClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.FIRE_ZOMBIE, FireZombieEntityRenderer::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.FEATHER_PARTICLE, FeatherParticle.Factory::new);

        ModMessages.registerS2CPacket();
        KeyInputHandler.register();
    }
}
