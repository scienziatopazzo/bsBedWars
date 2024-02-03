package dev.bsbedwars.it.team;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TeamColor {

    RED("&c"),
    GREEN("&a");

    private final String colorCode;

}
