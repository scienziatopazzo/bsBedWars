package dev.bsbedwars.it.gui;

import dev.bsbedwars.it.gui.AbstractGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ConcurrentModificationException;

public class OnClickGUI implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        try {
            AbstractGUI.getGuiRegistered().forEach(gui -> {
                if(gui.isInventory(event)) {
                    if(gui.onClick(event.getSlot(), event.getCurrentItem(), (Player) event.getView().getPlayer())) {
                        event.setCancelled(true);
                    }
                }
            });
        }catch (ConcurrentModificationException e) {
            // I know but it works
        }

    }



}