package dev.bsbedwars.it.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.NumberConversions;

@RequiredArgsConstructor
@Getter
public class LocationUtil {


    private final Location location;


    public void set(FileConfiguration config, String patch) {
        config.set(patch, location.getWorld().getName() + "," + (int) location.getX() + "," + (int) location.getY() + "," + (int) location.getZ() + "," + (int) location.getYaw() + "," + (int) location.getPitch());
    }

    public Location deserialize(String location) {
        String[] split = location.split(",");

        return new Location(
                Bukkit.getWorld(split[0]),
                NumberConversions.toDouble(split[1]),
                NumberConversions.toDouble(split[2]),
                NumberConversions.toDouble(split[3]),
                NumberConversions.toFloat(split[4]),
                NumberConversions.toFloat(split[5])
        );
    }

}
