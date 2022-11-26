package net.vukrosic.custommobswordsmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.vukrosic.custommobswordsmod.CustomMobSwordsMod;
import net.vukrosic.custommobswordsmod.entity.ModEntities;
import net.vukrosic.custommobswordsmod.item.custom.*;


public class ModItems {

    public static final Item FIRE_ZOMBIE_FLINT_AND_STEEL = registerItem("fire_zombie_flint_and_steel",
            new FireZombieFlintAndSteel(new FabricItemSettings().group(ItemGroup.MISC)));

    /*
    public static final Item ABSORPTION_SHIELD_ITEM2 = registerItem("absorption2_shield_item",
            new AbsorptionShieldItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxDamage(336)));*/

    public static final Item FROG_KING_SPAWN_ITEM = registerItem("frog_king_spawn_item",
            new FrogKingSpawnEggItem(new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item CHUNKEN_SPAWN_ITEM = registerItem("chunken_spawn_item",
            new ChunkenSpawnEggItem(new FabricItemSettings().group(ItemGroup.MISC)));

    /*
    public static final Item FROG_KING_SPAWN_EGG = registerItem("frog_king_spawn_egg",
            new SpawnEggItem(ModEntities.FROG_KING, 0x642e8d, 0x1b7535,
                    new FabricItemSettings().group(ItemGroup.MISC)));*/


   /* public static final Item CHUNKEN_SPAWN_EGG = registerItem("chunken_spawn_egg",
            new SpawnEggItem(ModEntities.CHUNKENGL, 0x946e8d, 0x3b3535,
                    new FabricItemSettings().group(ItemGroup.MISC)));*/
/*
    public static final Item HUNTER_LEW_EGG_ITEM = registerItem("hunter_lew_egg_item",
            new HunterLewEggItem(new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item HUNTER_NATE_EGG_ITEM = registerItem("hunter_nate_egg_item",
            new HunterNateEggItem(new FabricItemSettings().group(ItemGroup.MISC)));*/

    public static final Item HUNTER_GOLD_EGG_ITEM = registerItem("hunter_gold_egg_item",
            new HunterGoldEggItem(new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item HUNTER_FROG_KING_EGG_ITEM = registerItem("hunter_frog_king_egg_item",
            new HunterFrogKingEggItem(new FabricItemSettings().group(ItemGroup.MISC)));


    /*
    public static final Item HUNTER_EGG_ITEM = registerItem("hunter_egg_item",
            new HunterEggItem(new FabricItemSettings().group(ItemGroup.MISC)));*/

    public static final Item FROG_KING_ITEM = registerItem("frog_king_item",
            new FrogKingItem(new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item FIRE_ITEM = registerItem("fire_item",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item FIRE_PEARL = registerItem("fire_pearl",
            new FirePearlItem(new FabricItemSettings().group(ItemGroup.MISC)));


    public static final Item BURNT_APPLE = registerItem("burnt_apple",
            new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(ModFoodComponents.BURNT_APPLE)));

    public static final Item BURNT_BEEF = registerItem("burnt_beef",
            new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(ModFoodComponents.BURNT_BEEF)));

    public static final Item BURNT_BREAD = registerItem("burnt_bread",
            new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(ModFoodComponents.BURNT_BREAD)));

    public static final Item BURNT_CARROT = registerItem("burnt_carrot",
            new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(ModFoodComponents.BURNT_CARROT)));

    public static final Item BURNT_CHICKEN = registerItem("burnt_chicken",
            new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(ModFoodComponents.BURNT_CHICKEN)));

    public static final Item BURNT_COD = registerItem("burnt_cod",
            new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(ModFoodComponents.BURNT_COD)));

    public static final Item BURNT_GOLDEN_APPLE = registerItem("burnt_golden_apple",
            new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(ModFoodComponents.BURNT_GOLDEN_APPLE)));

    public static final Item BURNT_MUTTON = registerItem("burnt_mutton",
            new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(ModFoodComponents.BURNT_MUTTON)));

    public static final Item BURNT_MELON = registerItem("burnt_melon",
            new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(ModFoodComponents.BURNT_MELON)));

    public static final Item BURNT_PORKCHOP = registerItem("burnt_porkchop",
            new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(ModFoodComponents.BURNT_PORKCHOP)));

    public static final Item BURNT_POTATO = registerItem("burnt_potato",
            new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(ModFoodComponents.BURNT_POTATO)));

    public static final Item BURNT_RABBIT = registerItem("burnt_rabbit",
            new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(ModFoodComponents.BURNT_RABBIT)));

    public static final Item BURNT_SALMON = registerItem("burnt_salmon",
            new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(ModFoodComponents.BURNT_SALMON)));

    public static final Item BURNT_STEW = registerItem("burnt_stew",
            new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(ModFoodComponents.BURNT_STEW)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(CustomMobSwordsMod.MOD_ID, name), item);
    }
    public static void registerModItems(){
        CustomMobSwordsMod.LOGGER.info("Registering Mod Items for " + CustomMobSwordsMod.MOD_ID);
    }
}
