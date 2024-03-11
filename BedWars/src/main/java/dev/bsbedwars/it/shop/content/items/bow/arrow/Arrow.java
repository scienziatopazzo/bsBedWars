package dev.bsbedwars.it.shop.content.items.bow.arrow;

import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Arrow extends ShopItem {
    public Arrow() {
        super(
                "Arrow",
                ShopPrice.GOLD,
                2,
                false
        );
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        return new ItemFactory(new ItemStack(Material.ARROW, 6));
    }

    @Override
    public boolean onClick(Player player) {
        player.getInventory().addItem(getItemDisplayName(player).build());
        return true;
    }

}

