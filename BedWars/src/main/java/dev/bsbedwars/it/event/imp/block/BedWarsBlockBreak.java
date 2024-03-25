package dev.bsbedwars.it.event.imp.block;

import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.event.reg.BedWarsBlockUpdate;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BedWarsBlockBreak implements Listener {

    @EventHandler
    public void onBreak(BedWarsBlockUpdate e) {
        if(e.getType() != BedWarsBlockUpdate.UpdateType.BLOCK_BREAK) return;

        Player player = e.getPlayer();
        Arena arena = e.getArena();
        Block block = e.getBlock();

        if(arena.getStatus() == Status.LOBBY || arena.getStatus() == Status.STARTING || arena.getStatus() == Status.ENDING || !arena.getBlockPlaced().contains(block)) {
            if(!BuildManager.getEnableBuild().contains(player)) {
                e.getBreakEvent().setCancelled(true);
                return;
            }
            if(arena.getStatus() != Status.LOBBY)
                arena.getBlockPlaced().remove(block);
            return;
        }

        arena.getBlockPlaced().remove(block);
    }


}
