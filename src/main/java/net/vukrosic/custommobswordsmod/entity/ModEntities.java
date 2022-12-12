package net.vukrosic.custommobswordsmod.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.vukrosic.custommobswordsmod.CustomMobSwordsMod;
import net.vukrosic.custommobswordsmod.entity.custom.*;

public class ModEntities {
    public static final EntityType<LavaBlockEntity> LAVA_BLOCK_ENTITY = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(CustomMobSwordsMod.MOD_ID, "lava_block_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, LavaBlockEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6F, 0.6F))
                    .build());


    private static Item registerEntityItem(EntityType<?> entityType, Item item) {
        Registry.register(Registry.ITEM, new Identifier(CustomMobSwordsMod.MOD_ID, "infinity_block_item"), item);
        return item;
    }


    public static void registerModEntities() {
        CustomMobSwordsMod.LOGGER.info("Registering Mod Entities for " + CustomMobSwordsMod.MOD_ID);
    }
}
