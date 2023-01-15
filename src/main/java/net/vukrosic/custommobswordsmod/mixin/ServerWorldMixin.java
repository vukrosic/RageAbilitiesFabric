package net.vukrosic.custommobswordsmod.mixin;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
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
import net.vukrosic.custommobswordsmod.util.TickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World implements StructureWorldAccess {

    public ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> dimension, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        TickCounter.calledEveryTick();
        if(SetHunterCommand.pray == null){
            if(SetHunterCommand.prayUuid != null){
                SetHunterCommand.pray = getPlayerByUuid(SetHunterCommand.prayUuid);
            }
        }

        //if(SetHunterCommand.hunters.size() == 0){
        setHuntersFromUuids();
        //}
        //sendHunterUuidsToClients();

    }

    private void setHuntersFromUuids() {
        if(SetHunterCommand.huntersUUIDs.size() != 0){
            // get a list of players
            SetHunterCommand.hunters = (ArrayList<PlayerEntity>) this.getPlayers().stream().filter(player -> SetHunterCommand.huntersUUIDs.contains(player.getUuid())).collect(Collectors.toList());

            for(UUID uuid : SetHunterCommand.huntersUUIDs){
                PlayerEntity player = getPlayerByUuid(uuid);
                if(player != null){
                    if(!SetHunterCommand.hunters.contains(player)){
                        SetHunterCommand.hunters.add(player);
                    }
                }
            }
        }
    }
/*
    private void sendHunterUuidsToClients() {
        if(SetHunterCommand.huntersUUIDs.size() != 0){
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            for(UUID uuid : SetHunterCommand.huntersUUIDs){
                buf.writeUuid(uuid);
            }
            for(PlayerEntity player : this.getPlayers()){
                ServerPlayNetworking.send((ServerPlayerEntity)player, ModMessages.HUNTER_UUIDS, buf);
            }
        }
    }*/

    private static void setPrerAndHunters() {

    }
}
