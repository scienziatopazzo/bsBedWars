package dev.bsbedwars.it.commands.join.gui;

import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.gui.AbstractGUI;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BedWarsGUIMain extends AbstractGUI {
    public BedWarsGUIMain() {
        super("BedWars", 27);
        setItem(
                new ItemFactory(Material.BED)
                        .name("&6SOLO")
                        .setLore(
                                "",
                                "&eClick to open the Solo-Mode selector!",
                                ""
                        )
                        .build(),
                11
        );
        setItem(
                new ItemFactory(Material.BED)
                        .name("&6DUO")
                        .setLore(
                                "",
                                "&eClick to open the Duo-Mode selector!",
                                ""
                        )
                        .build(),
                12
        );
        setItem(
                new ItemFactory(Material.SIGN)
                        .name("&e&lBEDWARS")
                        .setLore(
                                "",
                                "&6Do you want play bedwars?",
                                ""
                        )
                        .build(),
                13
        );
        setItem(
                new ItemFactory(Material.BED)
                        .name("&6TRIO")
                        .setLore(
                                "",
                                "&eClick to open the Trio-Mode selector!",
                                ""
                        )
                        .build(),
                14
        );
        setItem(
                new ItemFactory(Material.BED)
                        .name("&6SQUAD")
                        .setLore(
                                "",
                                "&eClick to open the Squad-Mode selector!",
                                ""
                        )
                        .build(),
                15
        );
    }

    @Override
    public boolean onClick(int slot, ItemStack itemStack, Player player) {
        HashMap<Integer, Type> slots = new HashMap<>();

        slots.put(11, Type.SOLO);
        slots.put(12, Type.DUO);
        slots.put(14, Type.TRIO);
        slots.put(15, Type.SQUAD);

        if(!slots.containsKey(slot)) return true;

        new BedWarsGUI(slots.get(slot)).open(player);
        sound(player, Sound.ORB_PICKUP);
        return true;
    }
}
