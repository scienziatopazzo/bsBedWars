package dev.bsbedwars.it.shop.content.items.special;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Water extends ShopItem {
    public Water() {
        super(
                "Water",
                ShopPrice.GOLD,
                3,
                false
        );
        Bukkit.getPluginManager().registerEvents(this, BedWars.getInstance());
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        return new ItemFactory(new ItemStack(Material.WATER_BUCKET)).name("&bWater");
    }

    @Override
    public boolean onClick(Player player) {
        player.getInventory().addItem(getItemDisplayName(player).build());
        return true;
    }


    @EventHandler
    public void onClick(PlayerInteractEvent event) {

        if(event.getItem() == null || event.getItem().getType() == Material.AIR || event.getItem().getType() != Material.WATER_BUCKET || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;


        int amount = event.getItem().getAmount() - 1;

        if(amount == 0) {
            event.getPlayer().setItemInHand(null);
        }else {
            event.getItem().setAmount(amount);
        }

    }




}