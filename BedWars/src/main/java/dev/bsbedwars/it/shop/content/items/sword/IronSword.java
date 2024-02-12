package dev.bsbedwars.it.shop.content.items.sword;

import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class IronSword extends ShopItem {
    public IronSword() {
        super(
                "Iron Sword",
                new ItemFactory(Material.IRON_SWORD)
                        .name("&bIron Sword")
                        .build(),
                new ItemFactory(Material.IRON_SWORD)
                        .name("&bIron Sword")
                        .build(),
                ShopPrice.GOLD,
                12
        );
    }

    @Override
    public void onClick(Player player) {
        player.getInventory().addItem(getItemStack());
    }

}
