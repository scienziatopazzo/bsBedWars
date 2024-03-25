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


public class UpgradeMine extends UpgradeItem {


    private static final HashMap<Type, Integer[]> prices = new HashMap<>();


    public UpgradeMine() {
        super("Maniac Miner", prices, Material.GOLD_PICKAXE, Arrays.asList("All players on your team will", "receive a permanent fast mining effect."));
        Bukkit.getPluginManager().registerEvents(this, BedWars.getInstance());
    }

    @Override
    public int getLevel(Team team) {
        return (int) team.getTeamUpgrade().getUpgrade("mine");
    }

    @Override
    public boolean onClick(Team team) {
        team.getTeamUpgrade().addUpgrade("mine", (int) team.getTeamUpgrade().getUpgrade("mine") + 1);
        return true;
    }



    static {
        prices.put(Type.SOLO, Arrays.asList(2, 4).toArray(new Integer[0]));
        prices.put(Type.DUO, Arrays.asList(2, 4).toArray(new Integer[0]));
        prices.put(Type.TRIO, Arrays.asList(2, 4).toArray(new Integer[0]));
        prices.put(Type.SQUAD, Arrays.asList(2, 4).toArray(new Integer[0]));
    }


    /*
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Arena arena = BedWars.getInstance().getArena();
        if(arena.getStatus() != Status.GAME) return;
        Team team = arena.getTeam(player);
        if((int) team.getTeamUpgrade().getUpgrade("mine") >= 1)
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 5, (int) team.getTeamUpgrade().getUpgrade("mine"), true, false));
    }
     */

}