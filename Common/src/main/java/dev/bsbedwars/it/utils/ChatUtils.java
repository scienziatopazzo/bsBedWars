package dev.bsbedwars.it.utils;

import dev.bsbedwars.it.Common;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChatUtils {

    // String color(String)
    // List<String> color (List<String>)
    // List<String> color (String...)

    public static String prefix() { return Common.getInstance().getPrefix(); }

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }


    public static List<String> color(List<String> list) {
        return list.stream()
                .map(s -> ChatColor.translateAlternateColorCodes('&', s))
                .collect(Collectors.toList());
    }

    public static List<String> color(String... list) {
        return Arrays.asList(list).stream()
                .map(s -> ChatColor.translateAlternateColorCodes('&', s))
                .collect(Collectors.toList());
    }

    public static void sendMessage(Player player, FileConfiguration config, String patch, String... args) {
        String message = config.getString(patch);
        for (int i = 0; i < args.length; i++) {
            message = message.replace("{" + i + "}", args[i]);
        }
        player.sendMessage(color(message));
    }

    public static void sendTitle(Player player, FileConfiguration config, String patch, String... args) {
        String message = config.getString(patch);

        if (message.contains("[title]") && message.contains("[subtitle]")) {
            String[] parts = message.split("\\[subtitle\\]");
            String title = parts[0].replace("[title]", "");
            String subtitle = parts[1].replace("[subtitle]", "");

            for (int i = 0; i < args.length; i++) {
                title = title.replace("{" + i + "}", args[i]);
                subtitle = subtitle.replace("{" + i + "}", args[i]);
            }

            player.sendTitle(color(title), color(subtitle));
        } else {
            for (int i = 0; i < args.length; i++) {
                message = message.replace("{" + i + "}", args[i]);
            }

            player.sendTitle(color(message), "");
        }
    }







}
