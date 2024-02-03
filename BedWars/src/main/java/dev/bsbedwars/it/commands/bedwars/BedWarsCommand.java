package dev.bsbedwars.it.commands.bedwars;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import dev.bsbedwars.it.commands.bedwars.subcommand.SetTeam;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("bedwars")
@CommandPermission("bedwars.admin")
public class BedWarsCommand extends BaseCommand {


    @Subcommand("setteam")
    public void setTeam(CommandSender sender, String[] args) {
        new SetTeam().execute((Player) sender, args);
    }


}
