package dev.bsbedwars.it.event.imp;

import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.arena.runnable.ScoreboardRunnable;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.event.reg.BedWarsQuitEvent;
import dev.bsbedwars.it.scoreboard.PlayerScoreboard;
import dev.bsbedwars.it.team.Team;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class BedWarsQuitEventImp implements Listener {


    @EventHandler
    public void onQuit(BedWarsQuitEvent e){
        Arena arena = e.getArena();
        Player player = e.getPlayer();

        PlayerScoreboard.getPlayers().remove(player);



        if(arena.getStatus() == Status.LOBBY || arena.getStatus() == Status.STARTING) {
            HashMap<String, String> placeholder = new HashMap<>();
            placeholder.put("player", player.getName());
            placeholder.put("players", String.valueOf(arena.getPlayers().size()));
            placeholder.put("maxPlayers", String.valueOf(arena.getType().getMaxPlayers()));
            arena.getPlayers().remove(player);
            for (Player playerInArena : arena.getPlayers())
                if(playerInArena != player) {
                    ChatUtils.sendMessage(playerInArena, arena.getMessageConfig(), "quit_msg", placeholder);
                }
            return;
        }


        Team team = arena.getTeam(player);

        if(team == null) {
            arena.getSpectators().remove(player);
            return;
        }

        team.getPlayers().remove(player);
        arena.getPlayers().remove(player);
        if(team.isEmpty())
            arena.getTeams().remove(team);

        HashMap<String, String> placeholder = new HashMap<>();
        placeholder.put("player", player.getName());
        placeholder.put("team_color_name", team.getColor().toString());
        placeholder.put("team_color_code", team.getColor().getColorCode());
        for (Player playerInArena : arena.getPlayers())
            if(playerInArena != player)
                ChatUtils.sendMessage(playerInArena, arena.getMessageConfig(), "quit", placeholder);

        arena.checkWin();


    }


}
