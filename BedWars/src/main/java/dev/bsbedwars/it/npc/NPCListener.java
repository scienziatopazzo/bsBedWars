package dev.bsbedwars.it.npc;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.shop.guis.ShopGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class NPCListener implements Listener {


    @EventHandler
    public void onClick(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        UUID uuid = event.getRightClicked().getUniqueId();
        NPCManager.Usage usage = NPCManager.getNpcs().get(uuid);
        if(usage == NPCManager.Usage.SHOP)
            new ShopGUI(BedWars.getInstance().getShopProvider().getCategory("QuickBuy"), player).open(player);
        if(usage == NPCManager.Usage.UPGRADE)
            new ShopGUI(BedWars.getInstance().getShopProvider().getCategory("Upgrades"), player).open(player);
    }
}
