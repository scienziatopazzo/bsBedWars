package dev.bsbedwars.it.shop.content.items;

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
                "iron_armor",
                new ItemFactory(Material.IRON_CHESTPLATE)
                        .name("&bIron Armor")
                        .build(),
                new ItemFactory(Material.IRON_LEGGINGS)
                        .name("")
                        .build(),
                ShopPrice.GOLD,
                7
        );
    }

    @Override
    public void onClick(Player player) {
        player.getInventory().setLeggings(getItemStack());
    }

    @Override
    public void onClickEvent(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void onMoveEvent(Player player, ItemStack itemStack, PlayerMoveEvent event) {
    }

}
