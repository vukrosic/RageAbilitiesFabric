package net.vukrosic.custommobswordsmod.util;



import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.vukrosic.custommobswordsmod.command.*;
import net.vukrosic.custommobswordsmod.entity.ModEntities;
import net.vukrosic.custommobswordsmod.entity.custom.*;

public class ModRegistries {

    public static void registerModStuffs(){
        registerAttributes();
        CommandRegistrationCallback.EVENT.register(SetHunterCommand::register);
        CommandRegistrationCallback.EVENT.register(RestoreDeathCommand::register);
        CommandRegistrationCallback.EVENT.register(AbilitiesCommand::register);
    }

    private static void registerAttributes(){
        //FabricDefaultAttributeRegistry.register(ModEntities.ENDERZOGLIN, EnderZoglinEntity.setAttributes());
    }


}
