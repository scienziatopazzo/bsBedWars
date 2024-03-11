package dev.bsbedwars.it.shop.content.items.bloks;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

public class Glass extends ShopItem{

    public Glass() {
        super(
                "Glass",
                ShopPrice.IRON,
                12,
                false
        );
        Bukkit.getPluginManager().registerEvents(this, BedWars.getInstance());
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        return new ItemFactory(new ItemStack(Material.GLASS, 4)).name("&bGlass");
    }

    @Override
    public boolean onClick(Player player) {
        player.getInventory().addItem(new ItemFactory(new ItemStack(Material.GLASS, 4)).build());
        return true;
    }


    @EventHandler
    public void onTntExplode(EntityExplodeEvent event) {
        event.blockList().removeIf(block -> block.getType() == Material.GLASS || !BedWars.getInstance().getArena().getBlockPlaced().contains(block));
    }

}

