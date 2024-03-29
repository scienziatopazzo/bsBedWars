package dev.bsbedwars.it.event.imp;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.arena.component.ArenaScoreboard;
import dev.bsbedwars.it.arena.component.ArenaTAB;
import dev.bsbedwars.it.arena.runnable.DeathAnimation;
import dev.bsbedwars.it.event.reg.BedWarsKillEvent;
import dev.bsbedwars.it.team.Team;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class BedWarsKillEventImp implements Listener {


    @EventHandler
    public void onKill(BedWarsKillEvent e){
        Arena arena = e.getArena();
        Player player = e.getPlayer();
        Player victim = e.getVictim();
        Team team = e.getTeam();
        Team teamVictim = e.getTeamVictim();
        boolean finalKill = e.isFinalKill();
        boolean suicide = e.isSuicide();

        HashMap<String, String> placeholder = new HashMap<>();
        if(!suicide) {
            placeholder.put("player", player.getName());
            placeholder.put("player_team_color_code", team.getColor().getColorCode());
            placeholder.put("player_team_color_name", team.getColor().toString());
        }
        placeholder.put("victim_name", victim.getName());
        placeholder.put("victim_team_color_code", teamVictim.getColor().getColorCode());
        placeholder.put("victim_team_color_name", teamVictim.getColor().toString());
        for (Player playerInArena : arena.getPlayers()) {
            if(finalKill) {
                ChatUtils.sendMessage(playerInArena, arena.getMessageConfig(), !suicide ? "finalKill" : "finalKill_suicide", placeholder);
            }else {
                ChatUtils.sendMessage(playerInArena, arena.getMessageConfig(), !suicide ? "kill" : "kill_suicide", placeholder);
            }
        }

        ChatUtils.sendMessage(victim, arena.getMessageConfig(), "kill_msg", placeholder);

        Bukkit.getScheduler().runTask(BedWars.getInstance(), () -> {
            if (finalKill) {
                teamVictim.death(victim);
                arena.getPlayers().remove(victim);
                arena.getSpectators().add(victim);
                if(teamVictim.isEmpty())
                    arena.getTeams().remove(teamVictim);
                ArenaScoreboard.update();
                ArenaTAB.update();
            }
        });



        e.getEvent().setKeepInventory(true);
        e.getEvent().setDroppedExp(0);

        e.getPlayer().getInventory().addItem(e.getEvent().getDrops().stream()
                .filter(itemStack -> !itemStack.getType().toString().contains("SWORD"))
                .filter(itemStack -> !itemStack.getType().toString().contains("PICKAXE"))
                .filter(itemStack -> !itemStack.getType().toString().contains("AXE"))
                .filter(itemStack -> !itemStack.getType().toString().contains("HELMET"))
                .filter(itemStack -> !itemStack.getType().toString().contains("CHESTPLATE"))
                .filter(itemStack -> !itemStack.getType().toString().contains("LEGGINGS"))
                .filter(itemStack -> !itemStack.getType().toString().contains("BOOTS"))
                .toArray(ItemStack[]::new));

        e.getEvent().getDrops().clear();

        new DeathAnimation(e.getArena(), victim, teamVictim, finalKill, arena.getConfig().getInt("respawn_seconds")).runTaskTimer(BedWars.getInstance(), 0L, 20L);

        arena.checkWin();
    }

}
