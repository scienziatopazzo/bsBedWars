package dev.bsbedwars.it.commands.help;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("bwhelp|bwh")
@CommandPermission("bedwars.admin")
public class HelpCommand extends BaseCommand {


    @Default
    public void onHelp(Player player) {
        player.sendMessage("...");
    }



}
