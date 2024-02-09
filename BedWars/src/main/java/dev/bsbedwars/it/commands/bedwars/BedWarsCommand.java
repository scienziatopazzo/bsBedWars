package dev.bsbedwars.it.commands.bedwars;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.commands.bedwars.subcommand.SetGenerator;
import dev.bsbedwars.it.commands.bedwars.subcommand.SetLobby;
import dev.bsbedwars.it.commands.bedwars.subcommand.SetTeam;
import dev.bsbedwars.it.commands.bedwars.subcommand.setup.SetUP;
import dev.bsbedwars.it.team.Team;
import org.bukkit.entity.Player;

@CommandAlias("bedwars|bw")
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

    @Subcommand("setlobby")
    public void setLobby(Player player, String[] args) {
        new SetLobby().execute((Player) player, args);
    }

    @Subcommand("setup")
    public void setUP(Player player, String[] args) {
        new SetUP().execute((Player) player, args);
    }

    @Subcommand("updateTeamUpgrade")
    public void updateTeamUpgrade(Player player, String[] args) {
        Team team = BedWars.getInstance().getArena().getTeam(player);
        team.getTeamUpgrade().addUpgrade(args[0], Integer.parseInt(args[1]));
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
