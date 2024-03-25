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

public class TabRunnable extends BukkitRunnable {

    @Override
    public void run() {

        for (int i = 0; i < Bukkit.getOnlinePlayers().size(); i++) {
            Player player = Bukkit.getOnlinePlayers().toArray(new Player[0])[i];
            PlayerTAB tab = PlayerTAB.getOrCreate(player);
            HashMap<String, String> placeholders = new HashMap<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String date = dateFormat.format(new Date());
            placeholders.put("date", date);
            placeholders.put("online", String.valueOf(Bukkit.getOnlinePlayers().size()));
            placeholders.put("player", player.getName());
            placeholders.put("level", "Soon");
            placeholders.put("clan", "Soon");
            placeholders.put("progress", "Soon");
            List<String> lines1 = ChatUtils.replace(Lobby.getInstance().getConfigFile().getFileConfiguration().getStringList("lobby.tab.header"), placeholders);
            tab.updateHeader(lines1);
            List<String> lines2 = ChatUtils.replace(Lobby.getInstance().getConfigFile().getFileConfiguration().getStringList("lobby.tab.footer"), placeholders);
            tab.updateFooter(lines2);
        }

    }
}
