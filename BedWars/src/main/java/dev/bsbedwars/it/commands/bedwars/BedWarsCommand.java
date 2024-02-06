package dev.bsbedwars.it.commands.bedwars;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import com.google.common.base.Enums;
import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.commands.bedwars.subcommand.SetGenerator;
import dev.bsbedwars.it.commands.bedwars.subcommand.SetTeam;
import dev.bsbedwars.it.team.Team;
import dev.bsbedwars.it.team.TeamColor;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("bedwars")
@CommandPermission("bedwars.admin")
public class BedWarsCommand extends BaseCommand {


    @Subcommand("setteam")
    public void setTeam(Player player, String[] args) {
        new SetTeam().execute((Player) player, args);
    }

    @Subcommand("setgenerator")
    public void setGenerator(Player player, String[] args) {
        new SetGenerator().execute((Player) player, args);
    }

    /*
     @Subcommand("updateGen")
    public void updateGen(Player player, String[] args) {
         TeamColor color = Enums.getIfPresent(TeamColor.class, args[0].toUpperCase()).orNull();

         if(color == null) {
             player.sendMessage("inv color");
             return;
         }

         Team team = BedWars.getInstance().getArena().getTeams().stream().filter(t -> t.getColor() == color).findFirst().get();

         team.updateGeneratorLvL(Integer.parseInt(args[1]));
         player.sendMessage("updated");

    }
     */

}
