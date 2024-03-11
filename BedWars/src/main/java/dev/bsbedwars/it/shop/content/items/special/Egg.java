package dev.bsbedwars.it.shop.content.items.special;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.runnable.EggRunnable;
import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Egg extends ShopItem {
    public Egg() {
        super(
                "Egg",
                ShopPrice.EMERALD,
                1,
                false
        );
        Bukkit.getPluginManager().registerEvents(this, BedWars.getInstance());
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        return new ItemFactory(new ItemStack(Material.EGG)).name("&bEgg");
    }

    @Override
    public boolean onClick(Player player) {
        player.getInventory().addItem(getItemDisplayName(player).name("&fEgg").build());
        return true;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {


        Player player = event.getPlayer();

        if(event.getItem() == null || event.getItem().getType() == Material.AIR || event.getItem().getType() != Material.EGG) return;

        int amount = event.getItem().getAmount() - 1;


        if(amount == 0) {
            event.getPlayer().setItemInHand(null);
        }else {
            event.getItem().setAmount(amount);
        }


        org.bukkit.entity.Egg egg = player.launchProjectile(org.bukkit.entity.Egg.class);
        new EggRunnable(player, egg).runTaskTimer(BedWars.getInstance(), 0L, 1L);

        event.setCancelled(true);


    }



}