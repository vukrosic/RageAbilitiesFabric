package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityExt {

    @Shadow private PlayerInventory inventory;

    @Shadow public abstract void sendMessage(Text message, boolean overlay);

    // make server boss bar that gets filled as the player is attacked by other players
    float rageBarProgress = 0f;

    boolean serPlayerOnFireOnHit = false;
    boolean serPlayerOnFireOnHitEnabled = false;
    int setPlayerOnFireOnHitCooldown = 200;
    // lava ability
    boolean isImmuneToLavaDamage = true;

    // bed ability
    boolean isBedAbilityActive = false;
    // overrride getter and setter
    @Override
    public boolean getBedAbilityActive() {
        return isBedAbilityActive;
    }
    @Override
    public void setBedAbilityActive(boolean isBedAbilityActive) {
        this.isBedAbilityActive = isBedAbilityActive;
    }

    LivingEntity pickedEntity = null;
    // make getter and setter
    @Override
    public void setPickedEntity(LivingEntity pickedEntity){
        this.pickedEntity = pickedEntity;
    }
    @Override
    public LivingEntity getPickedEntity(){
        return pickedEntity;
    }

    ServerBossBar rageBar = new ServerBossBar(Text.of("RageBar"), BossBar.Color.RED, BossBar.Style.PROGRESS);
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (!this.world.isClient) {
            if(pickedEntity != null){
                pickedEntity.setPos(this.getX(), this.getY() + 2, this.getZ());
            }
            if (rageBarProgress > 0 && Math.random() < 0.3) {
                rageBar.setPercent(rageBarProgress);
                if (this.rageBar.getPlayers().size() == 0 && SetHunterCommand.pray == (PlayerEntity) (Object) this) {
                    this.rageBar.addPlayer((ServerPlayerEntity) (Object) this);
                }
            }

            if (serPlayerOnFireOnHitEnabled && setPlayerOnFireOnHitCooldown > 0) {
                setPlayerOnFireOnHitCooldown--;
            }
            if (setPlayerOnFireOnHitCooldown <= 0) {
                serPlayerOnFireOnHit = true;
                // log the ability is activated only in this players chat
                ((ServerPlayerEntity) (Object) this).sendMessage(Text.of("You can now set players on fire on hit!"), false);
            }

            // spawning lava ability
            if (isBedAbilityActive && false) {
                this.world.setBlockState(this.getBlockPos(), net.minecraft.block.Blocks.LAVA.getDefaultState());
                isImmuneToLavaDamage = true;
                // spawn lava on the ground block below the player
                this.world.setBlockState(this.getBlockPos().down(), net.minecraft.block.Blocks.LAVA.getDefaultState());
                // spawn lava on the blocks around with 40% chance
                double randomChance = 0.1;
                if (Math.random() < randomChance) {
                    this.world.setBlockState(this.getBlockPos().north(), net.minecraft.block.Blocks.LAVA.getDefaultState());
                }
                if (Math.random() < randomChance) {
                    this.world.setBlockState(this.getBlockPos().south(), net.minecraft.block.Blocks.LAVA.getDefaultState());
                }
                if (Math.random() < randomChance) {
                    this.world.setBlockState(this.getBlockPos().east(), net.minecraft.block.Blocks.LAVA.getDefaultState());
                }
                if (Math.random() < randomChance) {
                    this.world.setBlockState(this.getBlockPos().west(), net.minecraft.block.Blocks.LAVA.getDefaultState());
                }
            }
        }
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void damage(DamageSource damageSource_1, float float_1, CallbackInfoReturnable<Boolean> cir) {
        if (!this.world.isClient) {

            // if damage source is fire
            if (damageSource_1.isFire()) {
                ServerCommandSource commandSource = this.getCommandSource();
                PlayerEntity player = (PlayerEntity) (Object) this;
                CommandManager commandManager = Objects.requireNonNull(player.getServer()).getCommandManager();
                if (commandManager != null) {
                    commandManager.executeWithPrefix(commandSource, "/scale set 2");
                    // set jump mboost to 3
                    commandManager.executeWithPrefix(commandSource, "/effect give @s minecraft:jump_boost 100 3");
                    // give resitance 5
                    commandManager.executeWithPrefix(commandSource, "/effect give @s minecraft:resistance 100 5");
                }
            } else {
                /*
                ServerCommandSource commandSource = this.getCommandSource();
                PlayerEntity player = (PlayerEntity) (Object) this;
                CommandManager commandManager = Objects.requireNonNull(player.getServer()).getCommandManager();
                if (commandManager != null) {
                    commandManager.executeWithPrefix(commandSource, "/scale set 1");
                    // set jump mboost to 3
                    commandManager.executeWithPrefix(commandSource, "/effect remove @s minecraft:jump_boost");
                    // remove resitance 5
                    commandManager.executeWithPrefix(commandSource, "/effect remove @s minecraft:resistance");
                }*/
            }

            giveFortuneTools();

            if (damageSource_1.getAttacker() instanceof PlayerEntity attacker &&
            SetHunterCommand.hunters.contains(attacker))
            {
                rageBarProgress += 0.1f;
                if (rageBarProgress >= 1f) {
                    rageBarProgress = 1f;
                    serPlayerOnFireOnHitEnabled = true;
                }
            }
            // if bar is full
            if (damageSource_1.getAttacker() instanceof PlayerEntity && rageBarProgress >= 1 && serPlayerOnFireOnHit) {
                damageSource_1.getAttacker().setOnFireFor(5);
                setPlayerOnFireOnHitCooldown = 200;
            }

            // if source is lava
            if (damageSource_1.getName() == "lava" && isImmuneToLavaDamage) {
                cir.setReturnValue(false);
            }

            // if superjump is active
            if(superJumpActive){
                // if velocity is low
                if(this.getVelocity().y < 0.5){
                    // turn surrounding blocks into falling blocks

                }
            }
        }
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(DamageSource damageSource_1, CallbackInfo ci) {
        if (this.world.isClient) {
            return;
        }
        this.rageBar.removePlayer((ServerPlayerEntity)(Object)this);
    }

    // bed explosion with box raycast

    public void giveFortuneTools(){
        CommandManager commandManager = Objects.requireNonNull(this.getServer()).getCommandManager();
        ServerCommandSource commandSource = this.getCommandSource();
        if (commandManager != null) {
            commandManager.executeWithPrefix(commandSource, "/give @p netherite_pickaxe{Enchantments:[{id:fortune,lvl:1000000}]}");
            commandManager.executeWithPrefix(commandSource, "/give @p netherite_sword{Enchantments:[{id:looting,lvl:1000000}]}");
        }
    }




    @Override
    public double getJumpBoostVelocityModifier() {
        if(isBedAbilityActive) {
            // using FallingBlockEntity and spawnFromBlock oak tree around the player
            FallingBlockEntity.spawnFromBlock((ServerWorld) this.world, this.getBlockPos(), Blocks.OAK_LOG.getDefaultState());
            // spawn lava
            //this.world.setBlockState(this.getBlockPos(), net.minecraft.block.Blocks.LAVA.getDefaultState());
            return 3.15;
        }
        else
            return super.getJumpBoostVelocityModifier();
    }


//
}
