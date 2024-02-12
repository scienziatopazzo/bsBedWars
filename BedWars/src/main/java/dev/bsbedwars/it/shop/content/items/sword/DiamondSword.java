package dev.bsbedwars.it.shop.content.items.sword;

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
                "Diamond Sword",
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

}

