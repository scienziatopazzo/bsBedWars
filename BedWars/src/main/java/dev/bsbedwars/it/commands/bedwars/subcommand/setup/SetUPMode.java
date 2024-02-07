package dev.bsbedwars.it.commands.bedwars.subcommand.setup;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.team.TeamColor;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.ItemFactory;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SetUPMode {

    private static final HashMap<Player, ItemStack[]> savedInventory = new HashMap<>();


    public static void setUPMode(Player player) {
        setSetUPMode(player, !savedInventory.containsKey(player));
    }

    private static void setSetUPMode(Player player, boolean set) {
        if(set) {
            savedInventory.put(player, player.getInventory().getContents());
            player.getInventory().clear();
            updateInventory(player);
            player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&aSetUP mode enabled! &8(DO AGAIN /bw setup to toggle this mode!)"));
            return;
        }
        player.getInventory().setContents(savedInventory.get(player));
        savedInventory.remove(player);
        player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&aSetUP mode disabled!"));
    }

    private static void updateInventory(Player player) {
        player.getInventory().setItem(
                0,
                new ItemFactory(Material.BLAZE_ROD)
                        .name("&b&lSETUP")
                        .setLore(
                                "",
                                "&7Click for open gui setup",
                                "")
                        .build()
        );
    }

}
