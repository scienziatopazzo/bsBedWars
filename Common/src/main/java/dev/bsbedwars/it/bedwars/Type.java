package dev.bsbedwars.it.bedwars;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Getter
public enum Type {


    SOLO("SOLO", 1, 2),
    DUO("DUO", 2, 2),
    TRIO("TRIO", 3, 3),
    SQUAD("SQUAD", 4, 4);

    private final String name;
    private final int playerForTeam;
    private final int maxTeams;
    private final int minPlayers;
    private final int maxPlayers;


    Type(String name, int playerForTeam, int maxTeams) {
        this.name = name;
        this.playerForTeam = playerForTeam;
        this.maxTeams = maxTeams;
        this.minPlayers = playerForTeam * 2;
        this.maxPlayers = playerForTeam * maxTeams;
    }

}
