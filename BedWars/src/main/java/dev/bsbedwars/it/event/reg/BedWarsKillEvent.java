package dev.bsbedwars.it.event.reg;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.team.Team;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

@Getter
public class BedWarsKillEvent extends Event implements Listener {

    private static final HandlerList handers = new HandlerList();

    private Arena arena;
    private Player player;
    private Team team;
    private Player victim;
    private Team teamVictim;
    private boolean finalKill;
    private PlayerDeathEvent event;
    private boolean suicide;

    public BedWarsKillEvent(Arena arena, Player player, Team team, Player victim, Team teamVictim, boolean finalKill, PlayerDeathEvent event, boolean suicide) {
        this.arena = arena;
        this.player = player;
        this.team = team;
        this.victim = victim;
        this.teamVictim = teamVictim;
        this.finalKill = finalKill;
        this.event = event;
        this.suicide = suicide;
    }

    public BedWarsKillEvent() {}



    public static HandlerList getHandlerList() {
        return handers;
    }

    @Override
    public HandlerList getHandlers() {
        return handers;
    }


    @EventHandler
    public void onKill(PlayerDeathEvent e) {

        boolean suicide = e.getEntity().getKiller() == null || e.getEntity().getKiller().getType() != EntityType.PLAYER;

        if(suicide) {
            Arena arena = BedWars.getInstance().getArena();

            if(arena == null)
                return;

            Player victim = e.getEntity();

            Team teamVictim = arena.getTeam(victim);

            if(team == null)
                return;

            boolean finalKill = !teamVictim.isBedAlive();
            Bukkit.getPluginManager().callEvent(new BedWarsKillEvent(arena, null, null, victim, teamVictim, finalKill, e, true));
        } else {
            Arena arena = BedWars.getInstance().getArena();

            if(arena == null)
                return;

            Player player = e.getEntity().getKiller();
            Player victim = e.getEntity();

            Team team = arena.getTeam(player);
            Team teamVictim = arena.getTeam(victim);

            if(team == null)
                return;

            boolean finalKill = !teamVictim.isBedAlive();
            Bukkit.getPluginManager().callEvent(new BedWarsKillEvent(arena, player, team, victim, teamVictim, finalKill, e, false));
        }


        e.setDeathMessage("");


    }


}
