package dev.bsbedwars.it.commands.spawn;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import dev.bsbedwars.it.Lobby;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.GameFile;
import dev.bsbedwars.it.utils.LocationUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("spawn")
public class SpawnCommand extends BaseCommand {

    @Default
    public boolean onSpawn(Player player, String[] args) {

        GameFile fileConfig = Lobby.getInstance().getSpawnFile();
        FileConfiguration config = fileConfig.getFileConfiguration();
        if (args.length == 0) {
            if (!config.isSet("spawn")) {
                player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&cNo spawn set!"));
                return true;
            }
            player.teleport(new LocationUtil(null).deserialize(config.getString("spawn")));
            return true;
        }


        return false;

    }

    @Subcommand("set")
    @CommandPermission("bedwars.admin")
    public void setSpawn(Player player, String[] args) {
        FileConfiguration config = Lobby.getInstance().getSpawnFile().getFileConfiguration();

        new LocationUtil(player.getLocation()).set(config, "spawn");
        Lobby.getInstance().getSpawnFile().save();

        player.sendMessage(ChatUtils.color(String.format("%s &aSpawn set! &8(%s,%s,%s)", ChatUtils.prefix(), (int) player.getLocation().getX(), (int) player.getLocation().getY(), (int) player.getLocation().getZ())));
    }

}
