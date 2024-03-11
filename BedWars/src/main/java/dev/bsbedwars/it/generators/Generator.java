package dev.bsbedwars.it.generators;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.generators.runnable.GeneratorRunnable;
import dev.bsbedwars.it.hologram.Hologram;
import dev.bsbedwars.it.utils.HologramFactory;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class Generator {

    private final GeneratorType type;
    private final Location location;
    private Hologram hologram;
    private BukkitTask task;
    @Setter
    private int level;

    public Generator(Location location, GeneratorType type, int level) {
        this.location = location;
        this.type = type;
        // For level
        this.level = level;
        if(type != GeneratorType.BASE) {
            this.hologram = HologramFactory.createHologram(location.clone().add(0, 1, 0), Arrays.asList("&bSeconds left: &c", "&b" + type.toString() + " &8(" + level + ")"), UUID.randomUUID().toString());
            hologram.create();
        }
    }

    public void start() {
        CompletableFuture.runAsync(() -> {
            task = Bukkit.getScheduler().runTaskTimer(BedWars.getInstance(), new GeneratorRunnable(this), 0L, 20L);
        });
    }

    public void stop() {
        if(task != null)
            task.cancel();
        if(hologram != null)
            hologram.delete();
    }

}
