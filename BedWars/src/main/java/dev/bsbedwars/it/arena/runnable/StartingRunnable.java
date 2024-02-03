package dev.bsbedwars.it.arena.runnable;

import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.team.Team;
import dev.bsbedwars.it.team.TeamColor;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.GameFile;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class StartingRunnable extends BukkitRunnable {

    private final Arena arena;
    private int second;

    public StartingRunnable(Arena arena, int second){
        this.arena = arena;
        this.second = second;
    }

    @Override
    public void run() {

        arena.setStatus(Status.STARTING);


        if(!(arena.getPlayers().size() >= arena.getType().getMinPlayers())){
            arena.setStatus(Status.LOBBY);
            for (Player player : arena.getPlayers()){
                ChatUtils.sendMessage(player, arena.getMessageConfig(), "starting_cancelled", String.valueOf(second));
                ChatUtils.sendTitle(player, arena.getMessageConfig(), "starting_cancelled_title", String.valueOf(second));
            }
            cancel();
            return;
        }


        if(second < 0){
            // Create teams
            List<Team> teams = createTeams();
            arena.setTeams(teams);

            // SetUP teams
            for (Team team : arena.getTeams()) {
                // Teleport to spawn
                for (Player player : team.getPlayers()) {
                    player.teleport(team.getSpawnLocation());
                    ChatUtils.sendMessage(player, arena.getMessageConfig(), "started_private_msg");
                }
            }
            for (Player player : arena.getPlayers())
                player.setGameMode(GameMode.SURVIVAL);
            arena.setStatus(Status.GAME);
            cancel();
            return;
        }



        for (Player player : arena.getPlayers()){
            if(second == 10 || second <= 5)
                ChatUtils.sendTitle(player, arena.getMessageConfig(), "starting_title", String.valueOf(second));
            ChatUtils.sendMessage(player, arena.getMessageConfig(), "starting", String.valueOf(second));
        }


        second--;

    }

    private List<Team> createTeams() {
        List<Player> players = arena.getPlayers();
        Collections.shuffle(players);

        int playersPerTeam = arena.getType().getPlayerForTeam();
        int totalTeams = (int) Math.ceil(players.size() / playersPerTeam);

        List<Team> teamsMap = new ArrayList<>();
        for (int i = 0; i < totalTeams; i++) {
            TeamColor teamColor = TeamColor.values()[i % TeamColor.values().length];
            List<Player> teamPlayers = players.subList(i * playersPerTeam, (i + 1) * playersPerTeam);
            Team team = new Team(
                    arena,
                    teamColor,
                    teamPlayers
            );
            teamsMap.add(team);
        }

        return teamsMap;
    }

}
