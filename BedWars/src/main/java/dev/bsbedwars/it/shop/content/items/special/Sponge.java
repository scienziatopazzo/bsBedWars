package dev.bsbedwars.it.shop.content.items.special;

import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Sponge extends ShopItem {
    public Sponge() {
        super(
                "Sponge",
                ShopPrice.GOLD,
                2,
                false
        );
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        return new ItemFactory(new ItemStack(Material.SPONGE)).name("&bSponge");
    }

    @Override
    public boolean onClick(Player player) {
        player.getInventory().addItem(new ItemFactory(new ItemStack(Material.SPONGE)).name("&bSponge").build());
        return true;
    }

}


