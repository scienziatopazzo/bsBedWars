package dev.bsbedwars.it.commands.bedwars.subcommand.setup;

import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.gui.AbstractGUI;
import dev.bsbedwars.it.team.component.TeamColor;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SetUPGUI extends AbstractGUI {

    public static boolean on = true;

    public SetUPGUI(Arena arena) {
        super("BedWars SETUP", 54);

        int[] diamondSlot = {38, 39, 47, 48};

        for (int i = 0; i < 4; i++) {
            int slot = diamondSlot[i];
            setItem(
                    new ItemFactory(Material.DIAMOND_BLOCK)
                            .name("&b&lSET DIAMOND GENERATOR " + (i + 1))
                            .build(),
                    slot
            );
        }

        int[] emeraldSlot = {40, 41, 49, 50};

        for (int i = 0; i < 4; i++) {
            int slot = emeraldSlot[i];
            setItem(
                    new ItemFactory(Material.EMERALD_BLOCK)
                            .name("&a&lSET EMERALD GENERATOR " + (i + 1))
                            .build(),
                    slot
            );
        }

        int[] teamsSlotsCENTER = {36, 37, 45, 46};

        int[] teamsSlotsCENTERUPDOWN = {36, 37, 45, 46, 18, 19, 27, 28};

        if (arena.getType() == Type.SOLO || arena.getType() == Type.DUO) {
            for (int i = 0; i < arena.getType().getMaxTeams(); i++) {
                TeamColor color = TeamColor.values()[i];
                int slot = teamsSlotsCENTERUPDOWN[i];
                setItem(
                        new ItemFactory(Material.BED)
                                .name("&c&lSET TEAM " + color.getColorCode() + color.toString())
                                .build(),
                        slot
                );
            }
        }

        if (arena.getType() == Type.TRIO || arena.getType() == Type.SQUAD) {
            for (int i = 0; i < arena.getType().getMaxTeams(); i++) {
                TeamColor color = TeamColor.values()[i];
                int slot = teamsSlotsCENTER[i];
                setItem(
                        new ItemFactory(Material.BED)
                                .name("&c&lSET TEAM " + color.getColorCode() + color.toString())
                                .build(),
                        slot
                );
            }
        }


        setItem(
                new ItemFactory(Material.DIAMOND)
                        .name("&a&lCREATIVE")
                        .setLore(
                                "",
                                "&7Click for go in creative!",
                                "")
                        .build(),
                1
        );
        setItem(
                new ItemFactory(Material.DIRT)
                        .name("&a&lSURVIVAL")
                        .setLore(
                                "",
                                "&7Click for go in survival!",
                                "")
                        .build(),
                2
        );
        setItem(
                new ItemFactory(Material.EYE_OF_ENDER)
                        .name("&a&lSPECTATOR")
                        .setLore(
                                "",
                                "&7Click for go in spectator!",
                                "")
                        .build(),
                3
        );
        setItem(
                new ItemFactory(Material.BOW)
                        .name("&b&lFLY")
                        .setLore(
                                "",
                                "&7Click for &aactivate&7/&cdeactivate &7the fly!",
                                "")
                        .build(),
                4
        );
        setItem(
                new ItemFactory(new ItemStack(Material.WOOL, 1, (short) 5))
                        .name("&a&lSTART")
                        .setLore(
                                "",
                                "&7Click for start the game!",
                                "")
                        .build(),
                5
        );
        setItem(
                new ItemFactory(new ItemStack(Material.WOOL, 1, (short) 14))
                        .name("&c&lSTOP")
                        .setLore(
                                "",
                                "&7Click for stop the game!",
                                "")
                        .build(),
                6
        );
        if(on) {
            setItem(
                    new ItemFactory(Material.QUARTZ_BLOCK)
                            .name("&c&lFAKE OFF")
                            .setLore(
                                    "",
                                    "&7Click for do a fake off!",
                                    "")
                            .build(),
                    7
            );
        }else  {
            setItem(
                    new ItemFactory(Material.QUARTZ_BLOCK)
                            .name("&a&lREDO ON")
                            .setLore(
                                    "",
                                    "&7Click for redo visible bedwars!",
                                    "")
                            .build(),
                    7
            );
        }



    }

    @Override
    public boolean onClick(int slot, ItemStack itemStack, Player player) {
        String displayName = itemStack.getItemMeta().getDisplayName();

        if(displayName.contains("DIAMOND")) {
            int id = Integer.parseInt(displayName.split(" ")[3]);
            player.closeInventory();
            sound(player, Sound.ORB_PICKUP);
            player.performCommand("bw setgenerator diamond " + id);
            return true;
        }
        if(displayName.contains("EMERALD")) {
            int id = Integer.parseInt(displayName.split(" ")[3]);
            player.closeInventory();
            sound(player, Sound.ORB_PICKUP);
            player.performCommand("bw setgenerator emerald " + id);
            return true;
        }
        if(displayName.contains("TEAM")) {
            TeamColor color = TeamColor.valueOf(displayName.split(" ")[2].substring(2));
            player.closeInventory();
            sound(player, Sound.ORB_PICKUP);
            player.performCommand("bw setteam " + color);
            return true;
        }

        HashMap<String, String> itemsFunction = new HashMap<>();

        itemsFunction.put("FLY", "fly");
        itemsFunction.put("SPECTATOR", "gmsp");
        itemsFunction.put("CREATIVE", "gmc");
        itemsFunction.put("SURVIVAL", "gms");
        itemsFunction.put("START", "start");
        itemsFunction.put("STOP", "stop");
        itemsFunction.put("FAKE OFF", "fakeoff");
        itemsFunction.put("REDO ON", "redoon");

        itemsFunction.forEach((key, value) -> {
           if(displayName.contains(key)) {
               player.closeInventory();
               sound(player, Sound.ORB_PICKUP);
               player.performCommand(value);
           }
        });
        return true;
    }
}
