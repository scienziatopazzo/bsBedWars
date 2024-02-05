package dev.bsbedwars.it.commands.join.gui;

import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.gui.AbstractGUI;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BedWarsGUIMain extends AbstractGUI {
    public BedWarsGUIMain() {
        super("BedWars", 27);
        setItem(
                new ItemFactory(Material.BED)
                        .name("&c&lSOLO")
                        .setLore(
                                "",
                                "&bClick open GUI solo!",
                                ""
                        )
                        .build(),
                11
        );
        setItem(
                new ItemFactory(Material.BED)
                        .name("&c&lDUO")
                        .setLore(
                                "",
                                "&bClick open GUI duo!",
                                ""
                        )
                        .build(),
                12
        );
        setItem(
                new ItemFactory(Material.SIGN)
                        .name("&b&lBEDWARS")
                        .setLore(
                                "",
                                "&bDo you want play bedwars?",
                                ""
                        )
                        .build(),
                13
        );
        setItem(
                new ItemFactory(Material.BED)
                        .name("&c&lTRIO")
                        .setLore(
                                "",
                                "&bClick open GUI trio!",
                                ""
                        )
                        .build(),
                14
        );
        setItem(
                new ItemFactory(Material.BED)
                        .name("&c&lSQUAD")
                        .setLore(
                                "",
                                "&bClick open GUI squad!",
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
        return true;
    }
}
