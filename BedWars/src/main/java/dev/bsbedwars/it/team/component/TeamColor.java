package dev.bsbedwars.it.team.component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

@RequiredArgsConstructor
@Getter
public enum TeamColor {

    RED("&c", Color.RED, (short) 14, DyeColor.RED),
    BLUE("&9", Color.BLUE, (short) 11, DyeColor.BLUE),
    GREEN("&a", Color.GREEN, (short) 5, DyeColor.GREEN),
    YELLOW("&e", Color.YELLOW, (short) 3, DyeColor.YELLOW),
    CYAN("&3", Color.AQUA, (short) 3, DyeColor.CYAN),
    WHITE("&f", Color.WHITE, (short) 0, DyeColor.WHITE),
    PINK("&d", Color.PURPLE, (short) 6, DyeColor.PINK),
    GRAY("&8", Color.GRAY, (short) 7, DyeColor.GRAY);


    private final String colorCode;
    private final Color color;
    private final short woolColor;
    private final DyeColor dyeColor;

}
