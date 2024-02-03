package dev.bsbedwars.it.event.imp;

import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.event.reg.BedWarsJoinEvent;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BedWarsJoinEventImp implements Listener {


    @EventHandler
    public void onJoin(BedWarsJoinEvent e){

        Player player = e.getPlayer();
        Arena arena = e.getArena();

        if(arena.getPlayers().size() + 1 > arena.getType().getMaxPlayers() && arena.getStatus() == Status.LOBBY || arena.getPlayers().size() + 1 > arena.getType().getMaxPlayers() && arena.getStatus() == Status.STARTING) {
            player.kickPlayer(ChatUtils.color("&cINTERNAL ERROR: TOO MANY PLAYERS IN THE GAME!"));
            return;
        }

        if(arena.getStatus() == Status.ENDING || arena.getStatus() == Status.GAME) {
            //TODO: spectator...
            return;
        }

        arena.getPlayers().add(player);
        player.sendMessage(ChatUtils.color("&bSuccessfully joined the game!"));

        for (Player playerInArena : arena.getPlayers())
            if(playerInArena != player)
                ChatUtils.sendMessage(playerInArena, arena.getMessageConfig(), "joined_msg", player.getName(), String.valueOf(arena.getPlayers().size()), String.valueOf(arena.getType().getMaxPlayers()));


    }

}
