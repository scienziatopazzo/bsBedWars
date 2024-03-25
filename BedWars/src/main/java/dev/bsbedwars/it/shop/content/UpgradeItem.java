package dev.bsbedwars.it.shop.content;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.team.Team;
import dev.bsbedwars.it.utils.ItemFactory;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public abstract class UpgradeItem implements Listener {


    private final String id;
    private final HashMap<Type, Integer[]> prices;
    private final Material displayMaterial;
    private final List<String> lines;

    protected UpgradeItem(String id, HashMap<Type, Integer[]> prices, Material displayMaterial, List<String> lines) {
        this.id = id;
        this.prices = prices;
        this.displayMaterial = displayMaterial;
        this.lines = lines;
        BedWars.getInstance().getShopProvider().getUpgradeItems().add(this);
    }

    public abstract int getLevel(Team team);
    public abstract boolean onClick(Team team);



}

