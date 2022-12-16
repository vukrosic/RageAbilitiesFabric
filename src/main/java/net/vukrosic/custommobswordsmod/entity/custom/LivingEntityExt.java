package net.vukrosic.custommobswordsmod.entity.custom;

import net.minecraft.entity.player.PlayerEntity;

public interface LivingEntityExt {
    boolean getBeingThrownByPrey();
    void setBeingThrownByPrey(boolean beingThrownByPrey);
    void setBeingPickedByPlayer(boolean beingPickedByPlayer);
    void getPicker();
    void setPicker(PlayerEntity picker);

}
