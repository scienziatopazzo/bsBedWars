package dev.bsbedwars.it;

import co.aikar.commands.BukkitCommandManager;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.PluginStatus;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.commands.bedwars.BedWarsCommand;
import dev.bsbedwars.it.commands.help.HelpCommand;
import dev.bsbedwars.it.commands.start.StartCommand;
import dev.bsbedwars.it.event.imp.*;
import dev.bsbedwars.it.event.reg.BedDestroyEvent;
import dev.bsbedwars.it.event.reg.BedWarsJoinEvent;
import dev.bsbedwars.it.event.reg.BedWarsKillEvent;
import dev.bsbedwars.it.event.reg.BedWarsQuitEvent;
import dev.bsbedwars.it.jedis.JedisChannel;
import dev.bsbedwars.it.lobby.LobbyManager;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.GameFile;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public final class BedWars extends JavaPlugin {

    @Getter
    private static BedWars instance;

    private Common common;
    private LobbyManager lobbyManager;
    private String bedwarsUUID;
    private JedisChannel channel;
    private Arena arena;



    @Override
    public void onEnable() {
        long currentTimeMillis = System.currentTimeMillis();

        instance = this;
        this.common = new Common(this);
        loadConfig();
        this.bedwarsUUID = new GameFile("config.yml").getFileConfiguration().getString("BedWarsBungeeCordID");
        this.lobbyManager = new LobbyManager();
        this.channel = new JedisChannel();
        Bukkit.getScheduler().runTaskAsynchronously(this, this::startListening);

        channel.updatePluginStatus(PluginStatus.ENABLE);
        channel.updateStatus(Status.LOBBY);
        channel.updateType(Type.SOLO);

        /*
        common.getJedisManager()
                .send("defChannel", bedwarsUUID + ";" + "PLUGINSTATUS " + ";" + "ENABLE");
        common.getJedisManager()
                .send("defChannel", bedwarsUUID + ";" + "STATUS" + ";" + "LOBBY");
        common.getJedisManager()
                .send("defChannel", bedwarsUUID + ";" + "TYPE" + ";" + "SOLO");
         */


        loadCommand();
        loadEvent();

        this.arena = new Arena();

        Bukkit.getConsoleSender().sendMessage(ChatUtils.color(ChatUtils.prefix() + "Plugin enabled in &b" + (System.currentTimeMillis() - currentTimeMillis) + "&ams"));


    }

    @Override
    public void onDisable() {
        common.getJedisManager()
                .send("defChannel", bedwarsUUID + ";" + "PLUGINSTATUS " + ";" + "DISABLE");
        common.getJedisManager().close();
    }


    private void startListening() {
        common.getJedisManager().register(channel, "defChannel", bedwarsUUID.toString());
    }


    public void loadConfig() {
        new GameFile("config.yml");
        new GameFile("messages.yml");
        new GameFile("component/teams.yml");
    }

    public void loadCommand() {
        BukkitCommandManager commandManager = new BukkitCommandManager(this);
        commandManager.registerCommand(new HelpCommand());
        commandManager.registerCommand(new BedWarsCommand());
        commandManager.registerCommand(new StartCommand());
    }

    public void loadEvent() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new BedWarsJoinEvent(), this);
        pluginManager.registerEvents(new BedWarsJoinEventImp(), this);
        pluginManager.registerEvents(new BedDestroyEvent(), this);
        pluginManager.registerEvents(new BedDestroyEventImp(), this);
        pluginManager.registerEvents(new BedWarsKillEvent(), this);
        pluginManager.registerEvents(new BedWarsKillEventImp(), this);
        pluginManager.registerEvents(new BedWarsWinEventImp(), this);
        pluginManager.registerEvents(new BedWarsQuitEvent(), this);
        pluginManager.registerEvents(new BedWarsQuitEventImp(), this);
    }


}
