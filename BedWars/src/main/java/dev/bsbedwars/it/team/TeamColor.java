package dev.bsbedwars.it.team;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TeamColor {

    RED("&c"),
    BLUE("&9"),
    GREEN("&a"),
    YELLOW("&e"),
    CYAN("&3"),
    WHITE("&f"),
    PINK("&d"),
    GRAY("&8");


    private final String colorCode;

}
