package dev.bsbedwars.it.hologram;

import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractHologram {

    public Location location;
    public List<String> lines;
    public final JavaPlugin plugin;
    public final String name;
    public FileConfiguration config;

    public AbstractHologram(Location location, List<String> lines, String name, JavaPlugin plugin) {
        this.location = location;
        this.lines = lines;
        this.plugin = plugin;
        this.name = name;
    }

    public abstract void create();
    public abstract void delete();
    public abstract void addLine(int line, String content);
    public abstract void addLine(String content);
    public abstract void removeLine(int line);
    public abstract void change(int line, String content);
    public abstract void change(List<String> lines);
    public abstract void move(Location newLocation);






}
