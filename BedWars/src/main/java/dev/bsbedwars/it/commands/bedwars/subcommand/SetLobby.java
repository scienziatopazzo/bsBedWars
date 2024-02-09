package dev.bsbedwars.it.commands.bedwars.subcommand;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.command.BWSubCommand;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.GameFile;
import dev.bsbedwars.it.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetLobby extends BWSubCommand {

    @Override
    public void execute(Player player, String[] args) {

        Location location = player.getLocation();

        Arena arena = BedWars.getInstance().getArena();
        GameFile lobbyFile = arena.getLobbyFile();
        FileConfiguration config = lobbyFile.getFileConfiguration();

        new LocationUtil(location)
                .set(config, "lobby");
        lobbyFile.save();

        send(player, ChatUtils.prefix() + "&aLobby set!");
    }
}
