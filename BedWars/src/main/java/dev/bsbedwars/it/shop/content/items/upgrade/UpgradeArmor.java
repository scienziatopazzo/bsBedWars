package dev.bsbedwars.it.shop.content.items.upgrade;

import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.shop.content.UpgradeItem;
import dev.bsbedwars.it.team.Team;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UpgradeArmor extends UpgradeItem {


    private static final HashMap<Type, Integer[]> prices = new HashMap<>();


    public UpgradeArmor() {
        super("Armor Upgrade", prices, Material.IRON_CHESTPLATE, Arrays.asList("Your team will gain protection", "on all armor pieces"));
    }

    @Override
    public int getLevel(Team team) {
        return (int) team.getTeamUpgrade().getUpgrade("protection");
    }

    @Override
    public boolean onClick(Team team) {
        team.getTeamUpgrade().addUpgrade("protection", (int) team.getTeamUpgrade().getUpgrade("protection") + 1);
        return true;
    }



    static {
        prices.put(Type.SOLO, Arrays.asList(2, 4, 8, 16).toArray(new Integer[0]));
        prices.put(Type.DUO, Arrays.asList(2, 4, 8, 16).toArray(new Integer[0]));
        prices.put(Type.TRIO, Arrays.asList(5, 10, 20, 30).toArray(new Integer[0]));
        prices.put(Type.SQUAD, Arrays.asList(5, 10, 20, 30).toArray(new Integer[0]));
    }

}
