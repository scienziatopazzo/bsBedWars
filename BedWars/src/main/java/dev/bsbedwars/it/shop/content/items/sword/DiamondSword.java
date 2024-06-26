package dev.bsbedwars.it.shop.content.items.sword;

import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class DiamondSword extends ShopItem {
    public DiamondSword() {
        super(
                "Diamond Sword",
                ShopPrice.EMERALD,
                4,
                false
        );
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        return new ItemFactory(Material.DIAMOND_SWORD).name("&bDiamond Sword").setUnbreakable(true);
    }

    @Override
    public boolean onClick(Player player) {
        player.getInventory().addItem(getItemDisplayName(player).build());
        return true;
    }

}

