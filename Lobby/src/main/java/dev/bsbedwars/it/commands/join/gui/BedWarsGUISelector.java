package dev.bsbedwars.it.commands.join.gui;

import dev.bsbedwars.it.Lobby;
import dev.bsbedwars.it.bedwars.BedWars;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.commands.join.runnable.BedWarsGUIReload;
import dev.bsbedwars.it.commands.join.utils.JoinUtils;
import dev.bsbedwars.it.gui.AbstractGUI;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class BedWarsGUISelector extends AbstractGUI {
    public BedWarsGUISelector(Type type, int page) {
        super("BedWars GUI " + type + " " + page, 54);
        setItem(
                new ItemFactory(Material.BARRIER)
                        .name("&6CLOSE")
                        .setLore(
                                "",
                                "&eClick to close!",
                                ""
                        )
                        .build(),
                49
        );
        setItem(
                new ItemFactory(Material.DIAMOND)
                        .name("&6RANDOM BEDWARS GAMES")
                        .setLore(
                                "",
                                "&eClick to join in a bedwars random game!",
                                ""
                        )
                        .build(),
                45
        );
        if(page > 1) {
            setItem(
                    new ItemFactory(Material.ARROW)
                            .name("&6Back page &8(&e" + (page - 1) + "&8)")
                            .setLore(
                                    ""
                            )
                            .build(),
                    18
            );
        }
        List<BedWars> bedWars = Lobby.getInstance().getBedWarsManager().getBedWars()
                .stream()
                .filter(b -> b.getType() == type)
                .collect(Collectors.toList());

        if(bedWars.size() > 15 * page) {
            setItem(
                    new ItemFactory(Material.ARROW)
                            .name("&6Next page &8(&e" + (page + 1) + "&8)")
                            .setLore(
                                    ""
                            )
                            .build(),
                    26
            );
        }


        int index = 15 * (page - 1);

        List<Integer> slots = new ArrayList<>();
        int[] slot_ = {11, 12, 13, 14, 15};

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                slots.add(
                        slot_[i] + 9 * j
                );
            }
        }

        Collections.sort(slots);


        List<BedWars> bw_ = new ArrayList<>();
        for (int i = index; i < index + 16 && i < bedWars.size(); i++) {
            BedWars bw = bedWars.get(i);
            bw_.add(bw);
        }



        bw_.sort(Comparator.comparingInt(bedWars::indexOf));

        for (int i = 0; i < slots.size() && i < bw_.size(); i++) {
            BedWars bw = bw_.get(i);
            setItem(
                new ItemFactory(new ItemStack(Material.INK_SACK, 1, (short) (bw.getStatus() == Status.LOBBY ? 10 : bw.getStatus() == Status.STARTING ? 11 : bw.getStatus() == Status.GAME || bw.getStatus() == Status.ENDING ? 1 : 0)))
                            .name("&b" + bw.getName())
                            .setLore(
                                    "",
                                    "&6Name: &e" + bw.getName(),
                                    "&6Players: &e" + bw.getPlayers() + "/" + bw.getType().getMaxPlayers(),
                                    "&6Status: &e" + bw.getStatus().toString(),
                                    "&6Type: &e" + bw.getType().toString(),
                                    ""
                            )
                            .build(),
                    slots.get(i)
            );
        }



    }


    @Override
    public boolean onClick(int slot, ItemStack itemStack, Player player) {
        if(slot == 18 || slot == 26) {
            InventoryView inventory = player.getOpenInventory();
            Type type = Type.valueOf(inventory.getTitle().replace("BedWars GUI ", "").split(" ")[0]);
            int page = Integer.parseInt(itemStack.getItemMeta().getDisplayName().replace((slot == 18 ? "§6Back page §8(§e" : "§6Next page §8(§e"), "").replace("§8)", ""));
            new BedWarsGUISelector(type, page).open(player);
            sound(player, Sound.ORB_PICKUP);
            return true;
        }
        if(slot == 49) {
            sound(player, Sound.ORB_PICKUP);
            player.closeInventory();
            return true;
        }

        if(slot == 45) {
            InventoryView inventory = player.getOpenInventory();
            Type type = Type.valueOf(inventory.getTitle().replace("BedWars GUI ", "").split(" ")[0]);

            sound(player, Sound.ORB_PICKUP);
            JoinUtils.findBedWars(player, type);
            return true;
        }

        BedWars bw = Lobby.getInstance().getBedWarsManager().getBedWars().stream()
                .filter(b -> b.getName().equalsIgnoreCase(itemStack.getItemMeta().getDisplayName().replace("§b", "")))
                .findFirst()
                .orElse(null);

        if(bw == null) {
            sound(player, Sound.VILLAGER_NO);
            player.closeInventory();
            player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&cERROR TO SEARCH A BEDWARS GAMES!"));
            return true;
        }



        if(bw.getStatus() == Status.GAME || bw.getStatus() == Status.ENDING || bw.getPlayers() >= bw.getType().getMaxPlayers()) {
            sound(player, Sound.VILLAGER_NO);
            return true;
        }


        player.closeInventory();

        JoinUtils.connect(player, bw);
        sound(player, Sound.ORB_PICKUP);

        return true;
    }


}
