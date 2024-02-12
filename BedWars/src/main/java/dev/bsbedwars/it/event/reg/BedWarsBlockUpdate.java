package dev.bsbedwars.it.event.reg;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

@Getter
public class BedWarsBlockUpdate extends Event implements Listener {

    private static final HandlerList handers = new HandlerList();

    private Arena arena;
    private Player player;
    private UpdateType type;
    private Block block;
    private BlockBreakEvent breakEvent;
    private BlockPlaceEvent placeEvent;


    public BedWarsBlockUpdate(Arena arena, Player player, UpdateType type, Block block, BlockBreakEvent breakEvent, BlockPlaceEvent placeEvent) {
        this.arena = arena;
        this.player = player;
        this.type = type;
        this.block = block;
        this.breakEvent = breakEvent;
        this.placeEvent = placeEvent;
    }

    public BedWarsBlockUpdate() {}



    @Override
    public HandlerList getHandlers() {
        return handers;
    }


    public static HandlerList getHandlerList() {
        return handers;
    }

    public enum UpdateType {
        BLOCK_PLACE,
        BLOCK_BREAK
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Arena arena = BedWars.getInstance().getArena();
        Block blockPlaced = e.getBlockPlaced();

        Bukkit.getPluginManager().callEvent(new BedWarsBlockUpdate(arena, player, UpdateType.BLOCK_PLACE, blockPlaced, null, e));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Arena arena = BedWars.getInstance().getArena();
        Block blockBreak = e.getBlock();

        Bukkit.getPluginManager().callEvent(new BedWarsBlockUpdate(arena, player, UpdateType.BLOCK_BREAK, blockBreak, e, null));
    }

}
