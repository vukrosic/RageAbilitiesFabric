package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.item.custom.ItemEntityMixinExt;
import net.vukrosic.custommobswordsmod.util.FireInfectedPlayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements ItemEntityMixinExt {
    @Shadow private int pickupDelay;

    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    int discardTimer = 55;
    public boolean bouncing = true;
    @Override
    public void setBouncing(boolean bouncing) {
        this.bouncing = bouncing;
    }
    @Override
    public boolean getBouncing() {
        return bouncing;
    }

    // inject into getBodyYaw
    @Inject(method = "getBodyYaw", at = @At("HEAD"), cancellable = true)
    public void getBodyYaw(CallbackInfoReturnable<Float> cir){
        // if the item is a fire sword, set the yaw to 0
        if(bouncing){
            // get command manager
            ServerCommandSource commandSource = world.getServer().getCommandSource();
            CommandManager commandManager = world.getServer().getCommandManager();
            if (commandManager != null) {
                commandManager.executeWithPrefix( commandSource, "/scale set 4");
            }
            cir.setReturnValue(0f);
        }
    }
    @Inject (method = "getRotation", at = @At("HEAD"), cancellable = true)
    public void getRotation(CallbackInfoReturnable<Float> cir){
        if(bouncing){
            /*ServerCommandSource commandSource = world.getServer().getCommandSource();
            CommandManager commandManager = world.getServer().getCommandManager();
            if (commandManager != null) {
                commandManager.executeWithPrefix( commandSource, "/scale set 4");
            }*/
            cir.setReturnValue(0f);
        }
    }
    // inject into tick
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(CallbackInfo ci){
        // if the item is a fire sword, set the yaw to 0
        if(bouncing){
            // get velocity
            Vec3d velocity = this.getVelocity();
            if(velocity.y < 0.1){
                discard();
            }
        }
    }

    // prevent the item from being picked up with infinite pickup delay
    @Inject(method = "setPickupDelay", at = @At("HEAD"), cancellable = true)
    public void setPickupDelay(int pickupDelay, CallbackInfo ci){
        if(bouncing){
            this.pickupDelay = 32767;
            ci.cancel();
        }
    }
}

