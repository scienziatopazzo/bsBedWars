package dev.bsbedwars.it.event.reg;


import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.team.Team;
import dev.bsbedwars.it.team.TeamColor;
import dev.bsbedwars.it.utils.ChatUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

@Getter
public class BedDestroyEvent extends Event implements Listener {

    private static final HandlerList handers = new HandlerList();

    private Arena arena;
    private Player player;
    private Team team;
    private Team teamVictim;

    public BedDestroyEvent(Arena arena, Player player, Team team, Team teamVictim) {
        this.arena = arena;
        this.player = player;
        this.team = team;
        this.teamVictim = teamVictim;
    }

    public BedDestroyEvent() {}


    @Override
    public HandlerList getHandlers() {
        return handers;
    }


    public static HandlerList getHandlerList() {
        return handers;
    }


    @EventHandler
    public void onBedDestroy(BlockBreakEvent e) {

        if(e.getBlock().getType() != Material.BED_BLOCK)
            return;

        Player player = e.getPlayer();
        Arena arena = BedWars.getInstance().getArena();

        if(arena == null || arena.getStatus() != Status.GAME)
            return;

        Team team = arena.getTeam(player);

        if(team == null)
            return;

        Team teamVictim = arena.getTeams().stream()
                .filter(t -> t.getBedLocation().distance(e.getBlock().getLocation()) < 3)
                .findFirst()
                .orElse(null);

        if(teamVictim == null)
            return;

        if(teamVictim == team) {
            ChatUtils.sendMessage(player, arena.getMessageConfig(), "self_bed_destroy_msg", player.getName());
            e.setCancelled(true);
            return;
        }

        Bukkit.getPluginManager().callEvent(new BedDestroyEvent(arena, player, team, teamVictim));

    }

}
