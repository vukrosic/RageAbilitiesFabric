package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.networking.packet.S2C.SetPreyNetherPositionS2CPacket;
import net.vukrosic.custommobswordsmod.networking.packet.S2C.SetPreyOverworldPositionS2CPacket;
import net.vukrosic.custommobswordsmod.networking.packet.S2C.SetPreyTheEndPositionS2CPacket;
import net.vukrosic.custommobswordsmod.util.custom.CompassAnglePredicateProviderExt;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(CompassAnglePredicateProvider.class)
public abstract class CompassAnglePredicateProviderMixin implements UnclampedModelPredicateProvider, CompassAnglePredicateProviderExt {
    boolean trackingPrey = true;

    BlockPos netherPortalPos = null;

    BlockPos OverworldPreyPos = new BlockPos(0, 0, 0);
    BlockPos NetherPreyPos = new BlockPos(0, 0, 0);
    BlockPos EndPreyPos = new BlockPos(0, 0, 0);
    boolean spinningCrazily = false;


    public void setOverworldPreyPos(BlockPos pos){
        OverworldPreyPos = pos;
    }

    /**
     * @author
     * @reason
     */

    @Inject(method = "getAngle", at = @At("HEAD"), cancellable = true)
    private void getAngle(ItemStack stack, ClientWorld world, int seed, Entity entity, CallbackInfoReturnable<Float> cir) {
        if (SetHunterCommand.pray != null) {
            cir.setReturnValue((float) getPreyAngle(entity, new BlockPos(0, 0, 0)));
        }
    }
    /*
    @Overwrite
    private float getAngle(ItemStack stack, ClientWorld world, int seed, Entity entity) {
        if(SetHunterCommand.pray != null){
            return (float) getPreyAngle(entity, new BlockPos(0, 0, 0));
        }
        GlobalPos globalPos = this.compassTarget.getPos(world, stack, entity);
        long l = world.getTime();
        return !this.canPointTo(entity, globalPos) ? this.getAimlessAngle(seed, l) : this.getAngleTo(entity, l, globalPos.getPos());
    }*/


    /**
     * @author
     * @reason
     */
    /*
    @Overwrite
    private float getAngleTo(Entity entity, long time, BlockPos pos) {
        BlockPos pos1 = SetPreyPositionS2CPacket.getPos();
        if(pos1 != new BlockPos(0, 0, 0)){
            MinecraftClient.getInstance().player.sendMessage(Text.of("CompassAnglePredicateProviderMixin getAngle1 pos: " + entity.getPos().toString()), false);
            return (float)(Math.atan2(Vec3d.ofCenter(pos1).getZ() - entity.getZ(), Vec3d.ofCenter(pos1).getX() - entity.getX()) / 6.2831854820251465);
        }
        return 0;
    }
    */


    /**
     * @author
     * @reason
     */
    @Overwrite
    private double getAngleTo(Entity entity, BlockPos pos) {
        return getPreyAngle(entity, pos);
    }

/*
    @Inject(method = "canPointTo", at = @At("RETURN"), cancellable = true)
    private void canPointTo(Entity entity, GlobalPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (spinningCrazily) {
            cir.setReturnValue(false);
        }
        cir.setReturnValue(true);
    }*/

    /**
     * @author
     * @reason
     */
    /*
    @Overwrite
    private boolean canPointTo(Entity entity, GlobalPos pos) {
        if (spinningCrazily) {
            return false;
        }
        return true;
        //return pos != null && pos.getDimension() == entity.world.getRegistryKey() && !(pos.getPos().getSquaredDistance(entity.getPos()) < 9.999999747378752E-6);
    }*/

    /**
     * @author
     * @reason
     */
    @Overwrite
    private float getAimlessAngle(int seed, long time) {
        PlayerEntity entity = MinecraftClient.getInstance().player;
        BlockPos pos = new BlockPos(0, 0, 0);
        return ((float) getPreyAngle(entity, pos));
    }

    double getPreyAngle(Entity entity, BlockPos pos){
        if (spinningCrazily) {
            return Math.random();
        }

        // so here my friend it is pointing into me but middle of a block so it's sometiems 0 sometimes some angle, but then idk why it worked before

        /*
        MinecraftClient.getInstance().player.sendMessage(Text.of("NETHER: " + (MinecraftClient.getInstance().world.getRegistryKey() == World.NETHER)), false);
        BlockPos pos12 = SetPreyNetherPositionS2CPacket.getPrayNetherPos();
        MinecraftClient.getInstance().player.sendMessage(Text.of("angle: " + Math.atan2(Vec3d.ofCenter(pos12).getZ() - entity.getZ(), Vec3d.ofCenter(pos12).getX() - entity.getX()) / 6.2831854820251465), false);
        */
        if (MinecraftClient.getInstance().world.getRegistryKey() == World.OVERWORLD && !Objects.equals(SetPreyOverworldPositionS2CPacket.getPos(), new BlockPos(0, 0, 0))) {
            BlockPos pos1 = SetPreyOverworldPositionS2CPacket.getPos();
            //MinecraftClient.getInstance().player.sendMessage(Text.of("CompassAnglePredicateProviderMixin getAngleTo pos OVERWORLD: " + entity.getPos().toString()), false);
            return Math.atan2(Vec3d.ofCenter(pos1).getZ() - entity.getZ(), Vec3d.ofCenter(pos1).getX() - entity.getX()) / 6.2831854820251465;
        }
        else if (MinecraftClient.getInstance().world.getRegistryKey() == World.NETHER && !Objects.equals(SetPreyNetherPositionS2CPacket.getPrayNetherPos(), new BlockPos(0, 0, 0))) {
            BlockPos pos1 = SetPreyNetherPositionS2CPacket.getPrayNetherPos();
            //MinecraftClient.getInstance().player.sendMessage(Text.of("CompassAnglePredicateProviderMixin getAngleTo pos NETHER: " + entity.getPos().toString()), false);
            return Math.atan2(Vec3d.ofCenter(pos1).getZ() - entity.getZ(), Vec3d.ofCenter(pos1).getX() - entity.getX()) / 6.2831854820251465;
        }
        else if (MinecraftClient.getInstance().world.getRegistryKey() == World.END && !Objects.equals(SetPreyTheEndPositionS2CPacket.getPrayTheEndPos(), new BlockPos(0, 0, 0))) {
            BlockPos pos1 = SetPreyTheEndPositionS2CPacket.getPrayTheEndPos();
            //MinecraftClient.getInstance().player.sendMessage(Text.of("CompassAnglePredicateProviderMixin getAngleTo pos THEEND: " + entity.getPos().toString()), false);
            return Math.atan2(Vec3d.ofCenter(pos1).getZ() - entity.getZ(), Vec3d.ofCenter(pos1).getX() - entity.getX()) / 6.2831854820251465;
        }
        Vec3d vec3d = Vec3d.ofCenter(pos);
        return Math.atan2(vec3d.getZ() - entity.getZ(), vec3d.getX() - entity.getX()) / 6.2831854820251465;
    }

}
