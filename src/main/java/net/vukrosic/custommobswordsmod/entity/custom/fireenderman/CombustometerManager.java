package net.vukrosic.custommobswordsmod.entity.custom.fireenderman;

import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.vukrosic.custommobswordsmod.entity.custom.PlayerEntityExt;

import java.util.ArrayList;
import java.util.UUID;

public class CombustometerManager {

    public static ArrayList <UUID> players = new ArrayList<>();
    //public static ArrayList <ServerBossBar> serverBossBars = new ArrayList<>();

/*
    public static void createServerBossBar(UUID uuid) {
        int index = players.indexOf(uuid);
        ServerBossBar combustometerBossBar = new ServerBossBar(Text.of("combust-o-meter"), ServerBossBar.Color.RED, ServerBossBar.Style.PROGRESS);
        serverBossBars.add(index, combustometerBossBar);
    }

    public static ServerBossBar getServerBossBar(UUID uuid) {
        int index = players.indexOf(uuid);
        return serverBossBars.get(index);
    }*/
}
