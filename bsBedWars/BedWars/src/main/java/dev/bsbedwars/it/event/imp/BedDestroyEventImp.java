package dev.bsbedwars.it.event.imp;

import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.event.reg.BedDestroyEvent;
import dev.bsbedwars.it.team.Team;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BedDestroyEventImp implements Listener {


    @EventHandler
    public void onBedDestroy(BedDestroyEvent e){


        Arena arena = e.getArena();
        Player player = e.getPlayer();
        Team team = e.getTeam();
        Team teamVictim = e.getTeamVictim();

        // public msg
        for (Player playerInArena : arena.getPlayers())
            ChatUtils.sendMessage(playerInArena, arena.getMessageConfig(), "bed_destroyed", player.getName(), team.getColor().getColorCode(), team.getColor().toString(), teamVictim.getColor().getColorCode(), teamVictim.getColor().toString());

        // Private msg
        for (Player teamPlayer : teamVictim.getPlayers()) {
            ChatUtils.sendTitle(teamPlayer, arena.getMessageConfig(), "bed_destroy_title", player.getName(), team.getColor().getColorCode(), team.getColor().toString(), teamVictim.getColor().getColorCode(), teamVictim.getColor().toString());
            ChatUtils.sendMessage(teamPlayer, arena.getMessageConfig(), "bed_destroy_msg", player.getName(), team.getColor().getColorCode(), team.getColor().toString(), teamVictim.getColor().getColorCode(), teamVictim.getColor().toString());
        }

        teamVictim.setBedAlive(false);

    }



}
