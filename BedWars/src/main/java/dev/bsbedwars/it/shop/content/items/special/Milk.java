package dev.bsbedwars.it.shop.content.items.special;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Milk extends ShopItem {

    @Getter
    private static final List<Player> playersWithMilk = new ArrayList<>();

    public Milk() {
        super(
                "Milk",
                ShopPrice.GOLD,
                3,
                false
        );
        Bukkit.getPluginManager().registerEvents(this, BedWars.getInstance());
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        return new ItemFactory(new ItemStack(Material.MILK_BUCKET)).name("&bMilk");
    }

    @Override
    public boolean onClick(Player player) {
        player.getInventory().addItem(getItemDisplayName(player).build());
        return true;
    }


    @EventHandler
    public void onClick(PlayerItemConsumeEvent event) {

        if (event.getItem() == null || event.getItem().getType() == Material.AIR || event.getItem().getType() != Material.MILK_BUCKET)
            return;


        int amount = event.getItem().getAmount() - 1;

        if (amount == 0) {
            event.getPlayer().setItemInHand(null);
        } else {
            event.getItem().setAmount(amount);
        }

        playersWithMilk.add(event.getPlayer());
        Bukkit.getScheduler().runTaskLater(BedWars.getInstance(), () -> playersWithMilk.remove(event.getPlayer()), 20L * 30);
    }

}


