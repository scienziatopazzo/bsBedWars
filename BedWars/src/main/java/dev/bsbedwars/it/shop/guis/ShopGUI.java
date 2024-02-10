package dev.bsbedwars.it.shop.guis;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.gui.AbstractGUI;
import dev.bsbedwars.it.shop.content.ShopCategory;
import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Bed;

import java.util.HashMap;
import java.util.List;

public class ShopGUI extends AbstractGUI {

    public ShopGUI(ShopCategory category) {
        super("Shop", 54);
        category.getShopContent().forEach((slot, item) -> {
            setItem(item, slot);
        });
        category.getShopCategory().forEach((slot, name) -> {
            setItem(BedWars.getInstance().getShopProvider().getCategory(name).getItemDisplay(), slot);
        });
        category.getShopItems().forEach((slot, item) -> {
            setItem(item.getItemDisplay(), slot);
        });
    }

    @Override
    public boolean onClick(int slot, ItemStack itemStack, Player player) {
        return true;
    }

}
