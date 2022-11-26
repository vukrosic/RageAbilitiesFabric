package net.vukrosic.custommobswordsmod;

import net.fabricmc.api.ModInitializer;
import net.vukrosic.custommobswordsmod.block.ModBlocks;
import net.vukrosic.custommobswordsmod.effect.ModEffects;
import net.vukrosic.custommobswordsmod.entity.ModEntities;
import net.vukrosic.custommobswordsmod.item.ModItems;
import net.vukrosic.custommobswordsmod.networking.ModMessages;
import net.vukrosic.custommobswordsmod.particle.ModParticles;
import net.vukrosic.custommobswordsmod.screen.ModScreenHandlers;
import net.vukrosic.custommobswordsmod.util.ModRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

public class CustomMobSwordsMod implements ModInitializer {
	public static final String MOD_ID = "custommobswordsmod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);





	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModEntities.registerModEntities();
		ModMessages.registerC2SPacket();
		ModRegistries.registerModStuffs();
        GeckoLib.initialize();
		ModEffects.registerEffects();
		ModParticles.registerParticles();
		ModScreenHandlers.registerAllScreenHandlers();
	}
}
