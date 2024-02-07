package dev.bsbedwars.it.commands.bedwars.subcommand.setup;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.command.BWSubCommand;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.GameFile;
import dev.bsbedwars.it.utils.ItemFactory;
import dev.bsbedwars.it.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetUP extends BWSubCommand {

    @Override
    public void execute(Player player, String[] args) {

        if(args.length == 1 && args[0].equalsIgnoreCase("gui")) {
            new SetUPGUI(BedWars.getInstance().getArena()).open(player);
        }

        SetUPMode.setUPMode(player);
    }
}
