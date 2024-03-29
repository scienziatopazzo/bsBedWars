package dev.bsbedwars.it.arena.runnable;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class UpgradeRunnable extends BukkitRunnable {

    @Override
    public void run() {

        Arena arena = BedWars.getInstance().getArena();
        if(arena.getStatus() != Status.GAME) {
            cancel();
            return;
        }
        try {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.setFoodLevel(20);
                Team team = arena.getTeam(player);
                if(team == null) continue;
                if((int) team.getTeamUpgrade().getUpgrade("mine") >= 1)
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 2, (int) team.getTeamUpgrade().getUpgrade("mine") - 1, true, false));
                if((boolean) team.getTeamUpgrade().getUpgrade("pool") && team.getTeamCuboid().isIn(player.getLocation()))
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5, 2, true, false));

            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                for (Team team : arena.getTeams()) {
                    if (team.getAlivePlayers().contains(player)) continue;
                    if (!team.getTeamCuboid().isIn(player)) continue;
                    boolean trap = (boolean) team.getTeamUpgrade().getUpgrade("trap");
                    if (!trap) continue;
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2, 2, true, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 2, true, false));
                    Bukkit.getScheduler().runTaskLater(BedWars.getInstance(), () -> {
                        team.getTeamUpgrade().addUpgrade("trap", false);
                    }, 20L * 8);
                }
            }
        } catch (Exception e) {
            return;
        }




    }

}
