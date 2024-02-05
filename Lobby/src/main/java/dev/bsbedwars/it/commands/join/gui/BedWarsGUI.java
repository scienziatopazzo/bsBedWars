package dev.bsbedwars.it.commands.join.gui;

import dev.bsbedwars.it.Lobby;
import dev.bsbedwars.it.bedwars.BedWars;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.commands.join.utils.JoinUtils;
import dev.bsbedwars.it.gui.AbstractGUI;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BedWarsGUI extends AbstractGUI {


    public BedWarsGUI(Type type) {
        super("BedWars " + type.toString(), 27);
        setItem(
                new ItemFactory(Material.BED)
                        .name("&c&l" + type.toString())
                        .setLore(
                                "",
                                "&bClick to join in a bedwars!",
                                ""
                        )
                        .build(),
                12
        );
        setItem(
                new ItemFactory(Material.SIGN)
                        .name("&cBedWars list")
                        .setLore(
                                "",
                                "&bSee all solo bedwars active!",
                                ""
                        )
                        .build(),
                14
        );
    }

    @Override
    public boolean onClick(int slot, ItemStack itemStack, Player player) {
        Type type = Type.valueOf(player.getOpenInventory().getTitle().split(" ")[1]);
        if(slot == 14) {
            new BedWarsGUISelector(type, 1).open(player);
            return true;
        }
        if(slot == 12) {

            List<BedWars> bedWars = Lobby.getInstance().getBedWarsManager().getBedWars();

            BedWars bw = bedWars.stream()
                    .filter(b -> b.getType() == type)
                    .filter(b -> b.getStatus() == Status.LOBBY || b.getStatus() == Status.STARTING)
                    .filter(b -> b.getPlayers() < b.getType().getMaxPlayers())
                    .sorted((b1, b2) -> Integer.compare(b2.getPlayers(), b1.getPlayers()))
                    .findFirst()
                    .orElse(null);


            if(bw == null) {
                player.closeInventory();
                player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&cNo bedwars found!"));
                return true;
            }

            player.closeInventory();

            JoinUtils.connect(player, bw);
            return true;
        }
        return true;
    }
}
