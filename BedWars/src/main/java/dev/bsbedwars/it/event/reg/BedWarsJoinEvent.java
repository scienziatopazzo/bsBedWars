package dev.bsbedwars.it.event.reg;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.awt.*;

@Getter
public class BedWarsJoinEvent extends Event implements Listener {

    private static final HandlerList handers = new HandlerList();

    private Arena arena;
    private Player player;

    public BedWarsJoinEvent(Arena arena, Player player) {
        this.arena = arena;
        this.player = player;
    }

    public BedWarsJoinEvent() {}



    @Override
    public HandlerList getHandlers() {
        return handers;
    }


    public static HandlerList getHandlerList() {
        return handers;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Arena arena = BedWars.getInstance().getArena();

        if(arena == null)
            return;

        Bukkit.getPluginManager().callEvent(new BedWarsJoinEvent(arena, player));

        e.setJoinMessage("");
    }


}
