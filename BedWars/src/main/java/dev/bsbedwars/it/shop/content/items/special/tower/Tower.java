package dev.bsbedwars.it.shop.content.items.special.tower;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.shop.content.items.special.tower.component.coord.TowerBuild;
import dev.bsbedwars.it.shop.content.items.special.tower.component.coord.TowerStatus;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class Tower extends ShopItem {
    public Tower() {
        super(
                "Tower",
                ShopPrice.IRON,
                24,
                false
        );
        Bukkit.getPluginManager().registerEvents(this, BedWars.getInstance());
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        return new ItemFactory(new ItemStack(Material.CHEST)).name("&bTower");
    }

    @Override
    public boolean onClick(Player player) {
        player.getInventory().addItem(getItemDisplayName(player).build());
        return true;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (e.getBlockPlaced().getType() == Material.CHEST && !e.isCancelled()) {
            e.setCancelled(true);
            Location loc = e.getBlockPlaced().getLocation();
            Block chest = e.getBlockPlaced();
            double rotation = ((player.getLocation().getYaw() - 90.0F) % 360.0F);
            if (rotation < 0.0D)
                rotation += 360.0D;
            if (45.0D <= rotation && rotation < 135.0D) {
                new TowerBuild(loc, chest, player, TowerStatus.Type.SOUTH);
            } else if (225.0D <= rotation && rotation < 315.0D) {
                new TowerBuild(loc, chest, player, TowerStatus.Type.NORTH);
            } else if (135.0D <= rotation && rotation < 225.0D) {
                new TowerBuild(loc, chest, player, TowerStatus.Type.WEST);
            } else if (0.0D <= rotation && rotation < 45.0D) {
                new TowerBuild(loc, chest, player, TowerStatus.Type.EST);
            } else if (315.0D <= rotation && rotation < 360.0D) {
                new TowerBuild(loc, chest, player, TowerStatus.Type.EST);
            }
            ItemStack itemInHand = player.getInventory().getItemInHand();
            if (itemInHand.getAmount() > 1) {
                itemInHand.setAmount(itemInHand.getAmount() - 1);
            } else {
                player.getInventory().setItemInHand(null);
            }
        }
    }

}