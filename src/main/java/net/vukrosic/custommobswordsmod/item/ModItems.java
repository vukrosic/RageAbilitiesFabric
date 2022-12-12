package net.vukrosic.custommobswordsmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.vukrosic.custommobswordsmod.CustomMobSwordsMod;
import net.vukrosic.custommobswordsmod.entity.ModEntities;
import net.vukrosic.custommobswordsmod.item.custom.*;


public class ModItems {


    public static final Item FIRE_ITEM = registerItem("fire_item",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item FIRE_PEARL = registerItem("fire_pearl",
            new FirePearlItem(new FabricItemSettings().group(ItemGroup.MISC)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(CustomMobSwordsMod.MOD_ID, name), item);
    }
    public static void registerModItems(){
        CustomMobSwordsMod.LOGGER.info("Registering Mod Items for " + CustomMobSwordsMod.MOD_ID);
    }
}
