package dev.bsbedwars.it.shop.content.items.bow.type;

import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Bow2 extends ShopItem {
    public Bow2() {
        super(
                "Bow2",
                ShopPrice.GOLD,
                20,
                false
        );
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        return new ItemFactory(new ItemStack(Material.BOW, 1)).addEnchant(Enchantment.ARROW_DAMAGE, 1).hideEnchant(true).setUnbreakable(true);
    }

    @Override
    public boolean onClick(Player player) {
        player.getInventory().addItem(getItemDisplayName(player).hideEnchant(false).build());
        return true;
    }

}
