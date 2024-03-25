package dev.bsbedwars.it.shop.content.items.upgrade;

import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.shop.content.UpgradeItem;
import dev.bsbedwars.it.team.Team;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashMap;

public class UpgradeSword extends UpgradeItem {


    private static final HashMap<Type, Integer[]> prices = new HashMap<>();


    public UpgradeSword() {
        super("Sharp Sword", prices, Material.IRON_SWORD, Arrays.asList("Your team will gain sharpness", "on all armor pieces"));
    }

    @Override
    public int getLevel(Team team) {
        return (int) team.getTeamUpgrade().getUpgrade("sharpness");
    }

    @Override
    public boolean onClick(Team team) {
        team.getTeamUpgrade().addUpgrade("sharpness", (int) team.getTeamUpgrade().getUpgrade("sharpness") + 1);
        return true;
    }



    static {
        prices.put(Type.SOLO, Arrays.asList(4).toArray(new Integer[0]));
        prices.put(Type.DUO, Arrays.asList(4).toArray(new Integer[0]));
        prices.put(Type.TRIO, Arrays.asList(6).toArray(new Integer[0]));
        prices.put(Type.SQUAD, Arrays.asList(6).toArray(new Integer[0]));
    }

}
