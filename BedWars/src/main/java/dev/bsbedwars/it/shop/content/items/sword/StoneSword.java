package dev.bsbedwars.it.shop.content.items.sword;

import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class StoneSword extends ShopItem {
    public StoneSword() {
        super(
                "Stone Sword",
                ShopPrice.IRON,
                10,
                false
        );
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        return new ItemFactory(Material.STONE_SWORD).name("&bStone Sword").setUnbreakable(true);
    }

    @Override
    public boolean onClick(Player player) {
        player.getInventory().addItem(getItemDisplayName(player).build());
        return true;
    }

}
