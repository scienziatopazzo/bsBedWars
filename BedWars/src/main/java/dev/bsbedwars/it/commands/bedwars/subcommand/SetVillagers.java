package dev.bsbedwars.it.commands.bedwars.subcommand;

import com.google.common.base.Enums;
import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.command.BWSubCommand;
import dev.bsbedwars.it.npc.NPCManager;
import dev.bsbedwars.it.team.component.TeamColor;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.GameFile;
import dev.bsbedwars.it.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SetVillagers extends BWSubCommand {

    @Override
    public void execute(Player player, String[] args) {

        Location location = player.getLocation();


        NPCManager.Usage usage = Enums.getIfPresent(NPCManager.Usage.class, args[0].toUpperCase()).orNull();

        if(usage == null) {
            send(player, ChatUtils.prefix() + "&cInvalid usage!");
            return;
        }

        Arena arena = BedWars.getInstance().getArena();
        FileConfiguration config = arena.getVillagersFile().getFileConfiguration();

        new LocationUtil(location)
                .set(config, "villagers." + usage + "." + UUID.randomUUID().toString());
        arena.getVillagersFile().save();

        send(player, ChatUtils.prefix() + "&aVillagers set!");
    }
}

