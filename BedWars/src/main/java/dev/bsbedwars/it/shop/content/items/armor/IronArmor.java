package dev.bsbedwars.it.shop.content.items.armor;

import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class IronArmor extends ShopItem {

    public IronArmor() {
        super(
                "Iron Armor",
                ShopPrice.GOLD,
                7,
                false
        );
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        return new ItemFactory(Material.IRON_CHESTPLATE).name("&bIron Armor").setUnbreakable(true);
    }

    @Override
    public boolean onClick(Player player) {
        player.getInventory().setLeggings(new ItemFactory(Material.IRON_LEGGINGS).name("").build());
        return true;
    }

}
