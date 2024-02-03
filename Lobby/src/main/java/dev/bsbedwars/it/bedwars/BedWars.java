package dev.bsbedwars.it.bedwars;

import dev.bsbedwars.it.Lobby;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

@Getter

public class BedWars {


    private final String name;
    @Setter
    private Type type;
    @Setter
    private Status status;
    private int players;


    public BedWars(String name, Type type, Status status) {
        this.name = name;
        this.type = type;
        this.status = status;
        startRunnable();
    }

    public void startRunnable()  {
        Bukkit.getScheduler().runTaskTimer(Lobby.getInstance(), this::updatePlayers, 0L, 5L);
    }

    public void updatePlayers() {
        if(!Bukkit.getOnlinePlayers().isEmpty())
            Lobby.getInstance().getCommon().getBungeeApi().getPlayerCount(name).whenComplete((count, error) -> {
                players = count;
            });

    }

}
