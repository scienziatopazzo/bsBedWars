package dev.bsbedwars.it.shop.content.items.special;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.ShopPrice;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Fireball extends ShopItem {


    public Fireball() {
        super(
                "Fireball",
                ShopPrice.IRON,
                40,
                false
        );
        Bukkit.getPluginManager().registerEvents(this, BedWars.getInstance());
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        return new ItemFactory(new ItemStack(Material.FIREBALL)).name("&bFireball");
    }

    @Override
    public boolean onClick(Player player) {
        player.getInventory().addItem(getItemDisplayName(player).name("&6Fireball").build());
        return true;
    }

    private final static List<Player> coolDownPlayers = new ArrayList<>();

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(event.getItem() == null || event.getItem().getType() == Material.AIR || event.getItem().getType() != Material.FIREBALL) return;

        if (BedWars.getInstance().getArena().getConfig().getBoolean("Cooldown.enabled")) {
            if(coolDownPlayers.contains(player)) {
                event.setCancelled(true);
                return;
            }
            coolDownPlayers.add(player);
            Bukkit.getServer().getScheduler().runTaskLater(BedWars.getInstance(), () -> coolDownPlayers.remove(player), BedWars.getInstance().getArena().getConfig().getLong("Cooldown.time"));
        }

        int amount = event.getItem().getAmount() - 1;


        if(amount == 0) {
            event.getPlayer().setItemInHand(null);
        }else {
            event.getItem().setAmount(amount);
        }



        player.launchProjectile(org.bukkit.entity.Fireball.class);

        event.setCancelled(true);

    }





}
