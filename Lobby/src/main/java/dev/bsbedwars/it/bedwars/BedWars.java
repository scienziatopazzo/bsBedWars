package dev.bsbedwars.it.bedwars;

import dev.bsbedwars.it.Lobby;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

@Getter

public class BedWars {


    private final String name;
    private final boolean fake;
    @Setter
    private Type type;
    @Setter
    private Status status;
    @Setter
    private int players;


    public BedWars(String name, Type type, Status status, boolean fake) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.fake = fake;
    }

    public BedWars(String name, Type type, Status status) {
        this.name = name;
        this.fake = false;
        this.type = type;
        this.status = status;
    }
}
