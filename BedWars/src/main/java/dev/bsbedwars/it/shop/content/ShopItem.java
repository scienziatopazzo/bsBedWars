package dev.bsbedwars.it.shop.content;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.team.Team;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.ItemFactory;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class ShopItem implements Listener {


    private final String id;
    private ShopPrice priceType;
    private int price;
    private final boolean requireReload;

    protected ShopItem(String id, ShopPrice priceType, int price, boolean requireReload) {
        this.id = id;
        this.priceType = priceType;
        this.price = price;
        this.requireReload = requireReload;
        BedWars.getInstance().getShopProvider().getShopItems().add(this);
    }


    public abstract ItemFactory getItemDisplayName(Player player);

    public abstract boolean onClick(Player player);



}
