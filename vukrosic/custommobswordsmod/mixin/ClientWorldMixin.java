package net.vukrosic.custommobswordsmod.mixin;

import net.vukrosic.custommobswordsmod.util.TickCounter;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin( ClientWorld.class )
public abstract class ClientWorldMixin extends World {


    protected ClientWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> dimension, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void countTicks(CallbackInfo info){
        TickCounter.incrementTickCounter();
        TickCounter.calledEveryTick();
        if(Math.random() < 0.05){
            System.out.println("ClientWorldMixin TickCounter: " + TickCounter.getTickCounter());
        }
    }
/*
    public static int getTickCounter() {
        return tickCounter;
    }*/
}
