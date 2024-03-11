package dev.bsbedwars.it.team.component.sword;

import dev.bsbedwars.it.team.component.armor.Armor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class DropSwordEvent implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        int sword = 0;
        for (ItemStack itemStack : e.getPlayer().getInventory()) {
            if(itemStack == null) continue;
            if(itemStack.getType() == Material.WOOD_SWORD || itemStack.getType() == Material.IRON_SWORD || itemStack.getType() == Material.DIAMOND_SWORD)
                sword++;
        }
        if(e.getItemDrop().getItemStack().getType() == Material.WOOD_SWORD || e.getItemDrop().getItemStack().getType() == Material.IRON_SWORD || e.getItemDrop().getItemStack().getType() == Material.DIAMOND_SWORD) {
            if(e.getItemDrop().getItemStack().getType() == Material.WOOD_SWORD) {
                e.setCancelled(true);
                return;
            }
            if(sword == 0)
                Sword.giveWoodSword(e.getPlayer());
        }
    }

}
