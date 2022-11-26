package net.vukrosic.custommobswordsmod.entity.custom.chunken;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.vukrosic.custommobswordsmod.CustomMobSwordsMod;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.item.ModItems;

import java.util.ArrayList;

public class ChunkenPhaseManager {


    public static PlayerEntity eatenPlayer = null;
    public static Vec3d eatenPlayerPos = null;

    public static Identifier defaultEatenPlayer = new Identifier(CustomMobSwordsMod.MOD_ID, "textures/entity/chicken_robot_phase_5_gold.png");
    public static int chunkenPhase = 0;
    public static int hitsPerPhase = 6;

    public static float scalePerPhase = 1;

    static ArrayList<Identifier> textures = new ArrayList<Identifier>();

    static ArrayList<String> idleAnimation = new ArrayList<>();
    static ArrayList<String> walkAnimation = new ArrayList<>();
    static ArrayList<String> attackAnimation = new ArrayList<>();

    public static PlayerEntity chickenEffectPlayer = null;
    public static PlayerEntity chickenDimensionPlayer = null;


    public static void resetChunkenPhase() {
        chunkenPhase = 0;
    }

    public static void set4Phase(){
        chunkenPhase = 4;
        if(eatenPlayer != null){
            MinecraftServer server = eatenPlayer.getServer();
            assert server != null;
            CommandManager commandManager = server.getPlayerManager().getServer().getCommandManager();
            String xStr = String.valueOf(eatenPlayer.getX() + 8);
            String yStr = String.valueOf(eatenPlayer.getY());
            String zStr = String.valueOf(eatenPlayer.getZ());
            //commandManager.executeWithPrefix(eatenPlayer.getCommandSource(), "/execute in minecraft:overworld run teleport " + eatenPlayer.getName().getString() + " " + xStr + " " + yStr + " " + zStr);
            String command = "/execute in minecraft:overworld run teleport " + eatenPlayer.getName().getString() + " " + xStr + " " + yStr + " " + zStr;
            commandManager.executeWithPrefix(eatenPlayer.getCommandSource(), command.toString());
        }

    }




    public static void createChunkenPhases(){
        textures.add(new Identifier(CustomMobSwordsMod.MOD_ID, "textures/entity/chicken_robot_phase_1.png"));
        textures.add(new Identifier(CustomMobSwordsMod.MOD_ID, "textures/entity/chicken_robot_phase_2.png"));
        textures.add(new Identifier(CustomMobSwordsMod.MOD_ID, "textures/entity/chicken_robot_phase_3.png"));
        textures.add(new Identifier(CustomMobSwordsMod.MOD_ID, "textures/entity/chicken_robot_phase_5_nate.png"));
        textures.add(new Identifier(CustomMobSwordsMod.MOD_ID, "textures/entity/chicken_robot_phase_5_nate.png"));


        idleAnimation.add("animation.geometry.chicken.phase1_idle");
        walkAnimation.add("animation.geometry.chicken.phase1_walk");
        attackAnimation.add("animation.geometry.chicken.phase1_attack");

        idleAnimation.add("animation.geometry.chicken.phase2_idle");
        walkAnimation.add("animation.geometry.chicken.phase2_walk");
        attackAnimation.add("animation.geometry.chicken.phase2_attack");

        idleAnimation.add("animation.geometry.chicken.phase3_idle");
        walkAnimation.add("animation.geometry.chicken.phase3_walk");
        attackAnimation.add("animation.geometry.chicken.phase3_attack");

        idleAnimation.add("animation.geometry.chicken.phase4_idle");
        walkAnimation.add("animation.geometry.chicken.phase4_walk");
        attackAnimation.add("animation.geometry.chicken.phase4_attack");

        idleAnimation.add("animation.geometry.chicken.phase5_idle");
        walkAnimation.add("animation.geometry.chicken.phase5_walk");
        attackAnimation.add("animation.geometry.chicken.phase5_attack");

    }

    public static String getIdleAnimation(){
        return idleAnimation.get(chunkenPhase);
    }

    public static String getWalkAnimation(){
        return walkAnimation.get(chunkenPhase);
    }

    public static String getAttackAnimation(){
        return attackAnimation.get(chunkenPhase);
    }

    public static Identifier getChunkenTexture(){
        if(MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().world != null)
            //MinecraftClient.getInstance().player.sendMessage(Text.of("ChunkenPhaseManager getChunkenTexture chunkenPhase: " + chunkenPhase));
        if(eatenPlayer != null && chunkenPhase >= 3){
            return get4Texture(eatenPlayer);
        }
        return textures.get(chunkenPhase);
    }

    public static Identifier get4Texture(Entity player) {
        if(player.getName().getString() == "Goldactual") {
            return textures.set(4, new Identifier(CustomMobSwordsMod.MOD_ID, "textures/entity/chicken_robot_phase_5_gold.png"));
        } else if (player.getName().getString() == "Lewwolfe") {
            return textures.set(4, new Identifier(CustomMobSwordsMod.MOD_ID, "textures/entity/chicken_robot_phase_5_lew.png"));
        } else {
            return textures.set(4, new Identifier(CustomMobSwordsMod.MOD_ID, "textures/entity/chicken_robot_phase_5_nate.png"));
        }
    }

/*
    public static void poopEgg(Vec3d pos, World world){
        // spawn item
        ItemEntity itemEntity = new ItemEntity(world, pos.x, pos.y, pos.z, new ItemStack(ModItems.HUNTER_GOLD_EGG_ITEM));
        itemEntity.refreshPositionAndAngles(pos.x, pos.y, pos.z, 0.0F, 0.0F);
        world.spawnEntity(itemEntity);
    }*/
}
