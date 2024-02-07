package dev.bsbedwars.it;

import co.aikar.commands.BukkitCommandManager;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.PluginStatus;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.commands.bedwars.BedWarsCommand;
import dev.bsbedwars.it.commands.bedwars.subcommand.setup.SetUPListener;
import dev.bsbedwars.it.commands.fake.FakeOFFCommand;
import dev.bsbedwars.it.commands.fake.RedoONCommand;
import dev.bsbedwars.it.commands.fly.FlyCommand;
import dev.bsbedwars.it.commands.gamemode.GMC;
import dev.bsbedwars.it.commands.gamemode.GMS;
import dev.bsbedwars.it.commands.gamemode.GMSP;
import dev.bsbedwars.it.commands.help.HelpCommand;
import dev.bsbedwars.it.commands.leave.LeaveCommand;
import dev.bsbedwars.it.commands.start.StartCommand;
import dev.bsbedwars.it.commands.stop.StopCommand;
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


        loadCommand();
        loadEvent();

        this.arena = new Arena();

        Bukkit.getConsoleSender().sendMessage(ChatUtils.color(ChatUtils.prefix() + "Plugin enabled in &b" + (System.currentTimeMillis() - currentTimeMillis) + "&ams"));

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
                .send("defChannel", bedwarsUUID + ";" + "PLUGINSTATUS " + ";" + "DISABLE");
        common.getJedisManager().close();
    }


    private void startListening() {
        common.getJedisManager().register(channel, "defChannel", bedwarsUUID);
    }


    public void loadConfig() {
        new GameFile("config.yml");
        new GameFile("messages.yml");
        new GameFile("component/teams.yml");
        new GameFile("component/generators.yml");
        new GameFile("component/lobby.yml");
    }

    public void loadCommand() {
        BukkitCommandManager commandManager = new BukkitCommandManager(this);
        commandManager.registerCommand(new HelpCommand());
        commandManager.registerCommand(new BedWarsCommand());
        commandManager.registerCommand(new StartCommand());
        commandManager.registerCommand(new StopCommand());
        commandManager.registerCommand(new FakeOFFCommand());
        commandManager.registerCommand(new RedoONCommand());
        commandManager.registerCommand(new GMC());
        commandManager.registerCommand(new GMS());
        commandManager.registerCommand(new GMSP());
        commandManager.registerCommand(new FlyCommand());
        commandManager.registerCommand(new LeaveCommand());
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
        pluginManager.registerEvents(new SetUPListener(), this);
    }


}
