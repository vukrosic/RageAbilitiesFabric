package net.vukrosic.custommobswordsmod.util.abilities;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.networking.ModMessages;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

public class PlayerAbilities {
    public static boolean bigBlocksTest = false;
    public static int bigBlocksTimer = 0;
    public static int AbilityTier = 0;
    public static boolean blockEntitiesScaled = false;
    public static int blockEntitiesScaledTimer = 20;

    public static boolean preyScaled = false;
    public static boolean isFlyingEnabled = false;
    // make an array of picked entities
    public static ArrayList<LivingEntity> pickedEntities = new ArrayList<LivingEntity>();
    public static boolean ActiveAbility = false;

    public static void levelUp() {
        AbilityTier++;
        // clear all status effects
        if(SetHunterCommand.pray != null){
            SetHunterCommand.pray.clearStatusEffects();
            SetHunterCommand.pray.sendMessage(Text.of("Speedrunner's ability level is now " + AbilityTier), true);
        }
    }

    public static void tick() {

        // test
        if(Math.random() < 0.2){
            if(SetHunterCommand.pray != null)
            {/*
                ServerCommandSource commandSource = SetHunterCommand.pray.getCommandSource();
                CommandManager commandManager = Objects.requireNonNull(SetHunterCommand.pray.getServer()).getCommandManager();
                if (commandManager != null) {
                    commandManager.executeWithPrefix(commandSource, "/scale set 2");
                    PlayerAbilities.preyScaled = true;
                }*/
/*
                ItemEntity closestItemEntity = null;
                double closestDistance = 100;
                for(ItemEntity itemEntity : SetHunterCommand.pray.world.getEntitiesByClass(ItemEntity.class, SetHunterCommand.pray.getBoundingBox().expand(100), entity -> entity.getPos().distanceTo(SetHunterCommand.pray.getPos()) <= 50)){
                    if(itemEntity.getPos().distanceTo(SetHunterCommand.pray.getPos()) < closestDistance){
                        closestDistance = itemEntity.getPos().distanceTo(SetHunterCommand.pray.getPos());
                        closestItemEntity = itemEntity;
                        SetHunterCommand.pray.sendMessage(Text.of("Closest item entity is " + closestItemEntity.getUuidAsString()), true);
                        break;
                    }
                }*/
                /*
                bigBlocksTest = !bigBlocksTest;
                SetHunterCommand.pray.sendMessage(Text.of("PlayerAbilities isClient, bigBlocksTest = " + SetHunterCommand.pray.world.isClient() + " " + bigBlocksTest), true);
                PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());
                buffer.writeBoolean(bigBlocksTest);
                if(!SetHunterCommand.pray.world.isClient()){
                    ServerPlayNetworking.send((ServerPlayerEntity)SetHunterCommand.pray, ModMessages.TEST_ITEM_ENLARGEMENT, new PacketByteBuf(new PacketByteBuf(null)));
                }
*/
            }

        }
        if(blockEntitiesScaled){
            blockEntitiesScaledTimer--;
            if(blockEntitiesScaledTimer <= 0){
                blockEntitiesScaled = false;
                blockEntitiesScaledTimer = 20;
                /*
                for(ItemEntity itemEntity : SetHunterCommand.pray.world.getEntitiesByClass(ItemEntity.class, SetHunterCommand.pray.getBoundingBox().expand(10), itemEntity -> true)){
                    itemEntity.discard();
                }*/
            }
        }
        /*
        if(SetHunterCommand.pray != null) {
            ItemEntity itemEntity1 = new ItemEntity(SetHunterCommand.pray.world, SetHunterCommand.pray.getX(), SetHunterCommand.pray.getY(), SetHunterCommand.pray.getZ(), Items.EMERALD_BLOCK.getDefaultStack());
            itemEntity1.refreshPositionAndAngles(SetHunterCommand.pray.getX(), SetHunterCommand.pray.getY(), SetHunterCommand.pray.getZ(), 0, 0);
            SetHunterCommand.pray.world.spawnEntity(itemEntity1);
        }*/
        PlayerAbilityTier0.tick();
        PlayerAbilityTier1.tick();
        PlayerAbilityTier2.tick();
        PlayerAbilityTier3.tick();
        PlayerAbilityTier4.tick();
        if(SetHunterCommand.pray != null && pickedEntities.size() > 0) {
            //set position to above the player for each pickedEntity
            for(LivingEntity entity : pickedEntities){
                // set entities above the pray but not at the same place, so they don't stack, use index to offset them
                int index = pickedEntities.indexOf(entity);
                double normalized = (index / pickedEntities.size()) / 2;
                float yOffset = 2.5f;
                if(preyScaled){
                    yOffset = 4.8f;
                }
                PlayerEntity player = entity.world.getPlayerByUuid(SetHunterCommand.pray.getUuid());
                if(player != null)
                    entity.setPos(player.getX() + normalized, player.getY() + normalized + yOffset, player.getZ() + normalized);
            }
        }
    }

    public static void onPreyTakeDamage(DamageSource damageSource1, float float1, PlayerEntity playerEntity) {
        if(damageSource1.getAttacker() != null && damageSource1.getAttacker() instanceof PlayerEntity){
            switch (AbilityTier){
                case 0:
                    PlayerAbilityTier0.onPreyTakeDamage(damageSource1, float1, playerEntity);
                    break;
                case 1:
                    PlayerAbilityTier1.dropHunterInventoryWhenTheyDamagePrey(damageSource1, float1, playerEntity);
                    break;
                case 2:
                    PlayerAbilityTier2.dropHunterInventoryWhenTheyDamagePrey(damageSource1, float1, playerEntity);
                    break;
                case 3:
                    PlayerAbilityTier3.dropHunterInventoryWhenTheyDamagePrey(damageSource1, float1, playerEntity);
                    break;
            }
        }
    }
}
