package dev.bsbedwars.it.event.imp;

import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.event.reg.BedDestroyEvent;
import dev.bsbedwars.it.team.Team;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class BedDestroyEventImp implements Listener {


    @EventHandler
    public void onBedDestroy(BedDestroyEvent e){


        Arena arena = e.getArena();
        Player player = e.getPlayer();
        Team team = e.getTeam();
        Team teamVictim = e.getTeamVictim();

        HashMap<String, String> placeholder = new HashMap<>();
        placeholder.put("player", player.getName());
        placeholder.put("player_team_color_code", team.getColor().getColorCode());
        placeholder.put("player_team_color_name", team.getColor().toString());
        placeholder.put("victim_team_color_code", teamVictim.getColor().getColorCode());
        placeholder.put("victim_team_color_name", teamVictim.getColor().toString());
        // public msg
        for (Player playerInArena : arena.getPlayers())
            ChatUtils.sendMessage(playerInArena, arena.getMessageConfig(), "bed_destroyed", placeholder);

        // Private msg
        for (Player teamPlayer : teamVictim.getPlayers()) {
            ChatUtils.sendTitle(teamPlayer, arena.getMessageConfig(), "bed_destroy_title", placeholder);
            ChatUtils.sendMessage(teamPlayer, arena.getMessageConfig(), "bed_destroy_msg", placeholder);
        }

        teamVictim.setBedAlive(false);

    }



}
