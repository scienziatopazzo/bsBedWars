package dev.bsbedwars.it.arena.runnable;

import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.team.Team;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WinRunnable extends BukkitRunnable {

    private final Arena arena;
    private int seconds;

    public WinRunnable(Arena arena, int seconds){
        this.arena = arena;
        this.seconds = seconds;
    }


    @Override
    public void run() {
        // team.getPlayers().removeIf(player -> !player.isOnline()); get ConcurrentModificationException error :(


        if(seconds < 0) {
            arena.stop();
            cancel();
            return;
        }



        seconds--;

    }
}
