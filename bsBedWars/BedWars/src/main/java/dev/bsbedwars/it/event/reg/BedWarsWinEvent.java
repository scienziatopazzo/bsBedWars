package dev.bsbedwars.it.event.reg;

import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.team.Team;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;


@Getter
public class BedWarsWinEvent extends Event {

    private static final HandlerList handers = new HandlerList();

    private Arena arena;
    private Team team;

    public BedWarsWinEvent(Arena arena, Team team) {
        this.arena = arena;
        this.team = team;
    }

    public BedWarsWinEvent() {}


    public static HandlerList getHandlerList() {
        return handers;
    }

    @Override
    public HandlerList getHandlers() {
        return handers;
    }

}
