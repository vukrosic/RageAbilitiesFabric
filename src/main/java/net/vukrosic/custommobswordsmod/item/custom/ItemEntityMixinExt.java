package net.vukrosic.custommobswordsmod.item.custom;

import net.minecraft.entity.player.PlayerEntity;

public interface ItemEntityMixinExt {
    int burntimer = 0;
    PlayerEntity thrownBy = null;
    void setBurnTimer(int burntimer);

    void setThrownBy(PlayerEntity player);
    void setBouncing(boolean bouncing);
    boolean getBouncing();
}
