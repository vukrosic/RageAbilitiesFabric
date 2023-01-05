package net.vukrosic.custommobswordsmod.mixin;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.item.custom.ItemEntityMixinExt;
import net.vukrosic.custommobswordsmod.networking.ModMessages;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements ItemEntityMixinExt {
    @Shadow private int pickupDelay;

    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    int distanceToPrey = 7;
    public boolean isBeingThrown = false;
    public boolean isPickedUp = false;
    int discardTimer = 55;
    public boolean bouncing = false;
    @Override
    public void setBouncing(boolean bouncing) {
        this.bouncing = bouncing;
    }
    @Override
    public boolean getBouncing() {
        return bouncing;
    }
    // setter for isPickedUp
    @Override
    public void setPickedUp(boolean pickedUp) {
        this.isPickedUp = pickedUp;
    }

    // override setBeingThrown
    @Override
    public void setBeingThrown(boolean beingThrown) {
        this.isBeingThrown = beingThrown;
    }

    // inject into getBodyYaw
    @Inject(method = "getBodyYaw", at = @At("HEAD"), cancellable = true)
    public void getBodyYaw(CallbackInfoReturnable<Float> cir){
        // if the item is a fire sword, set the yaw to 0
        if(PlayerAbilities.ActiveAbility){
            /*
            // get command manager
            ServerCommandSource commandSource = world.getServer().getCommandSource();
            CommandManager commandManager = world.getServer().getCommandManager();
            if (commandManager != null) {
                commandManager.executeWithPrefix( commandSource, "/scale set 4");
            }
            cir.setReturnValue(0f);*/
        }
    }
    @Inject (method = "getRotation", at = @At("HEAD"), cancellable = true)
    public void getRotation(CallbackInfoReturnable<Float> cir){
        /*if(PlayerAbilities.ActiveAbility){
            ServerCommandSource commandSource = world.getServer().getCommandSource();
            CommandManager commandManager = world.getServer().getCommandManager();
            if (commandManager != null) {
                commandManager.executeWithPrefix(commandSource, "/scale set 4");
            }
            cir.setReturnValue(0f);
        }*/
    }

    // inject into tick
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(CallbackInfo ci){
        if(!world.isClient() && PlayerAbilities.ActiveAbility && bouncing){
            // get velocity
            Vec3d velocity = this.getVelocity();
            if(velocity.y < 0.2){
                discard();
            }
        }
    }
    /*
    // turn of pickup
    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    public void onPlayerCollision(PlayerEntity player, CallbackInfo ci){
        if(PlayerAbilities.ActiveAbility  1){
            // if the item is a fire sword, set the yaw to 0
            if(this.getStack().getItem() == PlayerAbilities.ActiveAbilityItem){
                // set the pickup delay to 0
                this.pickupDelay = 0;
                // set the item to be picked up
                this.isPickedUp = true;
                // set the item to be thrown
                this.isBeingThrown = true;
                // set the item to be bouncing
                this.bouncing = true;
                // set the velocity to 0
                this.setVelocity(0,0,0);
                // set the position to the player's position
                this.setPosition(player.getX(), player.getY(), player.getZ());
                // set the item to be discarded
                this.discardTimer = 0;
                // send message to client
                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                passedData.writeBoolean(true);
                ServerPlayNetworking.send((ServerPlayerEntity) player, ModMessages.ACTIVE_ABILITY, passedData);
                // cancel the method
                ci.cancel();
            }
        }
    }*/

    // prevent the item from being picked up with infinite pickup delay
    @Inject(method = "setPickupDelay", at = @At("HEAD"), cancellable = true)
    public void setPickupDelay(int pickupDelay, CallbackInfo ci){
        /*
        if(PlayerAbilities.ActiveAbility){
            this.pickupDelay = 32767;
            ci.cancel();
        }*/
    }

    // inject into onPlayerCollision
/*
    @Override
    protected void onBlockCollision(BlockState state) {
        super.onBlockCollision(state);
        // create explosion
        if(SetHunterCommand.pray != null && SetHunterCommand.pray.distanceTo(this) < distanceToPrey && isBeingThrown){
            this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 5, Explosion.DestructionType.DESTROY);
            this.discard();
        }
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        super.onPlayerCollision(player);
        if(SetHunterCommand.pray != null && SetHunterCommand.pray.distanceTo(this) < distanceToPrey && isBeingThrown){
            this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 5, Explosion.DestructionType.DESTROY);
            this.discard();
        }
    }*/
}

