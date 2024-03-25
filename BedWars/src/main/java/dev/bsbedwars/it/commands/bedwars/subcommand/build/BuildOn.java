package dev.bsbedwars.it.commands.bedwars.subcommand.build;

import dev.bsbedwars.it.command.BWSubCommand;
import dev.bsbedwars.it.event.imp.block.BuildManager;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.entity.Player;

public class BuildOn extends BWSubCommand {

    @Override
    public void execute(Player player, String[] args) {
        BuildManager.getEnableBuild().add(player);
        send(player, ChatUtils.prefix() + "&aBuild enabled!");
    }

}
