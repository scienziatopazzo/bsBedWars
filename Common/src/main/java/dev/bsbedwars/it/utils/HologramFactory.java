package dev.bsbedwars.it.utils;

import dev.bsbedwars.it.Common;
import dev.bsbedwars.it.hologram.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HologramFactory {

    public static final List<Hologram> HOLOGRAMS_CACHE = new ArrayList<>();

    private String name;
    private Location location;
    private List<String> lines;

    public HologramFactory setName(String name) {
        this.name = name;
        return this;
    }

    public HologramFactory setLocation(Location location) {
        this.location = location;
        return this;
    }

    public HologramFactory setLines(String... lines) {
        this.lines = Arrays.asList(lines);
        return this;
    }

    public Hologram buildHologram() {
        if(location == null || lines == null || lines.isEmpty() || name == null)
            throw new IllegalArgumentException("Location, lines and name must not be null");

        return createHologram(location, lines, name);
    }




    public static Hologram createHologram(Location location, List<String> lines, String name) {
        return new Hologram(location, lines, name, Common.getInstance().getMain());
    }



}
