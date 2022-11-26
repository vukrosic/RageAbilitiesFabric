package net.vukrosic.custommobswordsmod.mixin;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.vukrosic.custommobswordsmod.command.RestoreDeathCommand;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.effect.ModEffects;
import net.vukrosic.custommobswordsmod.entity.ModEntities;
import net.vukrosic.custommobswordsmod.entity.custom.EnderZoglinEntityGL;
import net.vukrosic.custommobswordsmod.entity.custom.NateDimSpiderEntity;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import net.vukrosic.custommobswordsmod.entity.custom.corruptedallay.CorruptedAllayVexEntityGL;
import net.vukrosic.custommobswordsmod.entity.custom.fireenderman.CombustometerManager;
import net.vukrosic.custommobswordsmod.entity.custom.shieldingshulker.ShieldingShulkerEntity;
import net.vukrosic.custommobswordsmod.entity.custom.chunken.SpittingChickenEntity;
import net.vukrosic.custommobswordsmod.entity.custom.fireenderman.FireEndermanEntityGL;
import net.vukrosic.custommobswordsmod.entity.custom.frogking.FrogKingEntity;
import net.vukrosic.custommobswordsmod.entity.custom.summoner.SummonerEntityGL;
import net.vukrosic.custommobswordsmod.item.ModItems;
import net.vukrosic.custommobswordsmod.item.custom.ItemEntityMixinExt;
import net.vukrosic.custommobswordsmod.networking.ModMessages;
import net.vukrosic.custommobswordsmod.util.CarbonPoisoningEffectManager;
import net.vukrosic.custommobswordsmod.util.FireInfectedPlayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin_old extends LivingEntity implements PlayerEntityExt {

    // get player's dimension

    FrogKingEntity frogKingEntity;
    ShieldingShulkerEntity shieldingShulkerEntity;
    ServerBossBar serverBossBar;

    boolean hasCombusometerEffect = false;

    public float BeamProgress = 0;


    public boolean isInNateDimension = false;
    int NateDimensionTimer = 200;

    boolean isInChickenDimension = false;
    public boolean hasChickenEffect = false;
    public SummonerEntityGL summonerEntityGL = null;

    public boolean fireEndermenEnabled = false;

    int allayRespawnTimer, allayMaxRespawnTimer = 100;
    boolean hasAllayHelmet = false;
    boolean allayRespawn = true;



    protected PlayerEntityMixin_old(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }





    @Shadow private PlayerInventory inventory;

    @Shadow public abstract void sendMessage(Text message, boolean overlay);
    @Shadow public HungerManager getHungerManager() {
        return null;
    }

    @Shadow public abstract Text getName();

    boolean fireInfected = false;

/*
    public void setCombusometerEffect(boolean hasCombusometerEffect){
        if(hasCombusometerEffect) {
            if(!CombustometerManager.players.contains(this.getUuid())) {
                CombustometerManager.players.add(this.getUuid());
            }
            //addCombustomenter();
        }
        else {
            if(CombustometerManager.players.contains(this.getUuid())) {
                CombustometerManager.players.remove(this.getUuid());
            }
        }
        this.hasCombusometerEffect = hasCombusometerEffect;
    }*/

    public boolean hasCombusometerEffect(){
        return hasCombusometerEffect;
    }


    public boolean hasChickenEffect(){
        return hasChickenEffect;
    };

    public boolean fireEndermenEnabled(){
        return this.fireEndermenEnabled;
    };
    public void setFireEndermenEnabled(boolean fireEndermenEnabled){
        this.fireEndermenEnabled = fireEndermenEnabled;
    };

    public void setChickenEffect(boolean hasChickenEffect){
        this.hasChickenEffect = hasChickenEffect;
    }
    @Override
    public void setFireInfected(boolean fireInfected) {

        this.fireInfected = fireInfected;
    }

    public void setInNateDimension(boolean isInNateDimension){
        this.isInNateDimension = isInNateDimension;
    }
    public boolean isInNateDimension(){
        return isInNateDimension;
    }
    public void setSummonerEntityGL(SummonerEntityGL summonerEntityGL){
        this.summonerEntityGL = summonerEntityGL;
    }
    public SummonerEntityGL getSummonerEntityGL(){
        return summonerEntityGL;
    }

    public void setInChickenDimention(boolean isInChickenDimention){
        if(world.isClient) return;
        CommandManager commandManager = world.getServer().getCommandManager();
        if(isInChickenDimention){
            commandManager.executeWithPrefix(world.getServer().getCommandSource(), "gamemode adventure");
        }
        else {
            commandManager.executeWithPrefix(world.getServer().getCommandSource(), "gamemode survival");
        }
        this.isInChickenDimension = isInChickenDimention;
    }

    public boolean isInChickenDimention(){
        return isInChickenDimension;
    }

    @Override
    public boolean getFireInfected() {
        return fireInfected;
    }

    @Override
    public void setFrogKingEntity(FrogKingEntity frogKingEntity) {
        if(world.isClient()){
            return;
        }
        this.frogKingEntity = frogKingEntity;
    }

    @Override
    public void stopRiding() {
        super.stopRiding();
    }

    @Override
    public FrogKingEntity getFrogKingEntity() {
        return frogKingEntity;
    }
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick (CallbackInfo info) {
        removeEnderZoglinEffect();
        if (!world.isClient) {
            if(SetHunterCommand.pray != null && SetHunterCommand.pray.getUuid().equals(this.getUuid())){
                // set prey HP
                //((InGameHudMixinExt) MinecraftClient.getInstance().inGameHud).setPreyHealth(this.getHealth());
                PacketByteBuf bufHp = new PacketByteBuf(Unpooled.buffer());
                bufHp.writeFloat(this.getHealth());
                ServerPlayNetworking.send((ServerPlayerEntity) (Object) this, ModMessages.PREY_HEALTH_HUD, bufHp);

                Vec3d pos = new Vec3d(SetHunterCommand.pray.getBlockPos().getX(), SetHunterCommand.pray.getBlockPos().getY(), SetHunterCommand.pray.getBlockPos().getZ());
                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                passedData.writeBlockPos(this.getBlockPos());
                if(this.world.getRegistryKey() == World.OVERWORLD) {
                    // if it is, send pray position to client
                    ServerPlayNetworking.send((ServerPlayerEntity) (Object) this, ModMessages.SET_OVERWORLD_PREY_POS, passedData);
                } else if (this.world.getRegistryKey() == World.NETHER) {
                    // if it is, send pray position to client
                    ServerPlayNetworking.send((ServerPlayerEntity) (Object) this, ModMessages.SET_NETHER_PREY_POS, passedData);
                }
                else if (this.world.getRegistryKey() == World.END) {
                    // if it is, send pray position to client
                    ServerPlayNetworking.send((ServerPlayerEntity) (Object) this, ModMessages.SET_THEEND_PREY_POS, passedData);
                }

            }

        }

        if (hasAllayHelmet && allayRespawn) {
            //respawn allay
            if (allayRespawnTimer > 0) {
                allayRespawnTimer--;
                if (allayRespawnTimer <= 0) {
                    CorruptedAllayVexEntityGL corruptedAllayVexEntityGL = new CorruptedAllayVexEntityGL(EntityType.VEX, world);
                    corruptedAllayVexEntityGL.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0, 0);
                    world.spawnEntity(corruptedAllayVexEntityGL);
                    allayRespawnTimer = allayMaxRespawnTimer;
                }
            }
        }

        if (this.world.getRegistryKey().getValue().toString().equals("custommobswordsmod:natedim")) {
            if (NateDimensionTimer > 0) {
                NateDimensionTimer--;
            } else {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 100, 0, false, false, true));
                if(30 > world.getEntitiesByType(EntityType.PANDA, this.getBoundingBox().expand(15), (entity) -> true).size()){
                    for(int i = 0; i < 4; i++) {
                        PandaEntity panda = new PandaEntity(EntityType.PANDA, world);
                        double x = this.getPos().x + (Math.random() * 30) - 15;
                        double z = this.getPos().z + (Math.random() * 30) - 15;
                        float yawC = (float) (Math.random() * 360);
                        float pitchC = (float) ((Math.random() * 180) - 90);
                        panda.refreshPositionAndAngles(x, this.getPos().y, z, yawC, pitchC);
                        world.spawnEntity(panda);
                    }
                }
            }
        }

        if(!world.isClient()) {
            if(Math.random() < 0.0008f && CombustometerManager.players.contains(this.getUuid())){
                this.setOnFireFor(10);
            }
            /*
            if (CombustometerManager.getServerBossBar(this.getUuid()) != null && CombustometerManager.players.contains(this.getUuid())) {
                // get hunger manager
                HungerManager hungerManager = getHungerManager();
                hungerManager.getFoodLevel();
                if (hungerManager.getFoodLevel() == 20) {
                    sendMessage(Text.of("combustometerIncreasing: " + combustometerIncreasing + " combustometerBossBarProgress: " + combustometerBossBarProgress), false);
                    if (combustometerIncreasing && combustometerBossBarProgress <= 1) {
                        combustometerBossBarProgress += 0.02;
                        CombustometerManager.getServerBossBar(this.getUuid()).setPercent(combustometerBossBarProgress);
                        if (combustometerBossBarProgress >= 1) {
                            combustometerIncreasing = false;
                        }
                    } else if (!combustometerIncreasing && combustometerBossBarProgress >= 0) {
                        // set randomly on fire
                        Random random = new Random();
                        int randomInt = random.nextInt(2400);
                        if (randomInt < 3) {
                            // set on fire
                            this.setOnFireFor(5);
                        }
                        combustometerBossBarProgress -= 0.02;
                        CombustometerManager.getServerBossBar(this.getUuid()).setPercent(combustometerBossBarProgress);
                        if (combustometerBossBarProgress <= 0) {
                            combustometerIncreasing = true;
                        }
                    }
                }
            }*/
        }
        // check if chicken dimension every 20th tick

        ///execute in minecraft:overworld teleport ~ ~ ~





    }







