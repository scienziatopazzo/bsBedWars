package dev.bsbedwars.it.team.component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Color;

@RequiredArgsConstructor
@Getter
public enum TeamColor {

    RED("&c", Color.RED),
    BLUE("&9", Color.BLUE),
    GREEN("&a", Color.GREEN),
    YELLOW("&e", Color.YELLOW),
    CYAN("&3", Color.AQUA),
    WHITE("&f", Color.WHITE),
    PINK("&d", Color.PURPLE),
    GRAY("&8", Color.GRAY);


    private final String colorCode;
    private final Color color;

}
