package dev.bsbedwars.it.shop.content.items.sword;

import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Stick extends ShopItem {
    public Stick() {
        super(
                "Stick",
                ShopPrice.GOLD,
                5,
                false
        );
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        return new ItemFactory(new ItemStack(Material.STICK, 1)).name("&bStick").addEnchant(Enchantment.KNOCKBACK, 1).hideEnchant(true);
    }

    @Override
    public boolean onClick(Player player) {
        player.getInventory().addItem(getItemDisplayName(player).hideEnchant(false).build());
        return true;
    }

}


