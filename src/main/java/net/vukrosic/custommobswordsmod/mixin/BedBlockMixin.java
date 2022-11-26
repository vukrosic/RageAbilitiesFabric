package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BedBlock.class)
public abstract class BedBlockMixin extends HorizontalFacingBlock implements BlockEntityProvider{

    protected BedBlockMixin(Settings settings) {
        super(settings);
    }

    @Shadow
    public static boolean isBedWorking(World world) {
        return false;
    }

    // inject in onUse
    @Inject(at = @At("HEAD"), method = "onUse", cancellable = true)
    public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if(SetHunterCommand.pray != null){
            // explosion radius
            float radius = 5f;
            PlayerEntity pray = SetHunterCommand.pray;
            pray.sendMessage(Text.of("YOU GOT THE BED ABILITY"), false);
            double distance = SetHunterCommand.pray.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ());
            if (distance < radius * radius) {
                ((PlayerEntityExt)pray).setBedAbilityActive(true);
            }
        }

    }
}

