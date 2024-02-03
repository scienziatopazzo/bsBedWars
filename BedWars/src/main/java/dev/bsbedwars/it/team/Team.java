package dev.bsbedwars.it.team;

import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.utils.Cuboid;
import dev.bsbedwars.it.utils.LocationUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Team {

    private final Arena arena;
    private final TeamColor color;
    private final Location spawnLocation;
    private final Cuboid teamCuboid;
    private final Location bedLocation;
    private final List<Player> players;
    @Setter
    private boolean bedAlive;

    public Team(Arena arena, TeamColor color, List<Player> players) {
        this.arena = arena;
        this.color = color;
        this.players = players;
        FileConfiguration config = arena.getTeamsFile().getFileConfiguration();
        this.spawnLocation = new LocationUtil(null).deserialize(config.getString(color.toString() + ".spawn"));
        this.bedLocation = new LocationUtil(null).deserialize(config.getString(color.toString() + ".bed"));
        this.teamCuboid = Cuboid.createCube(spawnLocation, 20);
        this.bedAlive = true;
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

}
