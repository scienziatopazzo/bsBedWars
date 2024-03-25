package dev.bsbedwars.it.commands.bedwars.subcommand.setup;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SetUPListener implements Listener {

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        if(event.getPlayer().getItemInHand() == null) return;
        if(!event.getPlayer().getItemInHand().isSimilar(new ItemFactory(Material.BLAZE_ROD).name("&b&lSETUP").setLore("", "&7Click to open the setup gui!", "").build())) return;
        new SetUPGUI(BedWars.getInstance().getArena()).open(event.getPlayer());
    }



}
