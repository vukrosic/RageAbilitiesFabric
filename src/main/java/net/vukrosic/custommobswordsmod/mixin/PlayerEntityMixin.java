package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityExt {

    public int tier = 0;
    // make server boss bar that gets filled as the player is attacked by other players
    float rageBarProgress = 0f;
    boolean serPlayerOnFireOnHit = false;
    boolean serPlayerOnFireOnHitEnabled = false;
    int setPlayerOnFireOnHitCooldown = 200;
    // lava ability
    boolean isImmuneToLavaDamage = true;
    boolean isSpawningLavaAround = false;
    // bed ability
    boolean isBedAbilityActive = false;
    // overrride getter and setter
    // super jump
    boolean superJumpActive = false;
    int fireSizeIncreaseCoundown = -10;
    int jumpTimer = 4;
    LivingEntity pickedEntity = null;
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

    public void setSerPlayerOnFireOnHitEnabled(boolean serPlayerOnFireOnHitEnabled) {
        this.serPlayerOnFireOnHitEnabled = serPlayerOnFireOnHitEnabled;
    }

    //getter
    @Override
    public int getPlayerOnFireOnHitCooldown() {
        return setPlayerOnFireOnHitCooldown;
    }

    public void setSpawningLavaAround(boolean isSpawningLavaAround) {
        this.isSpawningLavaAround = isSpawningLavaAround;
    }

    @Override
    public boolean getSuperJumpActive() {
        return superJumpActive;
    }

    @Override
    public void setSuperJumpActive(boolean superJumpActive) {
        // set velocty of this to 100
        //this.setVelocity(this.getVelocity().x, 60, this.getVelocity().z);
        //this.addVelocity(0, 60, 0);
        //jump();
        this.superJumpActive = superJumpActive;
    }

    @Override
    public boolean getBedAbilityActive() {
        return isBedAbilityActive;
    }

    @Override
    public void setBedAbilityActive(boolean isBedAbilityActive) {
        this.isBedAbilityActive = isBedAbilityActive;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (!this.world.isClient) {
            if(SetHunterCommand.pray == (Object) this || PlayerAbilities.pickedEntities != null) {
                // set position of all picked entities to this
                for(LivingEntity entity : PlayerAbilities.pickedEntities){
                    double randX = this.getX() + new Random().nextFloat() * 2 - 1;
                    double randY = this.getY() + new Random().nextFloat() * 2 - 1;
                    double randZ = this.getZ()+ new Random().nextFloat() * 2 - 1;
                    entity.teleport(randX, randY, randZ);
                }
            }
            if (rageBarProgress > 0 && Math.random() < 0.3) {
                rageBar.setPercent(rageBarProgress);
                if (this.rageBar.getPlayers().size() == 0 && SetHunterCommand.pray == (Object) this) {
                    this.rageBar.addPlayer((ServerPlayerEntity) (Object) this);
                    if (!AbilitiesCommand.serverBossBars.contains(rageBar)) {
                        AbilitiesCommand.serverBossBars.add(rageBar);
                    }
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


            fireSizeIncreaseCoundown();


            if (isBedAbilityActive && jumpTimer > -10) {
                jumpTimer--;
            }
            if (isBedAbilityActive && jumpTimer <= -10 && this.isOnGround()) {
                jumpTimer = 4;
                bounceUpBlocksAround();
            }
            if (this.isOnGround()) {
                jumpTimer = 4;
            }

            // shoot lava
            if (isBedAbilityActive && superJumpActive) {
                shootLava();
                superJumpActive = false;
            }
            if (PlayerAbilities.AbilityTier == 1 && superJumpActive) {
                setFireBehind();
            }
            else if (PlayerAbilities.AbilityTier == 2) {

            }
            else if (PlayerAbilities.AbilityTier == 3) {

            }
            else if (PlayerAbilities.AbilityTier == 4) {
                turnFloorIntoNetherrack();
            }

        }
    }

    private void setFireBehind() {
        if (this.isSprinting()) {
            Vec3d direction = this.getRotationVector();
            Vec3d position = this.getPos();
            BlockPos blockBehind = new BlockPos(position.x - direction.x, position.y - direction.y, position.z - direction.z);
            this.world.setBlockState(blockBehind, Blocks.FIRE.getDefaultState());
        }
    }

    private void turnFloorIntoNetherrack() {
        // get all ground blocks around
        for (int x = -5; x < 5; x++) {
            for (int z = -5; z < 5; z++) {
                // turn it to netherrack
                BlockState[] blockStates = new BlockState[]{Blocks.NETHERRACK.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(),
                        Blocks.NETHER_WART_BLOCK.getDefaultState(), Blocks.NETHER_BRICK_WALL.getDefaultState()};
                world.setBlockState(new BlockPos(this.getX() + x, this.getY() - 1, this.getZ() + z), blockStates[new Random().nextInt(blockStates.length)]);
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
                    serPlayerOnFireOnHitEnabled = true;
                }
            }
            // if bar is full
            if (damageSource_1.getAttacker() instanceof PlayerEntity && rageBarProgress >= 1 && serPlayerOnFireOnHit) {
                damageSource_1.getAttacker().setOnFireFor(5);
                setPlayerOnFireOnHitCooldown = 200;
            }
        }
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(DamageSource damageSource_1, CallbackInfo ci) {
        if (this.world.isClient) {
            return;
        }
        this.rageBar.removePlayer((ServerPlayerEntity) (Object) this);
    }

    @Override
    public double getJumpBoostVelocityModifier() {
        if (isBedAbilityActive) {
            return 2.55;
        } else
            return super.getJumpBoostVelocityModifier();
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
                    // skip if it's under the player
                    if (y < 0 && x == 0 && z == 0)
                        continue;
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
