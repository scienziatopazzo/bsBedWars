package dev.bsbedwars.it.utils;

import dev.bsbedwars.it.Common;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

@Getter
public class GameFile {


    private final FileConfiguration fileConfiguration;
    private final File file;


    public GameFile(String name) {

        JavaPlugin main = Common.getInstance().getMain();

        File configFile = new File(main.getDataFolder(), name);
        this.file = configFile;


        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            main.saveResource(name, false);
        }

        FileConfiguration configuration = new YamlConfiguration();

        try {
            configuration.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        fileConfiguration = configuration;
    }

    public void reload() {
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}