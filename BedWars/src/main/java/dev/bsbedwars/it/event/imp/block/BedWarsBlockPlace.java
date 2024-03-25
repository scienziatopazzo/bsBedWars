package dev.bsbedwars.it.event.imp.block;

import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.event.reg.BedWarsBlockUpdate;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BedWarsBlockPlace implements Listener {

    @EventHandler
    public void onPlace(BedWarsBlockUpdate e) {
        if(e.getType() != BedWarsBlockUpdate.UpdateType.BLOCK_PLACE) return;

        Player player = e.getPlayer();
        Arena arena = e.getArena();
        Block block = e.getBlock();

        if(arena.getStatus() == Status.LOBBY || arena.getStatus() == Status.STARTING) {
            if(!BuildManager.getEnableBuild().contains(player)) {
                e.getPlaceEvent().setCancelled(true);
                return;
            }
            if(arena.getStatus() != Status.LOBBY)
                arena.getBlockPlaced().add(block);
            return;
        }

        arena.getBlockPlaced().add(block);
    }

}
