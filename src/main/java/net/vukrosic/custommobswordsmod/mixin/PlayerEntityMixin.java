package net.vukrosic.custommobswordsmod.mixin;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.vukrosic.custommobswordsmod.command.AbilitiesCommand;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.entity.custom.FireOrbEntity;
import net.vukrosic.custommobswordsmod.entity.custom.FirePearlEntity;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import net.vukrosic.custommobswordsmod.item.ModItems;
import net.vukrosic.custommobswordsmod.item.custom.ItemEntityMixinExt;
import net.vukrosic.custommobswordsmod.networking.ModMessages;
import net.vukrosic.custommobswordsmod.util.abilities.PlayerAbilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.stream.Collectors;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityExt {

    //public ArrayList<ItemEntity> pickedUpBlocks = new ArrayList<>();
   // public boolean pickUpBlocks = false;


    boolean isSpawningLavaAround = false;
    // bed ability
    int fireSizeIncreaseCoundown = -10;
    int jumpTimer = 0;
    boolean hasFireOrb = false;
    boolean increasedSize = false;
    boolean flyingEnabled = false;
    FireOrbEntity fireOrb = null;
    ServerBossBar rageBar = new ServerBossBar(Text.of("RageBar"), BossBar.Color.RED, BossBar.Style.PROGRESS);
    @Shadow
    private PlayerInventory inventory;
    /*
    @Override
    public void addPickUpBlocks(ItemEntity itemEntity) {
        this.pickedUpBlocks.add(itemEntity);
    }*/
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Shadow
    public abstract void sendMessage(Text message, boolean overlay);
    @Shadow
    public abstract void jump();

    @Override
    public boolean getIncreasedSize() {
        return increasedSize;
    }

    /*
    @Override
    public void setPickUpBlocks(boolean pickUpBlocks) {
        this.pickUpBlocks = pickUpBlocks;
    }
    @Override
    public void pickUpBlocksInXBlockRadius(int radius){

        // get block below this
        BlockPos blockPos = this.getBlockPos().add(0, -1, 0);
        // get block state
        BlockState blockState = this.world.getBlockState(blockPos);
        // spawn item
        ItemEntity itemEntity = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), new ItemStack(blockState.getBlock()));
        // place it on the ground around randomly
        itemEntity.refreshPositionAndAngles(SetHunterCommand.pray.getX(), SetHunterCommand.pray.getY() + 2.5, SetHunterCommand.pray.getZ(), 0, 0);
        ((ItemEntityMixinExt) itemEntity).setPickedUp(true);
        itemEntity.setNoGravity(true);
        this.world.spawnEntity(itemEntity);
        pickedUpBlocks.add(itemEntity);
        this.world.setBlockState(blockPos.add(1,0,0), Blocks.AIR.getDefaultState());
        //((ItemEntityMixinExt) itemEntity).setPickedUp(true);
    }*/

/*
    @Override
    public void throwBlocks(){
        for(ItemEntity itemEntity : pickedUpBlocks){
            Vec3d direction = getLookDirection((PlayerEntity)(Object)this, 1);
            itemEntity.setVelocity(direction.multiply(7));
            ((ItemEntityMixinExt) itemEntity).setPickedUp(false);
            ((ItemEntityMixinExt) itemEntity).setBeingThrown(true);
            itemEntity.setNoGravity(true);
            SetHunterCommand.pray.sendMessage(Text.of("Throwing blocksssssss"), false);
        }
        pickedUpBlocks.clear();
    }

    private static Vec3d getLookDirection(PlayerEntity player, float throwForce) {
        Vec3d cameraPos = player.getCameraPosVec(0);
        //Vec3d cameraPos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        Vec3d cameraDirection = player.getRotationVec(0);
        Vec3d vec3d3 = cameraDirection.subtract(cameraPos).multiply(throwForce);
        Vec3d entityPos = player.getPos();
        Vec3d lookRotation = player.getRotationVector();
        Vec3d cameraPos1 = player.getCameraPosVec(0);
        Vec3d crossPos = cameraPos1.add(lookRotation.multiply(100));
        Vec3d direction = crossPos.subtract(entityPos).normalize();
        return direction;
    }*/

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if(!SetHunterCommand.huntersUUIDs.isEmpty() && SetHunterCommand.huntersUUIDs.contains(this.getUuid())
        && PlayerAbilities.AbilityTier == 3){
            // get all entities in 10 block radius
            List<Entity> entities = this.world.getOtherEntities(this, new Box(this.getBlockPos()).expand(1));
            // check if there is a fire orb
            for(Entity entity : entities){
                if(entity instanceof LivingEntity livingEntity && !(livingEntity instanceof PlayerEntity)){
                    if(Math.random() < 0.1f){
                        SetHunterCommand.pray.sendMessage(Text.of("!(livingEntity instanceof PlayerEntity) =" + (livingEntity instanceof PlayerEntity) ), false);
                        this.damage(DamageSource.MAGIC, 2);
                    }
                }
            }
        }
        /*
        // big blocks
        if(SetHunterCommand.pray != null && SetHunterCommand.pray.getUuid().equals(this.getUuid())) {
            if (PlayerAbilities.AbilityTier == 3 && jumpTimer <= -14 && this.isOnGround() && activeAbilityActive) {
                jumpTimer = 0;
                if(!world.isClient)
                    ServerPlayNetworking.send((ServerPlayerEntity) (Object) this, ModMessages.BOUNCE_BLOCKS, PacketByteBufs.empty());
                //PlayerAbilities.smashPlayersIntoTheGround();
                // get item below

                //((ItemEntityMixinExt) itemEntity).setBouncing(true);
            }
            if (this.isOnGround()) {
                jumpTimer = 0;
            } else {
                jumpTimer--;
            }
        }
        PlayerAbilities.bigBlocksTimer = 10;
        if(PlayerAbilities.bigBlocksTimer > 0){
            PlayerAbilities.bigBlocksTimer--;
        }
*/

        if (!this.world.isClient) {
            if (PlayerAbilities.rageBarProgress > 0) {
                rageBar.setPercent(PlayerAbilities.rageBarProgress);
                if (SetHunterCommand.pray != null && SetHunterCommand.pray.getUuid().equals(this.getUuid()) && PlayerAbilities.AbilityTier == 0) {
                    this.rageBar.addPlayer((ServerPlayerEntity) (Object) this);
                    this.rageBar.setVisible(true);
                    if (!AbilitiesCommand.serverBossBars.contains(rageBar)) {
                        AbilitiesCommand.serverBossBars.add(rageBar);
                    }
                }
            }
            else{
                if (SetHunterCommand.pray != null && SetHunterCommand.pray.getUuid().equals(this.getUuid()) && PlayerAbilities.AbilityTier == 0) {
                    if(this.rageBar.getPlayers().contains((ServerPlayerEntity) (Object) this)){
                        this.rageBar.removePlayer((ServerPlayerEntity) (Object) this);
                        this.rageBar.setVisible(false);
                    }
                    if (AbilitiesCommand.serverBossBars.contains(rageBar)) {
                        AbilitiesCommand.serverBossBars.remove(rageBar);
                    }
                }
            }
            /*
            if(PlayerAbilities.AbilityTier != 0 && (this.rageBar.getPlayers().contains((ServerPlayerEntity) (Object) this))) {
                removeBossBar();
            }*/

/*
            for(ItemEntity itemEntity : pickedUpBlocks){
                itemEntity.setPos(this.getX(), this.getY() + 2.5f, this.getZ());
                //itemEntity.setVelocity(0, 0, 0);
            }*/







            if(SetHunterCommand.pray != null && SetHunterCommand.pray.getUuid().equals(this.getUuid())) {
                if (PlayerAbilities.AbilityTier >= 2 && jumpTimer <= -20 && this.isOnGround() && PlayerAbilities.ActiveAbility) {
                    jumpTimer = 0;
                    if(!SetHunterCommand.hunters.isEmpty()) {
                        if(SetHunterCommand.hunters.contains((PlayerEntity)(Object)this)){
                            // teleport 2 blockd down
                            this.teleport(this.getX(), this.getY() - 3, this.getZ());
                            this.setVelocity(0, -2, 0);
                            sendMessage(Text.of("Teleporting down"), false);
                        }

                        /*
                        ArrayList<PlayerEntity> hunters = world.getPlayers().stream().filter(playerEntity -> SetHunterCommand.huntersUUIDs.contains(playerEntity.getUuid())).collect(Collectors.toCollection(ArrayList::new));
                        for (PlayerEntity Hunter : hunters) {
                            // if distance to prey is 3 or less, smash them into the ground
                            if (Hunter.distanceTo(SetHunterCommand.pray) <= 4) {
                                PlayerAbilities.smashPlayersIntoTheGround(Hunter);
                                Hunter.sendMessage(Text.of("You have been smashed into the ground"), false);
                                smashPlayersIntoTheGround(Hunter);
                                secondTry(Hunter);
                            }
                        }*/
                    }
                    else {
                        // send message with a list of all names
                        String names = "";
                        for (PlayerEntity hunter : SetHunterCommand.hunters) {
                            names += hunter.getName() + ", ";
                        }
                        SetHunterCommand.pray.sendMessage(Text.of("Hunter are null, names: " + names), false);
                    }
                    //smashPlayersIntoTheGround();
                    bounceUpBlocksAround();
                }
                if (this.isOnGround()) {
                    jumpTimer = 0;
                } else {
                    jumpTimer--;
                }


                if (PlayerAbilities.AbilityTier != 0) {
                    if (PlayerAbilities.ActiveAbility && this.isOnGround()) {
                        setFireBehind();
                    }
                }


                if (PlayerAbilities.AbilityTier == 4) {
                    if (isOnGround())
                        turnFloorIntoNetherrack();
                }

            }


        }

        //if(SetHunterCommand.hunters.contains(this)){
        if(SetHunterCommand.pray != null) {
            if (SetHunterCommand.pray.getUuid().equals(this.getUuid())) {
                // if there are mobs in 1 block radius, damage this
                List<Entity> entities = this.world.getOtherEntities(this, new Box(this.getBlockPos()).expand(1));
                if (!entities.isEmpty()) {
                    if (Math.random() < 0.2f) {
                        this.damage(DamageSource.MAGIC, 2);
                    }
                }
            }
        }
    }


    private void smashPlayersIntoTheGround(PlayerEntity hunter){
        hunter.setVelocity(0, -2.5f, 0);
        hunter.setPos(hunter.getX(), hunter.getY() - 2.5f, hunter.getZ());
        hunter.teleport(hunter.getX(), hunter.getY() - 2.5f, hunter.getZ());
        hunter.refreshPositionAfterTeleport(hunter.getX(), hunter.getY() - 2.5f, hunter.getZ());
    }

    private void secondTry(PlayerEntity hunter){
        hunter.setVelocity(0, -0.5, 0);
        hunter.teleport(hunter.getX(), hunter.getY() - 3, hunter.getZ());
    }

    private void setFireBehind() {
        if (this.isSprinting() && this.isOnGround()) {
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
            if (SetHunterCommand.pray != null && SetHunterCommand.pray.getUuid() == this.getUuid()) {
                PlayerAbilities.onPreyTakeDamage(damageSource_1, float_1, (PlayerEntity) (Object) this);
            }
            // if damage source is fire
            /*
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
            }*/

            if (damageSource_1.getAttacker() instanceof PlayerEntity attacker &&
                    SetHunterCommand.hunters.contains(attacker)) {
                PlayerAbilities.rageBarProgress += 0.1f;
                if (PlayerAbilities.rageBarProgress >= 1f) {
                    PlayerAbilities.rageBarProgress = 1f;
                }
            }
            // testing by fire damage
            if (damageSource_1.isFire()) {

            }

            if (damageSource_1.isFromFalling()) {

            }

            // if bar is full
            /*
            if (damageSource_1.getAttacker() instanceof PlayerEntity)
                if(SetHunterCommand.pray != null && SetHunterCommand.pray == (PlayerEntity) (Object)this) {
                    if (PlayerAbilities.AbilityTier == 1)
                        damageSource_1.getAttacker().setOnFireFor(3);
                    else if (PlayerAbilities.AbilityTier == 2)
                        shootLava();
                }
            */
        }
    }

    @Shadow
    private PlayerInventory getInventory() {
        return null;
    }



    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(DamageSource damageSource_1, CallbackInfo ci) {
        if (!this.world.isClient) {
            if(this.rageBar.getPlayers().contains((ServerPlayerEntity) (Object) this)){
                this.rageBar.removePlayer((ServerPlayerEntity) (Object) this);
            }
            if (AbilitiesCommand.serverBossBars.contains(rageBar)) {
                AbilitiesCommand.serverBossBars.remove(rageBar);
            }
            PlayerAbilities.rageBarProgress = 0f;
        }
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
            //((ItemEntityMixinExt) itemEntity).setBouncing(true);
            ServerPlayNetworking.send((ServerPlayerEntity) (Object) this, ModMessages.BOUNCE_BLOCKS, new PacketByteBuf(Unpooled.buffer()));
            // add velocity away from the player
            float force = 0.25F;
            itemEntity.addVelocity((blockPosList.get(i).getX() - this.getBlockPos().getX()) * force, force, (blockPosList.get(i).getZ() - this.getBlockPos().getZ()) * force);
            this.world.spawnEntity(itemEntity);
            ((ItemEntityMixinExt) itemEntity).setBouncing(true);
            itemEntity.setPickupDelayInfinite();
            // remove block
            this.world.setBlockState(blockPosList.get(i), Blocks.AIR.getDefaultState());
        }
    }


