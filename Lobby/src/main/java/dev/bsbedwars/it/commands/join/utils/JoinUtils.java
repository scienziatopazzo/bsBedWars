package dev.bsbedwars.it.commands.join.utils;

import dev.bsbedwars.it.Lobby;
import dev.bsbedwars.it.bedwars.BedWars;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.entity.Player;

import java.util.List;

public class JoinUtils {

    public static void findBedWars(Player player, Type type) {
        List<BedWars> bedWars = Lobby.getInstance().getBedWarsManager().getBedWars();

        BedWars bw = bedWars.stream()
                .filter(b -> b.getType() == type)
                .filter(b -> b.getStatus() == Status.LOBBY || b.getStatus() == Status.STARTING)
                .filter(b -> b.getPlayers() < b.getType().getMaxPlayers())
                .sorted((b1, b2) -> Integer.compare(b2.getPlayers(), b1.getPlayers()))
                .findFirst()
                .orElse(null);


        if(bw == null) {
            player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&cError, no bedwars games!"));
            return;
        }

        connect(player, bw);
    }

    public static void connect(Player player, BedWars bedWars) {
        player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&6Connecting to &e" + bedWars.getName() + "&6..."));
        Lobby.getInstance().getCommon().getBungeeApi().connect(player, bedWars.getName());
    }

}
