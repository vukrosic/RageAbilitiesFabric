package net.vukrosic.custommobswordsmod.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import net.vukrosic.custommobswordsmod.effect.ModEffects;

public class ModFoodComponents {

    static StatusEffectInstance statusEffectInstance = new StatusEffectInstance(StatusEffects.SLOWNESS, 1200, 0);


    public static final FoodComponent BURNT_BEEF =  (new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).meat().build();
    public static final FoodComponent BURNT_APPLE = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).meat().build();
    public static final FoodComponent BURNT_PORKCHOP = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).meat().build();
    public static final FoodComponent BURNT_COD = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).meat().build();
    public static final FoodComponent BURNT_BREAD = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).meat().build();
    public static final FoodComponent BURNT_CARROT = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).meat().build();
    public static final FoodComponent BURNT_GOLDEN_APPLE = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).meat().build();
    public static final FoodComponent BURNT_POTATO = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).meat().build();
    public static final FoodComponent BURNT_SALMON = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).meat().build();
    public static final FoodComponent BURNT_RABBIT = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).meat().build();
    public static final FoodComponent BURNT_MUTTON = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).meat().build();
    public static final FoodComponent BURNT_STEW = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).meat().build();
    public static final FoodComponent BURNT_CHICKEN = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).meat().build();
    public static final FoodComponent BURNT_MELON = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).meat().build();

}

