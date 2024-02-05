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
                        .name("&c&lCLOSE")
                        .setLore(
                                "",
                                "&bClick to close!",
                                ""
                        )
                        .build(),
                49
        );
        setItem(
                new ItemFactory(Material.DIAMOND)
                        .name("&a&lRANDOM BEDWARS")
                        .setLore(
                                "",
                                "&bClick to join a random bedwars!",
                                ""
                        )
                        .build(),
                45
        );
        if(page > 1) {
            setItem(
                    new ItemFactory(Material.ARROW)
                            .name("&bBack page &8(" + (page - 1) + "&8)")
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
                            .name("&bNext page &8(" + (page + 1) + "&8)")
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
                                    "&bName: &c" + bw.getName(),
                                    "&bPlayers: &c" + bw.getPlayers() + "/" + bw.getType().getMaxPlayers(),
                                    "&bStatus: &c" + bw.getStatus().toString(),
                                    "&bType: &c" + bw.getType().toString(),
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
            int page = Integer.parseInt(itemStack.getItemMeta().getDisplayName().replace((slot == 18 ? "§bBack page §8(" : "§bNext page §8("), "").replace("§8)", ""));
            new BedWarsGUISelector(type, page).open(player);
            return true;
        }
        if(slot == 49) {
            player.closeInventory();
            return true;
        }

        if(slot == 45) {
            InventoryView inventory = player.getOpenInventory();
            Type type = Type.valueOf(inventory.getTitle().replace("BedWars GUI ", "").split(" ")[0]);

            JoinUtils.findBedWars(player, type);
            return true;
        }

        BedWars bw = Lobby.getInstance().getBedWarsManager().getBedWars().stream()
                .filter(b -> b.getName().equalsIgnoreCase(itemStack.getItemMeta().getDisplayName().replace("§b", "")))
                .findFirst()
                .orElse(null);

        if(bw == null) {
            player.closeInventory();
            player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&cERROR DURING SCREACH THE BEDWARS!"));
            return true;
        }



        if(bw.getStatus() == Status.GAME || bw.getStatus() == Status.ENDING || bw.getPlayers() >= bw.getType().getMaxPlayers())
            return true;

        player.closeInventory();

        JoinUtils.connect(player, bw);

        return true;
    }


}
