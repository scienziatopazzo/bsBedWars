package dev.bsbedwars.it.shop.content.items;

import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class DiamondSword extends ShopItem {
    public DiamondSword() {
        super(
                "diamond_sword",
                new ItemFactory(Material.DIAMOND_SWORD)
                        .name("&bDiamond Sword")
                        .build(),
                new ItemFactory(Material.DIAMOND_SWORD)
                        .name("&bDiamond Sword")
                        .build(),
                ShopPrice.GOLD,
                7
        );
    }

    @Override
    public void onClick(Player player) {
        player.getInventory().addItem(getItemStack());
    }

    @Override
    public void onClickEvent(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void onMoveEvent(Player player, ItemStack itemStack, PlayerMoveEvent event) {

    }

}

