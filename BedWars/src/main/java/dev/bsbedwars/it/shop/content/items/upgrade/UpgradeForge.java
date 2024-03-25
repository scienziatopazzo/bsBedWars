package dev.bsbedwars.it.shop.content.items.upgrade;

import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.shop.content.UpgradeItem;
import dev.bsbedwars.it.team.Team;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashMap;

public class UpgradeForge extends UpgradeItem {


    private static final HashMap<Type, Integer[]> prices = new HashMap<>();


    public UpgradeForge() {
        super("Iron Upgrade", prices, Material.FURNACE, Arrays.asList("Improve the resource spawn rate at", "your base. After level 3, real", "Emeralds will spawn at your base!"));
    }

    @Override
    public int getLevel(Team team) {
        return (int) team.getTeamUpgrade().getUpgrade("generator");
    }

    @Override
    public boolean onClick(Team team) {
        team.getTeamUpgrade().addUpgrade("generator", (int) team.getTeamUpgrade().getUpgrade("generator") + 1);
        return true;
    }



    static {
        prices.put(Type.SOLO, Arrays.asList(2, 4, 8, 16).toArray(new Integer[0]));
        prices.put(Type.DUO, Arrays.asList(2, 4, 8, 16).toArray(new Integer[0]));
        prices.put(Type.TRIO, Arrays.asList(5, 10, 20, 30).toArray(new Integer[0]));
        prices.put(Type.SQUAD, Arrays.asList(5, 10, 20, 30).toArray(new Integer[0]));
    }

}

