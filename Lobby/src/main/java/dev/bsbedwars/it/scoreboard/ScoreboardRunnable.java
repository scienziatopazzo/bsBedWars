package dev.bsbedwars.it.scoreboard;

import dev.bsbedwars.it.Lobby;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ScoreboardRunnable extends BukkitRunnable {

    @Override
    public void run() {

        for(Player player : Bukkit.getOnlinePlayers()) {
            PlayerScoreboard scoreboard = PlayerScoreboard.getOrCreate(player);
            scoreboard.updateTitle(ChatUtils.color(Lobby.getInstance().getConfigFile().getFileConfiguration().getString("lobby.scoreboard.title")));
            HashMap<String, String> placeholders = new HashMap<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String date = dateFormat.format(new Date());
            placeholders.put("date", date);
            placeholders.put("online", String.valueOf(Bukkit.getOnlinePlayers().size()));
            placeholders.put("player", player.getName());
            placeholders.put("level", "Soon");
            placeholders.put("clan", "Soon");
            placeholders.put("progress", "Soon");
            List<String> lines = ChatUtils.replace(Lobby.getInstance().getConfigFile().getFileConfiguration().getStringList("lobby.scoreboard.lines"), placeholders);
            scoreboard.update(lines);
        }

    }

}
