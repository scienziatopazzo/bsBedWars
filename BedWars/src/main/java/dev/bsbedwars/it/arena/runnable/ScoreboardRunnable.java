package dev.bsbedwars.it.arena.runnable;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.scoreboard.PlayerScoreboard;
import dev.bsbedwars.it.team.Team;
import dev.bsbedwars.it.team.component.TeamColor;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ScoreboardRunnable extends BukkitRunnable {


    @Override
    public void run() {

        Arena arena = BedWars.getInstance().getArena();
        try {
            for(Player player : Bukkit.getOnlinePlayers()) {
                PlayerScoreboard scoreboard = PlayerScoreboard.getOrCreate(player);
                if(arena.getPlayers().contains(player)) {

                    if(arena.getStatus() == Status.GAME || arena.getStatus() == Status.ENDING) {
                        game(scoreboard, arena);
                    }
                    if(arena.getStatus() == Status.LOBBY) {
                        lobby(scoreboard, arena);
                    }
                    if(arena.getStatus() == Status.STARTING) {
                        starting(scoreboard, arena);
                    }

                }
                // todo: spectator scoreboard
            }
        } catch (Exception e) {
            return;
        }


    }


    private void game(PlayerScoreboard scoreboard, Arena arena) {
        Player player = scoreboard.getPlayer();
        Type type = arena.getType();

        HashMap<String, String> placeholders = new HashMap<>();
        for (TeamColor value : TeamColor.values()) {
            Team arenaTeam = arena.getTeamByColor(value);

            String colorCode = value.getColorCode();
            String teamName = ChatUtils.makeLowercaseExceptFirst(value.toString());
            String status = arenaTeam != null ? ((arenaTeam.isBedAlive() ? "&a✔ " : "&c✖ ") + (arenaTeam.getPlayers().contains(player) ? "&8YOU" : (!arenaTeam.isBedAlive() ? "&8" + arenaTeam.getPlayers().size() : ""))) : "&c✖";
            String mode = String.valueOf(BedWars.getInstance().getArena().getType());


            placeholders.put(value + "_Color", colorCode);
            placeholders.put(value + "_Name", teamName);
            placeholders.put(value + "_Status", status);
            placeholders.put(value + "_Mode", mode);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(new Date());
        placeholders.put("date", date);
        placeholders.put("server_ip", arena.getConfig().getString("serverIP"));
        placeholders.put("player", player.getName());
        placeholders.put("mode", arena.getType().toString());

        List<String> scoreboardLines = ChatUtils.replace(
                arena.getConfig().getStringList("scoreboard.inGame." + type.toString()),
                placeholders
        );
        scoreboard.update(scoreboardLines);
        scoreboard.updateTitle(arena.getConfig().getString("scoreboard.inGame.title"));
    }

    private void lobby(PlayerScoreboard scoreboard, Arena arena) {
        Player player = scoreboard.getPlayer();
        HashMap<String, String> placeholders = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(new Date());
        placeholders.put("date", date);
        placeholders.put("players", String.valueOf(arena.getPlayers().size()));
        placeholders.put("maxPlayers", String.valueOf(arena.getType().getMaxPlayers()));
        placeholders.put("Map", String.valueOf(arena.getUuid()));
        placeholders.put("player", player.getName());
        placeholders.put("server_ip", arena.getConfig().getString("serverIP"));
        placeholders.put("mode", arena.getType().toString());
        List<String> scoreboardLines = ChatUtils.replace(
                arena.getConfig().getStringList("scoreboard.lobby.lines"),
                placeholders
        );
        scoreboard.update(scoreboardLines);
        scoreboard.updateTitle(arena.getConfig().getString("scoreboard.starting.title"));
    }

    private void starting(PlayerScoreboard scoreboard, Arena arena) {
        Player player = scoreboard.getPlayer();
        HashMap<String, String> placeholders = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(new Date());
        placeholders.put("date", date);
        placeholders.put("startingCooldown", String.valueOf(arena.getStartingRunnable().getSecond()));
        placeholders.put("players", String.valueOf(arena.getPlayers().size()));
        placeholders.put("maxPlayers", String.valueOf(arena.getType().getMaxPlayers()));
        placeholders.put("Map", String.valueOf(arena.getUuid()));
        placeholders.put("player", player.getName());
        placeholders.put("mode", arena.getType().toString());
        placeholders.put("server_ip", arena.getConfig().getString("serverIP"));
        List<String> scoreboardLines = ChatUtils.replace(
                arena.getConfig().getStringList("scoreboard.starting.lines"),
                placeholders
        );
        scoreboard.update(scoreboardLines);
        scoreboard.updateTitle(arena.getConfig().getString("scoreboard.starting.title"));
    }
}
