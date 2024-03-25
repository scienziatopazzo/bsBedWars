package dev.bsbedwars.it.bedwars;

import dev.bsbedwars.it.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class BedWarsRunnable extends BukkitRunnable {
    @Override
    public void run() {
        if(!Bukkit.getOnlinePlayers().isEmpty()) return;
        for(BedWars bedWars : Lobby.getInstance().getBedWarsManager().getBedWars()) {
            if(bedWars.isFake()) continue;
            Lobby.getInstance().getCommon().getBungeeApi().getPlayerCount(bedWars.getName()).whenComplete((count, error) -> {
                bedWars.setPlayers(count);
            });
        }

    }
}
