package dev.bsbedwars.it.shop.content.items.special.tower.component.coord;

import java.util.ArrayList;
import java.util.List;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.shop.content.items.special.tower.component.runnable.BlocksPlaced;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TowerBuild {
    private BukkitTask task;

    public TowerBuild(Location loc, Block chest, Player p, TowerStatus.Type status) {
        List<String> relloc = new ArrayList<>();
        switch (status) {
            case NORTH:
                relloc.addAll(TowerStatus.getRellocNord());
            case SOUTH:
                relloc.addAll(TowerStatus.getRellocSud());
            case EST:
                relloc.addAll(TowerStatus.getRellocEst());
            case WEST:
                relloc.addAll(TowerStatus.getRellocOvest());
        }
        int[] i = { 0 };
        this.task = new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getServer().getClass().getPackage().getName().contains("v1_8")) {
                    loc.getWorld().playSound(loc, Sound.valueOf("CHICKEN_EGG_POP"), 1.0F, 0.5F);
                } else {
                    loc.getWorld().playSound(loc, Sound.valueOf("ENTITY_CHICKEN_EGG"), 1.0F, 0.5F);
                }
                if(i[0] >= 109) {
                    task.cancel();
                    return;
                }
                if (relloc.size() + 1 == i[0] + 1) {
                    task.cancel();
                    return;
                }
                String c1 = relloc.get(i[0]);
                if (c1.contains("ladder")) {
                    int ldata = Integer.parseInt(c1.split("ladder")[1]);
                    new BlocksPlaced(chest, c1, p, true, ldata);
                } else {
                    new BlocksPlaced(chest, c1, p, false, 0);
                }
                if (relloc.size() + 1 == i[0] + 2) {
                    task.cancel();
                    return;
                }
                String c2 = relloc.get(i[0] + 1);
                if (c2.contains("ladder")) {
                    int ldata = Integer.parseInt(c2.split("ladder")[1]);
                    new BlocksPlaced(chest, c2, p, true, ldata);
                } else {
                    new BlocksPlaced(chest, c2, p, false, 0);
                }
                i[0] = i[0] + 2;
            }
        }.runTaskTimer(BedWars.getInstance(), 0L, 1L);
    }
}