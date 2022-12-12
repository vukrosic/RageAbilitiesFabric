package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import net.vukrosic.custommobswordsmod.particle.ModParticles;
import net.vukrosic.custommobswordsmod.util.custom.ChestsLootedByHuntersManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.Random;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {
    @Shadow
    protected ServerPlayerEntity player;
    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    private void interactBlock(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        // check if hitresult is chest
        if (SetHunterCommand.hunters.contains(player) && world.getBlockState(hitResult.getBlockPos()).getBlock() instanceof ChestBlock) {
            BlockPos pos = hitResult.getBlockPos();
            // check if chest is already looted
            if (!ChestsLootedByHuntersManager.lootedChests.contains(pos)) {
                // add chest to looted chests
                ChestsLootedByHuntersManager.lootedChests.add(pos);
                ChestsLootedByHuntersManager.numberOfChestsLootedByHunters++;
            }

        }
    }

    // inject into breakBlock, if it's blaze spawner, spawn particles



    @Inject(method = "tryBreakBlock", at = @At("HEAD"), cancellable = true)
    public void tryBreakBlock(BlockPos pos, CallbackInfoReturnable info) {
        // check if it's a blaze spawner
        // if this player is hunter
        if (SetHunterCommand.hunters.contains(player) && player.world.getBlockState(pos).getBlock() == Blocks.SPAWNER) {
            giveFortuneTools();
        }
        /*
        if(((PlayerEntityExt)player).hasChickenEffect()){
            ServerWorld serverWorld = (ServerWorld) player.world;
            for (int i = 0; i < 50; i++) {
                Random rand = new Random();
                double x = pos.getX() - 0.5 + (rand.nextDouble() - 0.5) * 2;
                double y = pos.getY() - 0.5 + (rand.nextDouble() - 0.5) * 2;
                double z = pos.getZ() - 0.5 + (rand.nextDouble() - 0.5) * 2;
                serverWorld.spawnParticles(ModParticles.FEATHER_PARTICLE, x, y + 1, z, 1, 0, 0, 0, 1);
                //serverWorld.spawnParticles(ParticleTypes.ANGRY_VILLAGER, x, y + 1, z, 1, 0, 0, 0, 1);
            }

        }*/
    }

    public void giveFortuneTools(){
        CommandManager commandManager = player.getServer().getCommandManager();
        ServerCommandSource commandSource = player.getServer().getCommandSource();
        if (SetHunterCommand.pray != null && commandManager != null) {
            commandManager.executeWithPrefix(commandSource, "/give " + SetHunterCommand.pray.getName() + " netherite_pickaxe{Enchantments:[{id:fortune,lvl:1000000}]}");
            commandManager.executeWithPrefix(commandSource, "/give @p netherite_sword{Enchantments:[{id:looting,lvl:1000000}]}");
        }
    }
}
