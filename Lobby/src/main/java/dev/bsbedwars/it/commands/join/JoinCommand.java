package dev.bsbedwars.it.commands.join;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.google.common.base.Enums;
import dev.bsbedwars.it.Lobby;
import dev.bsbedwars.it.bedwars.BedWars;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.concurrent.ExecutionException;

@CommandAlias("join")
public class JoinCommand extends BaseCommand {

    @Default
    public void onJoin(Player player, String[] args) {

        if(args.length == 0) {
            player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&cIncorrect syntax!"));
            return;
        }

        Type type = Enums.getIfPresent(Type.class, args[0].toUpperCase()).orNull();


        if(type == null) {
            player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&cIncorrect type!"));
            return;
        }


        Set<BedWars> bedWars = Lobby.getInstance().getBedWarsManager().getBedWars();

        BedWars bw = bedWars.stream()
                .filter(b -> b.getType() == type)
                .filter(b -> b.getStatus() == Status.LOBBY || b.getStatus() == Status.STARTING)
                .filter(b -> b.getPlayers() < b.getType().getMaxPlayers())
                .sorted((b1, b2) -> Integer.compare(b2.getPlayers(), b1.getPlayers()))
                .findFirst()
                .orElse(null);


        if(bw == null) {
             player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&cNo bedwars found!"));
             return;
         }

         player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&bConnecting to &c" + bw.getName() + "&b..."));
         Lobby.getInstance().getCommon().getBungeeApi().connect(player, bw.getName());
    }

}
