package dev.bsbedwars.it.shop.content.items.bloks;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Clay extends ShopItem {
    public Clay() {
        super(
                "Clay",
                ShopPrice.IRON,
                12,
                false
        );
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        return new ItemFactory(new ItemStack(Material.STAINED_CLAY, 16)).name("&bClay");
    }

    @Override
    public boolean onClick(Player player) {
        player.getInventory().addItem(new ItemFactory(new ItemStack(Material.STAINED_CLAY, 16)).build());
        return true;
    }

}
