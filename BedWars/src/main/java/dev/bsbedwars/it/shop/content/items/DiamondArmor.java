package dev.bsbedwars.it.shop.content.items;

import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class DiamondArmor extends ShopItem {

    public DiamondArmor() {
        super(
                "diamond_armor",
                new ItemFactory(Material.DIAMOND_CHESTPLATE)
                        .name("&bDiamond Armor")
                        .build(),
                new ItemFactory(Material.DIAMOND_LEGGINGS)
                        .name("&bDiamond Armor")
                        .build(),
                ShopPrice.GOLD,
                12
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
