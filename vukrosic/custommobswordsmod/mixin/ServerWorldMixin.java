package net.vukrosic.custommobswordsmod.mixin;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.vukrosic.custommobswordsmod.command.SetHunterCommand;
import net.vukrosic.custommobswordsmod.networking.ModMessages;
import net.vukrosic.custommobswordsmod.util.ThrowingAnimationManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World implements StructureWorldAccess {

    public ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> dimension, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if(SetHunterCommand.pray == null){
            if(SetHunterCommand.prayUuid != null){
                SetHunterCommand.pray = getPlayerByUuid(SetHunterCommand.prayUuid);
            }
        }

        if(SetHunterCommand.hunters.size() == 0){
            if(SetHunterCommand.huntersUUIDs.size() != 0){
                for(UUID uuid : SetHunterCommand.huntersUUIDs){
                    SetHunterCommand.hunters.add(getPlayerByUuid(uuid));
                }
            }
        }
        // create new buffer new PacketByteBuf(Unpooled.buffer())
        PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());
        buffer.writeBoolean(false);
        if(SetHunterCommand.pray != null && ThrowingAnimationManager.throwingPlayer != null) {
            buffer.writeBoolean(true);
            buffer.writeUuid(ThrowingAnimationManager.throwingPlayer.getUuid());
            ServerPlayNetworking.send((ServerPlayerEntity)SetHunterCommand.pray, ModMessages.EVERY_TICK, buffer);
        }

    }

    private static void setPrerAndHunters() {

    }
}
