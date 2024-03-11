package dev.bsbedwars.it.shop.content.items.bow.type;

import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Bow1 extends ShopItem {
    public Bow1() {
        super(
                "Bow1",
                ShopPrice.GOLD,
                12,
                false
        );
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        return new ItemFactory(new ItemStack(Material.BOW, 1)).setUnbreakable(true);
    }

    @Override
    public boolean onClick(Player player) {
        player.getInventory().addItem(getItemDisplayName(player).build());
        return true;
    }

}
