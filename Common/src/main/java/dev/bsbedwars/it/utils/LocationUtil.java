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
        config.set(patch, location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch());
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


    public Location toCenterLocation(Location original) {
        Location copy = original.clone();
        copy.setX(original.getBlockX() + 0.5);
        copy.setY(original.getBlockY() + 0.5);
        copy.setZ(original.getBlockZ() + 0.5);
        return copy;
    }

}
