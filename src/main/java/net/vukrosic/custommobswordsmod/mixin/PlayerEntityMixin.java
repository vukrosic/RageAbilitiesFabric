package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.vukrosic.custommobswordsmod.command.AbilitiesCommand;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.entity.ModEntities;
import net.vukrosic.custommobswordsmod.entity.custom.FireOrbEntity;
import net.vukrosic.custommobswordsmod.entity.custom.FirePearlEntity;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import net.vukrosic.custommobswordsmod.item.ModItems;
import net.vukrosic.custommobswordsmod.item.custom.ItemEntityMixinExt;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityExt {


    float rageBarProgress = 0f;

    boolean isSpawningLavaAround = false;
    // bed ability
    boolean isSuperjumping = false;
    // overrride getter and setter
    // super jump
    boolean activeAbilityActive = false;
    int fireSizeIncreaseCoundown = -10;
    int jumpTimer = 0;
    boolean hasFireOrb = false;
    boolean increasedSize = false;

    ServerBossBar rageBar = new ServerBossBar(Text.of("RageBar"), BossBar.Color.RED, BossBar.Style.PROGRESS);
    @Shadow
    private PlayerInventory inventory;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public abstract void sendMessage(Text message, boolean overlay);
    @Shadow
    public abstract void jump();
    @Override
    public boolean getActiveAbilityActive() {
        return activeAbilityActive;
    }
    @Override
    public boolean getIncreasedSize() {
        return increasedSize;
    }
    @Override
    public void setActiveAbilityActive(boolean activeAbilityActive) {
        this.activeAbilityActive = activeAbilityActive;
        if(!activeAbilityActive) {
            // clear all effects
            this.clearStatusEffects();
            ServerCommandSource commandSource = this.getCommandSource();
            PlayerEntity player = (PlayerEntity) (Object) this;
            CommandManager commandManager = Objects.requireNonNull(player.getServer()).getCommandManager();
            if (commandManager != null) {
                commandManager.executeWithPrefix(commandSource, "/scale set 1");
                increasedSize = false;
            }
            return;
        }
        int duration = 20 * 50000;
        if (PlayerAbilities.AbilityTier == 1) {
            this.clearStatusEffects();
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, duration, 6));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, duration, 6));
        } else if (PlayerAbilities.AbilityTier == 2) {
            this.clearStatusEffects();
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, duration, 6));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, duration, 6));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, duration, 6));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, duration, 6));
            ServerCommandSource commandSource = this.getCommandSource();
            PlayerEntity player = (PlayerEntity) (Object) this;
            CommandManager commandManager = Objects.requireNonNull(player.getServer()).getCommandManager();
            if (commandManager != null) {
                commandManager.executeWithPrefix(commandSource, "/scale set 2");
                increasedSize = true;
            }
        } else if (PlayerAbilities.AbilityTier == 3) {
            this.clearStatusEffects();
            this.clearStatusEffects();
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, duration, 6));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, duration, 6));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, duration, 6));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, duration, 6));
            ServerCommandSource commandSource = this.getCommandSource();
            PlayerEntity player = (PlayerEntity) (Object) this;
            CommandManager commandManager = Objects.requireNonNull(player.getServer()).getCommandManager();
            if (commandManager != null) {
                commandManager.executeWithPrefix(commandSource, "/scale set 2");
            }
            /*
            setVelocity(0, 60, 0);
            this.setVelocity(0, 60, 0);
            this.addVelocity(0, 60, 0);
            addVelocity(0, 60, 0);*/
            setSuperjumping(true);
        }
    }



    @Override
    public void setSuperjumping(boolean isBedAbilityActive) {
        this.isSuperjumping = isBedAbilityActive;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (!this.world.isClient) {
            if (rageBarProgress > 0 && Math.random() < 0.3) {
                rageBar.setPercent(rageBarProgress);
                if (this.rageBar.getPlayers().size() == 0 && SetHunterCommand.pray == (Object) this) {
                    this.rageBar.addPlayer((ServerPlayerEntity) (Object) this);
                    if (!AbilitiesCommand.serverBossBars.contains(rageBar)) {
                        AbilitiesCommand.serverBossBars.add(rageBar);
                    }
                }
            }



            fireSizeIncreaseCoundown();

/*
            if (isSuperjumping && jumpTimer > -5) {
                jumpTimer--;
            }*/

            if (PlayerAbilities.AbilityTier == 3 && jumpTimer <= -14 && this.isOnGround()) {
                jumpTimer = 0;
                setSuperjumping(false);
                bounceUpBlocksAround();
                smashPlayersIntoTheGround();
            }
            if (this.isOnGround()) {
                jumpTimer = 0;
            }
            else {
                jumpTimer--;
            }


            if (PlayerAbilities.AbilityTier == 1 || PlayerAbilities.AbilityTier == 2) {
                if(activeAbilityActive) {
                    setFireBehind();
                }
            }
            else if (PlayerAbilities.AbilityTier == 3) {

                if(!hasFireOrb) {
                    sendMessage(Text.of("Spawning fire orb!"), false);
                    FireOrbEntity fireOrbEntity = new FireOrbEntity(ModEntities.FIRE_ORB_ENTITY, this.world);
                    fireOrbEntity.setOwner((PlayerEntity) (Object) this);
                    fireOrbEntity.thrower = (PlayerEntity) (Object) this;
                }
            }
            else if (PlayerAbilities.AbilityTier == 4) {
                if(isOnGround())
                    turnFloorIntoNetherrack();
            }

        }
    }

    private void smashPlayersIntoTheGround() {
        // if SetHunterCommand.hunters is not empty
        if(SetHunterCommand.hunters != null) {
            for (PlayerEntity hunter : SetHunterCommand.hunters) {
                if (hunter != (Object) this) {
                    // if hunter is in range of 5 blocks
                    if (hunter.distanceTo( this) < 10) {
                        // teleport hunter to the ground
                        hunter.teleport(hunter.getX(), this.getY() - 2, hunter.getZ());
                    }
                }
            }
        }
    }

    private void setFireBehind() {
        if (this.isSprinting()) {
            Vec3d direction = this.getRotationVector();
            Vec3d position = this.getPos();
            BlockPos blockBehind = new BlockPos(position.x - direction.x, position.y - direction.y, position.z - direction.z);
            // get a few other blocks behind
            BlockPos blockBehind2 = new BlockPos(position.x - direction.x * 2, position.y - direction.y * 2, position.z - direction.z * 2);
            BlockPos blockBehind3 = new BlockPos(position.x - direction.x * 3, position.y - direction.y * 3, position.z - direction.z * 3);
            // also behind to the side
            BlockPos blockBehind4 = new BlockPos(position.x - direction.x * 2 + direction.z, position.y - direction.y * 2, position.z - direction.z * 2 - direction.x);
            BlockPos blockBehind5 = new BlockPos(position.x - direction.x * 2 - direction.z, position.y - direction.y * 2, position.z - direction.z * 2 + direction.x);
            for(BlockPos blockPos : new BlockPos[]{blockBehind, blockBehind2, blockBehind3, blockBehind4, blockBehind5}) {
                if (this.world.getBlockState(blockPos).isAir()) {
                    this.world.setBlockState(blockPos, Blocks.FIRE.getDefaultState());
                }
            }
        }
    }

    private void turnFloorIntoNetherrack() {

        // get all ground blocks around
        for (int x = -5; x < 5; x++) {
            for (int z = -5; z < 5; z++) {
                // turn it to netherrack
                BlockState[] blockStates = new BlockState[]{Blocks.NETHERRACK.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(),
                        Blocks.NETHER_WART_BLOCK.getDefaultState(), Blocks.NETHER_BRICK_WALL.getDefaultState()};
                // if the block is not already in the list
                if (!Arrays.asList(blockStates).contains(this.world.getBlockState(new BlockPos(this.getX() + x, this.getY() - 1, this.getZ() + z)))) {
                    // set it to a random block from the list
                    // if block is not air
                    if (!this.world.getBlockState(new BlockPos(this.getX() + x, this.getY() - 1, this.getZ() + z)).isAir()){
                        this.world.setBlockState(new BlockPos(this.getX() + x, this.getY() - 1, this.getZ() + z), blockStates[(int) (Math.random() * blockStates.length)]);
                    }
                }
            }
        }
    }

    private void fireSizeIncreaseCoundown() {
        // count down scale timer superJump
        if (fireSizeIncreaseCoundown > -10) {
            fireSizeIncreaseCoundown--;
            if (fireSizeIncreaseCoundown < 5) {
                ServerCommandSource commandSource = this.getCommandSource();
                PlayerEntity player = (PlayerEntity) (Object) this;
                CommandManager commandManager = Objects.requireNonNull(player.getServer()).getCommandManager();
                if (commandManager != null) {
                    commandManager.executeWithPrefix(commandSource, "/scale set 1");
                }
                fireSizeIncreaseCoundown = -10;
            }
        }
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void damage(DamageSource damageSource_1, float float_1, CallbackInfoReturnable<Boolean> cir) {
        if (!this.world.isClient) {

            // if damage source is fire
            if (SetHunterCommand.pray != null && SetHunterCommand.pray == (Object) this && damageSource_1.isFire()) {
                ServerCommandSource commandSource = this.getCommandSource();
                PlayerEntity player = (PlayerEntity) (Object) this;
                CommandManager commandManager = Objects.requireNonNull(player.getServer()).getCommandManager();
                if (commandManager != null) {
                    commandManager.executeWithPrefix(commandSource, "/scale set 2");
                    // set jump mboost to 3
                    commandManager.executeWithPrefix(commandSource, "/effect give @s minecraft:jump_boost 100 3");
                    // give resitance 5
                    commandManager.executeWithPrefix(commandSource, "/effect give @s minecraft:resistance 100 5");
                    fireSizeIncreaseCoundown = 1800;
                }
            }


            if (damageSource_1.getAttacker() instanceof PlayerEntity attacker &&
                    SetHunterCommand.hunters.contains(attacker)) {
                rageBarProgress += 0.1f;
                if (rageBarProgress >= 1f) {
                    rageBarProgress = 1f;
                }
            }
            // if bar is full
            if (damageSource_1.getAttacker() instanceof PlayerEntity)
                if(PlayerAbilities.AbilityTier == 1)
                    damageSource_1.getAttacker().setOnFireFor(3);
                else if(PlayerAbilities.AbilityTier == 2)
                    shootLava();

        }
    }



    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(DamageSource damageSource_1, CallbackInfo ci) {
        if (this.world.isClient) {
            return;
        }
        this.rageBar.removePlayer((ServerPlayerEntity) (Object) this);
    }



    void shootLava() {
        // for 1 to 10
        for (int i = 0; i < 2; i++) {
            FirePearlEntity enderPearlEntity = new FirePearlEntity(world, this);
            enderPearlEntity.setItem(ModItems.FIRE_PEARL.getDefaultStack());
            //enderPearlEntity.setVelocity(this, this.getPitch(), this.getYaw(), 0.0F, 1.5F, 1.0F);
            Random random = new Random();
            // random velocity
            enderPearlEntity.setVelocity(random.nextDouble() * 2 - 1, random.nextDouble() - 0.7F, random.nextDouble() * 2 - 1);
            enderPearlEntity.thrower = (PlayerEntity) (Object) this;
            world.spawnEntity(enderPearlEntity);
        }
    }

    void bounceUpBlocksAround() {
        // make a list of all blocks around the player
        List<BlockPos> blockPosList = new ArrayList<>();
        for (int x = -3; x < 4; x++) {
            for (int y = -3; y < 4; y++) {
                for (int z = -3; z < 4; z++) {
                    blockPosList.add(this.getBlockPos().add(x, y, z));
                }
            }
        }
        // get block state of all blocks
        List<BlockState> blockStateList = new ArrayList<>();
        for (BlockPos blockPos : blockPosList) {
            blockStateList.add(this.world.getBlockState(blockPos));
        }
        // spawn item entity for all blocks
        for (int i = 0; i < blockStateList.size(); i++) {
            ItemEntity itemEntity = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), new ItemStack(blockStateList.get(i).getBlock()));
            // place it on the ground around randomly
            itemEntity.refreshPositionAndAngles(blockPosList.get(i).getX(), blockPosList.get(i).getY() + 1, blockPosList.get(i).getZ(), 0, 0);
            ((ItemEntityMixinExt) itemEntity).setBouncing(true);
            // add velocity away from the player
            float force = 0.25F;
            itemEntity.addVelocity((blockPosList.get(i).getX() - this.getBlockPos().getX()) * force, force, (blockPosList.get(i).getZ() - this.getBlockPos().getZ()) * force);
            this.world.spawnEntity(itemEntity);
            // remove block
            this.world.setBlockState(blockPosList.get(i), Blocks.AIR.getDefaultState());
        }
    }
}
