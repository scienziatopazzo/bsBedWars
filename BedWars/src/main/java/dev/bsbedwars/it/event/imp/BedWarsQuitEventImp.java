package dev.bsbedwars.it.event.imp;

import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.event.reg.BedWarsQuitEvent;
import dev.bsbedwars.it.team.Team;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BedWarsQuitEventImp implements Listener {


    @EventHandler
    public void onQuit(BedWarsQuitEvent e){
        Arena arena = e.getArena();
        Player player = e.getPlayer();

        if(arena.getStatus() == Status.LOBBY || arena.getStatus() == Status.STARTING) {
            arena.getPlayers().remove(player);
            for (Player playerInArena : arena.getPlayers())
                if(playerInArena != player)
                    ChatUtils.sendMessage(playerInArena, arena.getMessageConfig(), "quit_msg", player.getName(), String.valueOf(arena.getPlayers().size()), String.valueOf(arena.getType().getMaxPlayers()));
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

        for (Player playerInArena : arena.getPlayers())
            if(playerInArena != player)
                ChatUtils.sendMessage(playerInArena, arena.getMessageConfig(), "quit", player.getName(), String.valueOf(arena.getPlayers().size()), String.valueOf(arena.getType().getMaxPlayers()));

        arena.checkWin();





    }


}
