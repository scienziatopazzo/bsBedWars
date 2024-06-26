package dev.bsbedwars.it.arena;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.runnable.StartingRunnable;
import dev.bsbedwars.it.arena.runnable.WinRunnable;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.event.reg.BedWarsWinEvent;
import dev.bsbedwars.it.generators.Generator;
import dev.bsbedwars.it.npc.NPCManager;
import dev.bsbedwars.it.team.Team;
import dev.bsbedwars.it.team.component.TeamColor;
import dev.bsbedwars.it.utils.GameFile;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class Arena implements Cloneable{

    private final String uuid;
    private Type type;
    private Status status;
    private final GameFile configFile;
    private final GameFile messageFile;
    private final GameFile teamsFile;
    private final GameFile generatorsFile;
    private final GameFile lobbyFile;
    private final GameFile villagersFile;
    private final GameFile shopFile;
    private final FileConfiguration config;
    private final FileConfiguration messageConfig;
    private final List<Block> blockPlaced = new ArrayList<>();
    private final List<Player> players;
    private final List<Player> spectators;
    private final List<Generator> generators;
    @Setter
    private List<Team> teams;
    @Setter
    private StartingRunnable startingRunnable;


    public Arena() {
        this.uuid = BedWars.getInstance().getBedwarsUUID();
        this.configFile = new GameFile("config.yml");
        this.messageFile = new GameFile("messages.yml");
        this.shopFile = new GameFile("shop.yml");
        this.teamsFile = new GameFile("component/teams.yml");
        this.generatorsFile = new GameFile("component/generators.yml");
        this.lobbyFile = new GameFile("component/lobby.yml");
        this.villagersFile = new GameFile("component/villagers.yml");
        this.generators = new ArrayList<>();
        this.config = configFile.getFileConfiguration();
        this.messageConfig = messageFile.getFileConfiguration();
        this.type = config.getString("Type") == null ? Type.SOLO : Type.valueOf(config.getString("Type").toUpperCase());
        this.status = config.getString("Status") == null ? Status.LOBBY : Status.valueOf(config.getString("Status").toUpperCase());
        this.players = new ArrayList<>();
        this.spectators = new ArrayList<>();
    }


    public void start() {

        if(status != Status.LOBBY)
            return;

        if(players.size() < type.getMinPlayers())
            return;

        this.startingRunnable = new StartingRunnable(this, config.getInt("Starting_time_seconds"));
        startingRunnable.runTaskTimer(BedWars.getInstance(), 0L, 20L);
    }

    public void stop() {
        if(status == Status.STARTING || status == Status.LOBBY)
            return;
        setStatus(Status.LOBBY);
        // Kick all players
        List<String> lobbys = new ArrayList<>(BedWars.getInstance().getLobbyManager().getLobbys());
        Collections.shuffle(lobbys);
        for (Player player : players)
            BedWars.getInstance().getCommon().getBungeeApi().connect(player, lobbys.stream().findFirst().orElse(null));
        players.clear();
        for (Player player : spectators)
            BedWars.getInstance().getCommon().getBungeeApi().connect(player, lobbys.stream().findFirst().orElse(null));
        spectators.clear();
        // Stop generators
        for(Team team : teams)
            team.getGenerator().stop();
        teams.clear();
        for (Generator generator : generators)
            generator.stop();
        generators.clear();
        // Replace Block
        blockPlaced.forEach(block -> block.setType(Material.AIR));
        blockPlaced.clear();
        // Kill All entity
        Bukkit.getWorld("world").getEntities().forEach(Entity::remove);
        NPCManager.removeALL();
    }

    public Team getTeam(Player player) {
        return teams.stream()
                .filter(team -> team.getAlivePlayers().contains(player))
                .findFirst()
                .orElse(null);
    }


    public void checkWin() {
        if (teams.size() != 1)
            return;

        Team winningTeam = teams.stream().findFirst().orElse(null);

        Bukkit.getPluginManager().callEvent(new BedWarsWinEvent(this, winningTeam));
        new WinRunnable(this, config.getInt("Win_time_seconds")).runTaskTimer(BedWars.getInstance(), 0L, 20L);
    }


    public Team getTeamByColor(TeamColor color) {
        return teams.stream().filter(team -> team.getColor() == color).findFirst().orElse(null);
    }


    public void setType(Type newType) {
        BedWars.getInstance().getChannel()
                .updateType(newType);
        this.type = newType;
    }

    public void setStatus(Status newStatus) {
        BedWars.getInstance().getChannel()
                .updateStatus(newStatus);
        this.status = newStatus;
    }


    @Override
    public Arena clone() {
        try {
            Arena clone = (Arena) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
