package dev.bsbedwars.it.lobby;

import dev.bsbedwars.it.BedWars;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class LobbyManager {


    private final Set<String> lobbys = new HashSet<>();

}
