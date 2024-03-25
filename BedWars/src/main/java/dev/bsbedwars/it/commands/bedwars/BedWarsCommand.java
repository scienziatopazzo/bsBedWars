package dev.bsbedwars.it.commands.bedwars;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.commands.bedwars.subcommand.*;
import dev.bsbedwars.it.commands.bedwars.subcommand.build.BuildOff;
import dev.bsbedwars.it.commands.bedwars.subcommand.build.BuildOn;
import dev.bsbedwars.it.commands.bedwars.subcommand.setup.SetUP;
import dev.bsbedwars.it.team.Team;
import org.bukkit.entity.Player;

@CommandAlias("bedwars|bw")
@CommandPermission("bedwars.admin")
public class BedWarsCommand extends BaseCommand {


    @Subcommand("setteam")
    public void setTeam(Player player, String[] args) {
        new SetTeam().execute(player, args);
    }

    @Subcommand("setgenerator")
    public void setGenerator(Player player, String[] args) {
        new SetGenerator().execute(player, args);
    }

    @Subcommand("setlobby")
    public void setLobby(Player player, String[] args) {
        new SetLobby().execute(player, args);
    }

    @Subcommand("setup")
    public void setUP(Player player, String[] args) {
        new SetUP().execute(player, args);
    }

    @Subcommand("build off")
    public void buildOff(Player player, String[] args) {
        new BuildOff().execute(player, args);
    }

    @Subcommand("build on")
    public void buildOn(Player player, String[] args) {
        new BuildOn().execute(player, args);
    }


    @Subcommand("updateTeamUpgrade")
    public void updateTeamUpgrade(Player player, String[] args) {
        Team team = BedWars.getInstance().getArena().getTeam(player);
        team.getTeamUpgrade().addUpgrade(args[0], Integer.parseInt(args[1]));
    }

    @Subcommand("setvillager")
    public void onSetVillager(Player player, String[] args) {
        new SetVillagers().execute(player, args);
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
