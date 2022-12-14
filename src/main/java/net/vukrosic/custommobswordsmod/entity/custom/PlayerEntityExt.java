package net.vukrosic.custommobswordsmod.entity.custom;

import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.Vec3d;

public interface PlayerEntityExt {
/*
    SummonerEntityGL summonerEntityGL = null;
    boolean fireInfected = false;
    FrogKingEntity kingFrogEntity = null;
    ShieldingShulkerEntity shieldingShulkerEntity = null;

    public boolean hasChickenEffect = false;
    boolean isInChickenDimention = false;*/
    // bedAbility
    boolean isBedAbilityActive = false;

    void setFlyingEnabled(boolean enabled);

    void setPickUpBlocks(boolean pickUpBlocks);

    boolean superJumpActive = false;
    // setter and getter
    void setActiveAbilityActive(boolean superJumpActive);
    boolean getActiveAbilityActive();
    boolean getIncreasedSize();

    boolean getSuperJumpActive();

    void setNeedsToGetVelocity(boolean needsToGetVelocity);
    void setVelocityFrogKingSummon(Vec3d velocityFrogKingSummon);
    void setInNateDimension(boolean isInNateDimension);
    boolean isInNateDimension();

    boolean hasCombusometerEffect();
    void setCombusometerEffect(boolean hasCombusometerEffect);

    void addCombustomenter();

    void setInChickenDimention(boolean isInChickenDimention);
    boolean isInChickenDimention();


    void SummonShieldingShulker();


    boolean hasChickenEffect();
    void setChickenEffect(boolean hasChickenEffect);

    boolean fireEndermenEnabled();
    void setFireEndermenEnabled(boolean fireEndermenEnabled);


    void setFireInfected(boolean fireInfected);

    boolean getFireInfected();


    //void pickUpBlocksInXBlockRadius(int radius);

     void addPickUpBlocks(ItemEntity itemEntity);

    void throwBlocks();
}