// when player goes through portal



    void removeEnderZoglinEffect(){
        if(this.hasStatusEffect(ModEffects.ENDER_ZOGLIN)){
            this.removeStatusEffect(ModEffects.ENDER_ZOGLIN);
        }
    }




    @Inject(method = "dropItem", at = @At("HEAD"), cancellable = true)
    public ItemEntity dropItem(ItemStack stack, boolean retainOwnership, CallbackInfoReturnable ci) {
        if (stack.isEmpty()) {
            return null;
        } else {
            if (this.world.isClient) {
                this.swingHand(Hand.MAIN_HAND);
            }

            double d = this.getEyeY() - 0.30000001192092896;
            ItemEntity itemEntity = new ItemEntity(this.world, this.getX(), d, this.getZ(), stack);



            if(FireInfectedPlayers.isPlayerInList(this.inventory.player)){
                itemEntity.setPickupDelay(40);
                itemEntity.setOnFireFor(500);
                ((ItemEntityMixinExt)itemEntity).setThrownBy(this.inventory.player);
                //((ItemEntityMixinExt)itemEntity).setBurnTimer(500);
                if (retainOwnership) {
                    itemEntity.setThrower(this.getUuid());
                }
            }
            else {
                itemEntity.setPickupDelay(40);
                if (retainOwnership) {
                    itemEntity.setThrower(this.getUuid());
                }
            }
            //world.spawnEntity(itemEntity);

            boolean throwRandomly = false;

            float f;
            float g;
            if (throwRandomly) {
                f = this.random.nextFloat() * 0.5F;
                g = this.random.nextFloat() * 6.2831855F;
                itemEntity.setVelocity((double)(-MathHelper.sin(g) * f), 0.20000000298023224, (double)(MathHelper.cos(g) * f));
            } else {
                f = 0.3F;
                g = MathHelper.sin(this.getPitch() * 0.017453292F);
                float h = MathHelper.cos(this.getPitch() * 0.017453292F);
                float i = MathHelper.sin(this.getYaw() * 0.017453292F);
                float j = MathHelper.cos(this.getYaw() * 0.017453292F);
                float k = this.random.nextFloat() * 6.2831855F;
                float l = 0.02F * this.random.nextFloat();
                itemEntity.setVelocity((double)(-i * h * 0.3F) + Math.cos((double)k) * (double)l, (double)(-g * 0.3F + 0.1F + (this.random.nextFloat() - this.random.nextFloat()) * 0.1F), (double)(j * h * 0.3F) + Math.sin((double)k) * (double)l);
            }

            return itemEntity;
        }
    }




    // inject into eating method
    @Inject(method = "eatFood", at = @At("HEAD"), cancellable = true)
    void eatFood(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (!world.isClient()) {
            //if(this.getHungerManager().getFoodLevel() < 20) {
                if (stack.getItem() == ModItems.BURNT_APPLE ||
                        stack.getItem() == ModItems.BURNT_BREAD ||
                        stack.getItem() == ModItems.BURNT_GOLDEN_APPLE ||
                        stack.getItem() == ModItems.BURNT_CARROT ||
                        stack.getItem() == ModItems.BURNT_CHICKEN ||
                        stack.getItem() == ModItems.BURNT_COD ||
                        stack.getItem() == ModItems.BURNT_MUTTON ||
                        stack.getItem() == ModItems.BURNT_MELON ||
                        stack.getItem() == ModItems.BURNT_PORKCHOP ||
                        stack.getItem() == ModItems.BURNT_POTATO ||
                        stack.getItem() == ModItems.BURNT_RABBIT ||
                        stack.getItem() == ModItems.BURNT_SALMON ||
                        stack.getItem() == ModItems.BURNT_BEEF ||
                        stack.getItem() == ModItems.BURNT_STEW) {
                    this.addStatusEffect(new StatusEffectInstance(ModEffects.CARBONPOISONING, CarbonPoisoningEffectManager.maxticksLeft, 0));
                }
            //}
        }
    }
















    public void SummonShieldingShulker(){
        if(!this.world.isClient() && this.getShieldingShulkerEntity() == null) {

            ShieldingShulkerEntity shieldingShulkerEntity = new ShieldingShulkerEntity(EntityType.SHULKER, this.world);
            shieldingShulkerEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0, 0);
            shieldingShulkerEntity.updatePosition(this.getX(), this.getY(), this.getZ());
            this.world.spawnEntity(shieldingShulkerEntity);

            setShieldingShulkerEntity(shieldingShulkerEntity);
            shieldingShulkerEntity.owner = SetHunterCommand.pray;
        }
    }
