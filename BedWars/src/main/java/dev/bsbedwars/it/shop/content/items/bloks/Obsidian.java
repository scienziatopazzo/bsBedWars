package dev.bsbedwars.it.shop.content.items.bloks;

import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Obsidian extends ShopItem {
    public Obsidian() {
        super(
                "Obsidian",
                ShopPrice.EMERALD,
                4,
                false
        );
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        return new ItemFactory(new ItemStack(Material.OBSIDIAN, 6)).name("&bObsidian");
    }

    @Override
    public boolean onClick(Player player) {
        player.getInventory().addItem(new ItemFactory(new ItemStack(Material.OBSIDIAN, 6)).build());
        return true;
    }

}

