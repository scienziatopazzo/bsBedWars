package dev.bsbedwars.it.event.imp;

import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.event.reg.BedWarsWinEvent;
import dev.bsbedwars.it.team.Team;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class BedWarsWinEventImp implements Listener {


    @EventHandler
    public void onWin(BedWarsWinEvent e){
        Arena arena = e.getArena();
        Team team = e.getTeam();

        //team.getPlayers().forEach(playerInArena -> ChatUtils.sendMessage(playerInArena, arena.getMessageConfig(), "win_private", team.getColor().getColorCode(), team.getColor().toString()));
        HashMap<String, String> placeholder = new HashMap<>();
        placeholder.put("team_color_code", team.getColor().getColorCode());
        placeholder.put("team_color_name", team.getColor().toString());
        arena.getPlayers().forEach(playerInArena -> {
            ChatUtils.sendMessage(playerInArena, arena.getMessageConfig(), "win", placeholder);
            ChatUtils.sendTitle(playerInArena, arena.getMessageConfig(), "win_title", placeholder);
        });
    }

}