/*
    void pickUpBlocksAround() {
        // make a list of all blocks around the player
        List<BlockPos> blockPosList = new ArrayList<>();
        for (int x = -3; x < 4; x++) {
            for (int y = -1; y < 1; y++) {
                for (int z = -3; z < 4; z++) {
                    blockPosList.add(SetHunterCommand.pray.getBlockPos().add(x, y, z));
                }
            }
        }
        // get block state of all blocks
        List<BlockState> blockStateList = new ArrayList<>();
        for (BlockPos blockPos : blockPosList) {
            blockStateList.add(SetHunterCommand.pray.world.getBlockState(blockPos));
        }
        // spawn item entity for all blocks
        for (int i = 0; i < blockStateList.size(); i++) {
            ItemEntity itemEntity = new ItemEntity(SetHunterCommand.pray.world, SetHunterCommand.pray.getX(), SetHunterCommand.pray.getY(), SetHunterCommand.pray.getZ(),
                    new ItemStack(blockStateList.get(i).getBlock()));
            // place it on the ground around randomly
            itemEntity.refreshPositionAndAngles(blockPosList.get(i).getX(), blockPosList.get(i).getY() + 1, blockPosList.get(i).getZ(), 0, 0);
            //((ItemEntityMixinExt) itemEntity).setBouncing(true);
            //ServerPlayNetworking.send((ServerPlayerEntity) (Object) SetHunterCommand.pray, ModMessages.BOUNCE_BLOCKS, new PacketByteBuf(Unpooled.buffer()));
            // add velocity away from the player
            float force = 0.25F;
            // set position above the player
            itemEntity.setPos(SetHunterCommand.pray.getX(), SetHunterCommand.pray.getY() + 3, SetHunterCommand.pray.getZ());
            itemEntity.setNoGravity(true);

            SetHunterCommand.pray.world.spawnEntity(itemEntity);
            SetHunterCommand.pray.sendMessage(Text.of("position size: " + itemEntity.getPos()), false);
            // remove block
            SetHunterCommand.pray.world.setBlockState(blockPosList.get(i), Blocks.AIR.getDefaultState());
        }
    }*/

    // inject into setHealth



    @Override
    public void setHealth(float health) {
        if(health <= 0){
            PlayerAbilities.rageBarProgress = 0f;
            rageBar.removePlayer((ServerPlayerEntity) (Object) this);
            rageBar.setVisible(false);
            /*
            if (PlayerAbilities.rageBarProgress > 0) {
                rageBar.setPercent(PlayerAbilities.rageBarProgress);
                if (SetHunterCommand.pray != null && SetHunterCommand.pray.getUuid().equals(this.getUuid()) && PlayerAbilities.AbilityTier == 0) {
                    this.rageBar.addPlayer((ServerPlayerEntity) (Object) this);
                    if (!AbilitiesCommand.serverBossBars.contains(rageBar)) {
                        AbilitiesCommand.serverBossBars.add(rageBar);
                    }
                }
            }
            else{
                if (SetHunterCommand.pray != null && SetHunterCommand.pray.getUuid().equals(this.getUuid()) && PlayerAbilities.AbilityTier == 0) {
                    if(this.rageBar.getPlayers().contains((ServerPlayerEntity) (Object) this)){
                        this.rageBar.removePlayer((ServerPlayerEntity) (Object) this);
                    }
                    if (AbilitiesCommand.serverBossBars.contains(rageBar)) {
                        AbilitiesCommand.serverBossBars.remove(rageBar);
                    }
                }
            }*/
        }
        super.setHealth(health);
    }
}
