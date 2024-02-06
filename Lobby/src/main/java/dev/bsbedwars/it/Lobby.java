package dev.bsbedwars.it;

import co.aikar.commands.BukkitCommandManager;
import dev.bsbedwars.it.bedwars.BedWars;
import dev.bsbedwars.it.bedwars.BedWarsManager;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.commands.join.JoinCommand;
import dev.bsbedwars.it.commands.join.runnable.BedWarsGUIReload;
import dev.bsbedwars.it.commands.spawn.SpawnCommand;
import dev.bsbedwars.it.jedis.JedisChannel;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.GameFile;
import dev.bsbedwars.it.utils.Logger;
import lombok.Getter;
import org.bukkit.Bukkit;
import dev.bsbedwars.it.commands.help.HelpCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;
import java.util.UUID;

@Getter
public final class Lobby extends JavaPlugin {

    @Getter
    private static Lobby instance;

    private Common common;
    private GameFile spawnFile;
    private GameFile configFile;
    private BedWarsManager bedWarsManager;
    private String lobbyUUID;

    @Override
    public void onEnable() {
        long currentTimeMillis = System.currentTimeMillis();

        instance = this;
        this.common = new Common(this);

        // Config files
        this.spawnFile = new GameFile("other/spawn.yml");
        this.configFile = new GameFile("config.yml");
        this.lobbyUUID = configFile.getFileConfiguration().getString("LobbyBungeeCordID");

        this.bedWarsManager = new BedWarsManager();

        Bukkit.getScheduler().runTaskAsynchronously(this, this::startListening);


        BukkitCommandManager commandManager = new BukkitCommandManager(this);

        commandManager.registerCommand(new HelpCommand());
        commandManager.registerCommand(new SpawnCommand());
        commandManager.registerCommand(new JoinCommand());

        common.getJedisManager()
                .send("defChannel", lobbyUUID + ";" + "ENABLE");



        common.getLogger()
                .log(true, "Plugin enabled in &b" + (System.currentTimeMillis() - currentTimeMillis) + "&ams");


        new BedWarsGUIReload().runTaskTimer(this, 0L, 5L);


        // Set Gamerules
        Bukkit.getWorld("world").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("world").setGameRuleValue("doWeatherCycle", "false");
        Bukkit.getWorld("world").setGameRuleValue("doMobSpawning", "false");
        Bukkit.getWorld("world").setGameRuleValue("doFireTick", "false");
        Bukkit.getWorld("world").setGameRuleValue("mobGriefing", "false");

    }

    @Override
    public void onDisable() {
        common.getJedisManager()
                .send("defChannel", lobbyUUID + ";" + "DISABLE");
        common.getJedisManager().close();

    }

    private void startListening() {
        JedisChannel channel = new JedisChannel();
        common.getJedisManager().register(channel, "defChannel", lobbyUUID);
    }

}