/*
    @Override
    public void setShieldingShulkerEntity(ShieldingShulkerEntity shieldingShulkerEntity) {
        serverBossBar = new ServerBossBar(this.getDisplayName(), ServerBossBar.Color.BLUE, ServerBossBar.Style.PROGRESS);
        serverBossBar.addPlayer((ServerPlayerEntity) SetHunterCommand.pray);
        serverBossBar.setPercent(BeamProgress);
        // make name of the bar "" to hide it
        serverBossBar.setName(Text.of("Shield Progress"));
        this.shieldingShulkerEntity = shieldingShulkerEntity;
    }

    public ShieldingShulkerEntity getShieldingShulkerEntity() {
        return this.shieldingShulkerEntity;
    }

    @Inject(method = "damageShield", at = @At("HEAD"), cancellable = true)
    public void damageShield(float amount, CallbackInfo ci) {
        if (this.activeItemStack.isOf(ModItems.ABSORPTION_SHIELD_ITEM)) {
            if (!this.world.isClient && this.shieldingShulkerEntity != null) {
                this.addBeamProgress(amount);
            }
            if(this.world.isClient()){
                //add crit particles
                this.world.addParticle(ParticleTypes.GLOW, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
                this.world.addParticle(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
                this.world.addParticle(ParticleTypes.WITCH, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }
*/

    public void addBeamProgress(float beamProgress) {
        this.BeamProgress += beamProgress / 500;
        serverBossBar.setPercent(BeamProgress);
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(RestoreDeathCommand.speedruner != null && this.getName() == RestoreDeathCommand.speedruner.getName()){
            RestoreDeathCommand.saveState();
        }
        // if player's vehicle is frog king, ignore fall damage
        if(source == DamageSource.FALL && this.getVehicle() instanceof FrogKingEntity){
            cir.setReturnValue(false);
        }
    }



    //inject into onDeath
    @Inject(method = "onDeath", at = @At("HEAD"), cancellable = true)
    public void onDeath(DamageSource source, CallbackInfo ci) {
        // if player is set to summoner, deactivate summoner
        if(summonerEntityGL != null){
            summonerEntityGL.controllingPlayer = null;
            summonerEntityGL.discard();
            summonerEntityGL = null;
        }
    }



    // animation player shielding shulker
    /*public void onTrackedDataSet(TrackedData<?> data) {
        if (POSE.equals(data)) {
            EntityPose entityPose = this.getPose();
            if (entityPose == EntityPose.LONG_JUMPING) {
                this.longJumpingAnimationState.start(this.age);
            } else {
                this.longJumpingAnimationState.stop();
            }

            if (entityPose == EntityPose.CROAKING) {
                this.croakingAnimationState.start(this.age);
            } else {
                this.croakingAnimationState.stop();
            }

            if (entityPose == EntityPose.USING_TONGUE) {
                this.usingTongueAnimationState.start(this.age);
            } else {
                this.usingTongueAnimationState.stop();
            }
        }

        super.onTrackedDataSet(data);
    }*/

    
    public void addCombustomenter(){
        //combustometerBossBar = new ServerBossBar(this.getDisplayName(), ServerBossBar.Color.RED, ServerBossBar.Style.PROGRESS);
        if(!CombustometerManager.players.contains(this.getUuid())) {
            CombustometerManager.players.add(this.getUuid());
        }
        /*
        CombustometerManager.createServerBossBar(this.getUuid());
        ServerPlayerEntity player = null;
        for(PlayerEntity playerEntity : this.world.getPlayers()){
            if(playerEntity.getUuid() == this.getUuid()){
                player = (ServerPlayerEntity) playerEntity;
            }
        }
        if(player != null) {
            CombustometerManager.getServerBossBar(this.getUuid()).addPlayer(player);

            CombustometerManager.getServerBossBar(this.getUuid()).setPercent(combustometerBossBarProgress);
            CombustometerManager.getServerBossBar(this.getUuid()).setName(Text.of("combust-o-meter"));
        }*/
    }
    
    
    
    


    /* Using fabric mod development kit make a boss bar titled rage that has a value of rage variable. Start by declaring it in fabric */


}
