package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.entity.custom.chunken.HunterGoldEggEntity;
import net.vukrosic.custommobswordsmod.entity.custom.frogking.HunterFrogKingEggEntity;
import net.vukrosic.custommobswordsmod.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ItemDispenserBehavior.class)
public abstract class ItemDispenserBehaviourMixin {


    @Inject(method = "spawnItem", at = @At("HEAD"), cancellable = true)
    private static void spawnItem(World world, ItemStack stack, int speed, Direction side, Position pos, CallbackInfo ci) {
        if(Objects.equals(stack.getItem().toString(), "hunter_frog_king_egg_item") && SetHunterCommand.pray != null) {
            HunterFrogKingEggEntity hunterEggEntity = new HunterFrogKingEggEntity(world, SetHunterCommand.pray);
            double g = world.random.nextDouble() * 0.1 + 0.2;
            //hunterEggEntity.setVelocity(world.random.nextTriangular((double) side.getOffsetX() * g * 4, 0.0172275 * (double) speed), world.random.nextTriangular(0.2, 0.0172275 * (double) speed), world.random.nextTriangular((double) side.getOffsetZ() * g * 4, 0.0172275 * (double) speed));
            hunterEggEntity.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), world.random.nextFloat() * 360.0F, 0.0F);
            Vec3f velocity = side.getUnitVector();
            hunterEggEntity.setVelocity(velocity.getX() * 1.5f, 0, velocity.getZ() * 1.5f);
            world.spawnEntity(hunterEggEntity);
            ci.cancel();
        }
        else if(Objects.equals(stack.getItem().toString(), "hunter_gold_egg_item") && SetHunterCommand.pray != null){
            HunterGoldEggEntity hunterEggEntity = new HunterGoldEggEntity(world, SetHunterCommand.pray);
            double g = world.random.nextDouble() * 0.1 + 0.2;
            hunterEggEntity.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), world.random.nextFloat() * 360.0F, 0.0F);
            // get dispenser rotation
            Vec3f velocity = side.getUnitVector();
            hunterEggEntity.setVelocity(velocity.getX() * 1.5f, 0, velocity.getZ() * 1.5f);
            //hunterEggEntity.setVelocity(world.random.nextTriangular((double) side.getOffsetX() * g * 4, 0.0172275 * (double) speed), world.random.nextTriangular(0.2, 0.0172275 * (double) speed), world.random.nextTriangular((double) side.getOffsetZ() * g * 4, 0.0172275 * (double) speed));
            world.spawnEntity(hunterEggEntity);
            ci.cancel();
        }
    }


}
