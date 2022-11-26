package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import net.vukrosic.custommobswordsmod.entity.custom.fireenderman.HungerManagerExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin implements HungerManagerExt {
    @Shadow private int foodLevel;


    @Shadow private int prevFoodLevel;
/*
    String ownerName = "";
    public void setOwnerName(String ownerName){
        this.ownerName = ownerName;
        SetHunterCommand.pray.sendMessage(Text.of("HungerManagerMixin: " + ownerName), false);
    }
    public PlayerEntity owner;
    public void setOwner(){
        ServerWorld serverWorld = (ServerWorld) owner.world;
        serverWorld.getPlayers().forEach(playerEntity -> {
            if (playerEntity.getName().getString() == ownerName){
                playerEntity.sendMessage(Text.of("Owner set to " + owner.getName().getString()), false);
                this.owner = playerEntity;
            }
        });

    }
    /*public boolean hasCombusometerEffect = false;

    public boolean hasCombusometerEffect() {
        return hasCombusometerEffect;
    }*/
    /*
    public void setCombusometerEffect(boolean hasCombusometerEffect)
    {
        owner.sendMessage(Text.of("SET COMBUSTOMETER NOW: " + hasCombusometerEffect), false);
        this.hasCombusometerEffect = hasCombusometerEffect;
    }*/


/*
    @Inject(method = "setFoodLevel", at = @At("HEAD"))
    public void setFoodLevel(int foodLevel, CallbackInfo ci) {
        if(foodLevel < this.foodLevel && hasCombusometerEffect) {
            foodLevel = this.foodLevel;
        }
    }*/
/*
    /**
     * @author
     * @reason
     *//*
    @Overwrite
    public void setFoodLevel(int foodLevel) {
        owner.sendMessage(Text.of("on combustion list: " + CombustometerManager.players.contains(owner)), false);

        /*if(foodLevel < this.foodLevel && ((PlayerEntityExt) owner).hasCombusometerEffect()) {
            return;
        }*//*
        this.foodLevel = foodLevel;
    }

    @Inject()*/
}
