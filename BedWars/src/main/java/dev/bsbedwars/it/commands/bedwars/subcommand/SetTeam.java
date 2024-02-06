package dev.bsbedwars.it.commands.bedwars.subcommand;

import com.google.common.base.Enums;
import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.command.BWSubCommand;
import dev.bsbedwars.it.commands.bedwars.BedWarsCommand;
import dev.bsbedwars.it.team.TeamColor;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.Cuboid;
import dev.bsbedwars.it.utils.GameFile;
import dev.bsbedwars.it.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetTeam extends BWSubCommand {

    @Override
    public void execute(Player player, String[] args) {

        TeamColor color = Enums.getIfPresent(TeamColor.class, args[0].toUpperCase()).orNull();

        if(color == null) {
            send(player, ChatUtils.prefix() + "&cInvalid color!");
            return;
        }

        Location location = player.getLocation();

        Cuboid cuboid = Cuboid.createCube(location, 20);

        Location bedLocation = null;
        Location generatorLocation = null;

        for (Block block : cuboid.getAllBlocks()) {
            if(block.getType() == Material.BED_BLOCK)
                bedLocation = block.getLocation();
            if(block.getType() == Material.IRON_BLOCK)
                generatorLocation = block.getLocation();
        }

        if(bedLocation == null) {
            send(player, ChatUtils.prefix() + "&cNo bed found in area 20x20!");
            return;
        }
        if(generatorLocation == null) {
            send(player, ChatUtils.prefix() + "&cNo generator found in area 20x20! (Iron block)");
            return;
        }

        generatorLocation = generatorLocation.clone().add(0, 1, 0); // to up y + 1
        generatorLocation = new LocationUtil(null).toCenterLocation(generatorLocation);

        Arena arena = BedWars.getInstance().getArena();
        GameFile teamsFile = arena.getTeamsFile();
        FileConfiguration config = teamsFile.getFileConfiguration();

        new LocationUtil(location)
                .set(config, color.toString() + ".spawn");
        new LocationUtil(bedLocation)
                .set(config, color.toString() + ".bed");
        new LocationUtil(generatorLocation)
                .set(config, color.toString() + ".generator");
        teamsFile.save();

        send(player, ChatUtils.prefix() + "&aTeam set! &8(" + color.toString() + ")");

    }
}
