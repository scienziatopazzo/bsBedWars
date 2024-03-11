package dev.bsbedwars.it.shop.content.items.special;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class Tnt extends ShopItem {
    public Tnt() {
        super(
                "Tnt",
                ShopPrice.GOLD,
                4,
                false
        );
        Bukkit.getPluginManager().registerEvents(this, BedWars.getInstance());
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        return new ItemFactory(new ItemStack(Material.TNT)).name("&bTNT");
    }

    @Override
    public boolean onClick(Player player) {
        player.getInventory().addItem(getItemDisplayName(player).name("&cTNT").build());
        return true;
    }


    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if(event.isCancelled()) return;

        Block block = event.getBlock();

        if(block.getType() != Material.TNT) return;

        player.getWorld().spawnEntity(block.getLocation(), EntityType.PRIMED_TNT);

        event.getPlayer().setItemInHand(null);

        event.setCancelled(true);

    }



}
