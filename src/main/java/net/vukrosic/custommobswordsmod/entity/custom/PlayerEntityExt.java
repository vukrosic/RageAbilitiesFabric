package net.vukrosic.custommobswordsmod.entity.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.vukrosic.custommobswordsmod.entity.custom.frogking.FrogKingEntity;
import net.vukrosic.custommobswordsmod.entity.custom.shieldingshulker.ShieldingShulkerEntity;
import net.vukrosic.custommobswordsmod.entity.custom.summoner.SummonerEntityGL;

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
    // bed ability getter and setter
    public void setBedAbilityActive(boolean isBedAbilityActive);
    public boolean getBedAbilityActive();

    LivingEntity pickedEntity = null;
    // getter and setter
    LivingEntity getPickedEntity();
    void setPickedEntity(LivingEntity pickedEntity);

    void setNeedsToGetVelocity(boolean needsToGetVelocity);
    void setVelocityFrogKingSummon(Vec3d velocityFrogKingSummon);
    void setSummoningFrogKingEntity(FrogKingEntity summoningFrogKingEntity);
    void setInNateDimension(boolean isInNateDimension);
    boolean isInNateDimension();

    boolean hasCombusometerEffect();
    void setCombusometerEffect(boolean hasCombusometerEffect);

    void addCombustomenter();

    void setSummonerEntityGL(SummonerEntityGL summonerEntityGL);
    SummonerEntityGL getSummonerEntityGL();

    void setInChickenDimention(boolean isInChickenDimention);
    boolean isInChickenDimention();

    void setShieldingShulkerEntity(ShieldingShulkerEntity shieldingShulkerEntity);
    ShieldingShulkerEntity getShieldingShulkerEntity();

    void SummonShieldingShulker();

    void setFrogKingEntity(FrogKingEntity frogKingEntity);
    FrogKingEntity getFrogKingEntity();

    boolean hasChickenEffect();
    void setChickenEffect(boolean hasChickenEffect);

    boolean fireEndermenEnabled();
    void setFireEndermenEnabled(boolean fireEndermenEnabled);


    void setFireInfected(boolean fireInfected);

    boolean getFireInfected();
}
