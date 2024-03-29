package dev.bsbedwars.it.arena.component;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.scoreboard.PlayerTAB;
import dev.bsbedwars.it.team.Team;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ArenaTAB {

    public static void update() {
        try {
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
                List<String> lines1 = ChatUtils.replace(BedWars.getInstance().getArena().getConfigFile().getFileConfiguration().getStringList("tab.header"), placeholders);
                tab.updateHeader(lines1);
                List<String> lines2 = ChatUtils.replace(BedWars.getInstance().getArena().getConfigFile().getFileConfiguration().getStringList("tab.footer"), placeholders);
                tab.updateFooter(lines2);
                Arena arena = BedWars.getInstance().getArena();
                if(arena.getStatus() == Status.ENDING || arena.getStatus() == Status.GAME) {
                    Team team = arena.getTeam(player);
                    tab.updateUseCustomDisplayName(true);
                    if(team == null) {
                        tab.updateCustomDisplayName(ChatUtils.color(BedWars.getInstance().getArena().getConfigFile().getFileConfiguration().getString("tab.spectator")));
                    }else {
                        tab.updateCustomDisplayName(ChatUtils.color(BedWars.getInstance().getArena().getConfigFile().getFileConfiguration().getString("tab.game").replace("{Team_color}", team.getColor().getColorCode()).replace("{Team_name}", ChatUtils.makeLowercaseExceptFirst(team.getColor().toString()))));
                    }
                } else {
                    tab.updateUseCustomDisplayName(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
