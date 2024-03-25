package dev.bsbedwars.it.scoreboard;

import dev.bsbedwars.it.utils.ChatUtils;
import fr.mrmicky.fastboard.FastBoard;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
public class PlayerScoreboard {

    @Getter
    private static final HashMap<Player, PlayerScoreboard> players = new HashMap<>();


    private final Player player;
    private final FastBoard scoreboard;

    public PlayerScoreboard(Player player) {
        this.player = player;
        this.scoreboard = new FastBoard(player);
        players.put(player, this);
    }


    public void update(List<String> lines) {
        scoreboard.updateLines(ChatUtils.color(lines));
    }

    public void update(String... lines) {
        scoreboard.updateLines(ChatUtils.color(lines));
    }


    public void updateTitle(String title) {
        scoreboard.updateTitle(ChatUtils.color(title));
    }


    public static PlayerScoreboard getOrCreate(Player player) {
        return players.getOrDefault(player, new PlayerScoreboard(player));
    }

    public void delete() {
        scoreboard.delete();
        players.remove(player);
    }

}
