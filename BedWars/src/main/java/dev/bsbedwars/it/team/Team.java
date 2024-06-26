package dev.bsbedwars.it.team;

import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.generators.Generator;
import dev.bsbedwars.it.generators.GeneratorType;
import dev.bsbedwars.it.team.component.TeamColor;
import dev.bsbedwars.it.team.component.TeamUpgrade;
import dev.bsbedwars.it.utils.Cuboid;
import dev.bsbedwars.it.utils.LocationUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Bed;
import org.bukkit.material.Dye;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Team {

    private final Arena arena;
    private final TeamColor color;
    private final Location spawnLocation;
    private final Cuboid teamCuboid;
    private final Location bedLocation;
    private final Location generatorLocation;
    private final List<Player> players;
    private final List<Player> playersDeath;
    private final TeamUpgrade teamUpgrade;
    private final Generator generator;
    @Setter
    private boolean bedAlive;

    public Team(Arena arena, TeamColor color, List<Player> players) {
        this.arena = arena;
        this.color = color;
        this.players = players;
        this.playersDeath = new ArrayList<>();
        FileConfiguration config = arena.getTeamsFile().getFileConfiguration();
        this.spawnLocation = new LocationUtil(null).deserialize(config.getString(color.toString() + ".spawn"));
        this.bedLocation = new LocationUtil(null).deserialize(config.getString(color.toString() + ".bed"));
        for(Block block : LocationUtil.placeBed(config.getString(color.toString() + ".bed")))
            arena.getBlockPlaced().add(block);
        this.generatorLocation = new LocationUtil(null).deserialize(config.getString(color.toString() + ".generator"));
        this.generator = new Generator(generatorLocation, GeneratorType.BASE, 0); // base level is 0
        generator.start();
        this.teamCuboid = Cuboid.createCube(spawnLocation, 35);
        this.teamUpgrade = new TeamUpgrade();
        setUPTeamUpgrade();
        this.bedAlive = true;
    }

    public List<Player> getAlivePlayers() {
        return players.stream().filter(player -> !playersDeath.contains(player)).collect(Collectors.toList());
    }

    public void death(Player player) {
        playersDeath.add(player);
    }

    public boolean isEmpty() {
        return getAlivePlayers().isEmpty();
    }

    private void setUPTeamUpgrade() {
        teamUpgrade.addUpgrade("generator", 0);
        teamUpgrade.addUpgrade("protection", 0);
        teamUpgrade.addUpgrade("sharpness", 0);
        teamUpgrade.addUpgrade("mine", 0);
        teamUpgrade.addUpgrade("trap", false);
        teamUpgrade.addUpgrade("pool", false);

    }

    public void updateGeneratorLvL(int level) {
        teamUpgrade.addUpgrade("generator", level);
        generator.setLevel(level);
    }

    public short getWoolID() {
        return color.getWoolColor();
    }

    public DyeColor getDyeColor() {
        return color.getDyeColor();
    }



}
