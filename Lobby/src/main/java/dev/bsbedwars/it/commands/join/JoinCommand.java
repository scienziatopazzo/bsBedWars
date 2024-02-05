package dev.bsbedwars.it.commands.join;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.google.common.base.Enums;
import dev.bsbedwars.it.Lobby;
import dev.bsbedwars.it.bedwars.BedWars;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.commands.join.gui.BedWarsGUI;
import dev.bsbedwars.it.commands.join.gui.BedWarsGUIMain;
import dev.bsbedwars.it.commands.join.gui.BedWarsGUISelector;
import dev.bsbedwars.it.commands.join.utils.JoinUtils;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@CommandAlias("join")
public class JoinCommand extends BaseCommand {

    @Default
    public void onJoin(Player player, String[] args) {

        if(args.length == 0) {
            new BedWarsGUIMain().open(player);
            return;
        }

        Type type = Enums.getIfPresent(Type.class, args[0].toUpperCase()).orNull();

        if(type == null) {
            player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&cIncorrect type!"));
            return;
        }

        new BedWarsGUI(type).open(player);
    }





}
