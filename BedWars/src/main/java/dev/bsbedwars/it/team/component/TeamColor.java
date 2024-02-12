package dev.bsbedwars.it.team.component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Color;

@RequiredArgsConstructor
@Getter
public enum TeamColor {

    RED("&c", Color.RED, (short) 14),
    BLUE("&9", Color.BLUE, (short) 11),
    GREEN("&a", Color.GREEN, (short) 5),
    YELLOW("&e", Color.YELLOW, (short) 3),
    CYAN("&3", Color.AQUA, (short) 3),
    WHITE("&f", Color.WHITE, (short) 0),
    PINK("&d", Color.PURPLE, (short) 6),
    GRAY("&8", Color.GRAY, (short) 7);


    private final String colorCode;
    private final Color color;
    private final short woolColor;

}
