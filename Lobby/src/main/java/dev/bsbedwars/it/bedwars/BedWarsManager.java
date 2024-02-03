package dev.bsbedwars.it.bedwars;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public class BedWarsManager {


    private final Set<BedWars> bedWars = new HashSet<>();


}
