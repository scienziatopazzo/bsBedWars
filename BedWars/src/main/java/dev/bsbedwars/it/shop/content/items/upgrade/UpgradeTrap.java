package dev.bsbedwars.it.shop.content.items.upgrade;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.shop.content.UpgradeItem;
import dev.bsbedwars.it.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;

public class UpgradeTrap extends UpgradeItem {


    private static final HashMap<Type, Integer[]> prices = new HashMap<>();


    public UpgradeTrap() {
        super("It's a Trap!", prices, Material.TRIPWIRE_HOOK, Arrays.asList("Applies blindness and slowness to the", "enemy for 8 seconds when they come", "to your base."));
        Bukkit.getPluginManager().registerEvents(this, BedWars.getInstance());
    }

    @Override
    public int getLevel(Team team) {
        return !((boolean) team.getTeamUpgrade().getUpgrade("trap")) ? 0 : 1;
    }

    @Override
    public boolean onClick(Team team) {
        team.getTeamUpgrade().addUpgrade("trap", true);
        return true;
    }



    static {
        prices.put(Type.SOLO, Arrays.asList(1).toArray(new Integer[0]));
        prices.put(Type.DUO, Arrays.asList(1).toArray(new Integer[0]));
        prices.put(Type.TRIO, Arrays.asList(1).toArray(new Integer[0]));
        prices.put(Type.SQUAD, Arrays.asList(1).toArray(new Integer[0]));
    }


    /*
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Arena arena = BedWars.getInstance().getArena();
        if(arena.getStatus() != Status.GAME) return;

        for (Team team : arena.getTeams()) {
            if (team.getPlayers().contains(player)) continue;
            if (!team.getTeamCuboid().isIn(player)) continue;
            boolean trap = (boolean) team.getTeamUpgrade().getUpgrade("trap");
            if (!trap) return;
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5, 2, true, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 2, true, true));
            Bukkit.getScheduler().runTaskLater(BedWars.getInstance(), () -> {
                team.getTeamUpgrade().addUpgrade("trap", false);
            }, 20L * 5);
        }
    }
     */

}

