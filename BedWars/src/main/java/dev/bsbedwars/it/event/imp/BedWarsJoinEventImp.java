package dev.bsbedwars.it.event.imp;

import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.event.reg.BedWarsJoinEvent;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.LocationUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BedWarsJoinEventImp implements Listener {


    @EventHandler
    public void onJoin(BedWarsJoinEvent e){

        Player player = e.getPlayer();
        Arena arena = e.getArena();

        player.getInventory().clear();
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        player.getInventory().setChestplate(new ItemStack(Material.AIR));
        player.getInventory().setLeggings(new ItemStack(Material.AIR));
        player.getInventory().setBoots(new ItemStack(Material.AIR));

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
            if(playerInArena != player) {
                HashMap<String, String> placeholder = new HashMap<>();
                placeholder.put("player", player.getName());
                placeholder.put("players", String.valueOf(arena.getPlayers().size()));
                placeholder.put("maxPlayers", String.valueOf(arena.getType().getMaxPlayers()));
                ChatUtils.sendMessage(playerInArena, arena.getMessageConfig(), "joined_msg", placeholder);
            }

        if(!arena.getLobbyFile().getFileConfiguration().isSet("lobby"))
            return;

        Location location = new LocationUtil(null)
                .deserialize(arena.getLobbyFile().getFileConfiguration().getString("lobby"));

        player.teleport(location);

    }

}
