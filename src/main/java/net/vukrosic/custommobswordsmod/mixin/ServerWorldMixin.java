package net.vukrosic.custommobswordsmod.mixin;

import net.minecraft.entity.player.PlayerEntity;
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
    }

    private static void setPrerAndHunters() {

    }
}
