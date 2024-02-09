package dev.bsbedwars.it.arena.runnable;

import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.team.Team;
import dev.bsbedwars.it.team.component.armor.Armor;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathAnimation extends BukkitRunnable {

    private final Arena arena;
    private final Player player;
    private final Team team;
    private final boolean finalKill;
    private int seconds;


    public DeathAnimation(Arena arena, Player player, Team team, boolean finalKill, int seconds) {
        this.arena = arena;
        this.player = player;
        this.team = team;
        this.finalKill = finalKill;
        this.seconds = seconds;
    }


    @Override
    public void run() {
        if(!player.isOnline()){
            team.getPlayers().remove(player);
            arena.getPlayers().remove(player);
            arena.checkWin();
            cancel();
            return;
        }

        player.spigot().respawn();

        if(finalKill) {
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(team.getBedLocation());
            ChatUtils.sendMessage(player, arena.getMessageConfig(), "final_kill_respawn_msg", player.getName(), team.getColor().toString(), team.getColor().getColorCode());
            cancel();
            return;
        }



        if(seconds < 0) {
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(team.getSpawnLocation());
            ChatUtils.sendMessage(player, arena.getMessageConfig(), "respawn", player.getName(), team.getColor().toString(), team.getColor().getColorCode());
            player.getInventory().clear();
            Armor.giveWoodSword(player);
            Armor.updatePlayerArmor(player, Armor.getArmor(player));
            cancel();
            return;
        }


        player.spigot().respawn();
        player.setGameMode(GameMode.SPECTATOR);

        ChatUtils.sendTitle(player, arena.getMessageConfig(), "respawn_title", player.getName(), team.getColor().toString(), team.getColor().getColorCode(), String.valueOf(seconds));
        ChatUtils.sendMessage(player, arena.getMessageConfig(), "respawn_msg", player.getName(), team.getColor().toString(), team.getColor().getColorCode(), String.valueOf(seconds));

        seconds--;

    }
}
