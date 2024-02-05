package dev.bsbedwars.it.commands.join.runnable;

import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.commands.join.gui.BedWarsGUISelector;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.scheduler.BukkitRunnable;


public class BedWarsGUIReload extends BukkitRunnable {


    @Override
    public void run() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            InventoryView inventoryView = player.getOpenInventory();

            if (inventoryView == null || !inventoryView.getTitle().contains("BedWars GUI"))
                continue;

            Type type = Type.valueOf(inventoryView.getTitle().split(" ")[2]);
            int page = Integer.parseInt(inventoryView.getTitle().split(" ")[3]);

            Inventory newInventory = new BedWarsGUISelector(type, page).getInventory();
            inventoryView.getTopInventory().setContents(newInventory.getContents());
        }
    }

}
