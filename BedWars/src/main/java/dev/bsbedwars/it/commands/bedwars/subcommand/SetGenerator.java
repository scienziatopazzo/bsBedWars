package dev.bsbedwars.it.commands.bedwars.subcommand;

import com.google.common.base.Enums;
import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.command.BWSubCommand;
import dev.bsbedwars.it.generators.GeneratorType;
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

public class SetGenerator extends BWSubCommand {

    @Override
    public void execute(Player player, String[] args) {
        GeneratorType generatorType = Enums.getIfPresent(GeneratorType.class, args[0].toUpperCase()).orNull();
        int id = tryParse(args[1], -1);

        if(generatorType == null || generatorType == GeneratorType.BASE) {
            send(player, ChatUtils.prefix() + "&cInvalid generatorType!");
            return;
        }
        if(id == -1 || id > 4) {
            send(player, ChatUtils.prefix() + "&cInvalid id!");
            return;
        }

        Location location = player.getLocation();

        Arena arena = BedWars.getInstance().getArena();

        GameFile generatorsFile = arena.getGeneratorsFile();
        FileConfiguration config = generatorsFile.getFileConfiguration();

        new LocationUtil(location)
                .set(config, generatorType.toString() + "." + id + ".location");
        generatorsFile.save();

        send(player, ChatUtils.prefix() + "&aGenerator set!");
    }

    public int tryParse(String value, int defaultVal) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

}
