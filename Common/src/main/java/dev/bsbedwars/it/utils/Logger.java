package dev.bsbedwars.it.utils;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.List;

public class Logger {



    public void log(boolean prefix, String... messages) {
        log(prefix, Arrays.asList(messages));
    }

    public void log(boolean prefix, List<String> messages) {
        for (String message : messages)
            Bukkit.getConsoleSender().sendMessage(ChatUtils.color(prefix ? ChatUtils.prefix() + " " + message : message));
    }

}
