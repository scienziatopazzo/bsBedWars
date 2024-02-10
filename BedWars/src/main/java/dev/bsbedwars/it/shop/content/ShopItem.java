package dev.bsbedwars.it.shop.content;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.ItemFactory;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
public abstract class ShopItem {


    private final String id;
    private final ItemStack itemDisplay;
    private final ItemStack itemStack;
    private final ShopPrice priceType;
    private final int price;

    protected ShopItem(String id, ItemStack itemDisplay, ItemStack itemStack, ShopPrice priceType, int price) {
        this.id = id;
        this.itemDisplay = ItemFactory.getItemBuilder(itemDisplay)
                .setLore(
                        BedWars.getInstance().getShopProvider().getConfig().getStringList("itemLore.shopItem").stream()
                                .map(s -> s.replace("%color%", priceType.getColor()))
                                .map(s -> s.replace("%price%", String.valueOf(price)))
                                .map(s -> s.replace("%type%", priceType.toString()))
                                .collect(Collectors.toList())
                ).build();
        this.itemStack = itemStack;
        this.priceType = priceType;
        this.price = price;
        BedWars.getInstance().getShopProvider().getItems().add(this);
    }


    public abstract void onClick(Player player);

    // Events
    public abstract void onClickEvent(Player player, ItemStack itemStack, PlayerInteractEvent event);
    public abstract void onMoveEvent(Player player, ItemStack itemStack, PlayerMoveEvent event);


}
