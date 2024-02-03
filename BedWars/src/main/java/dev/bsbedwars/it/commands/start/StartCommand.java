package dev.bsbedwars.it.commands.start;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("start")
public class StartCommand extends BaseCommand {


    @Default
    public boolean onStart(Player player) {
        Arena arena = BedWars.getInstance().getArena();

        if(arena.getStatus() != Status.LOBBY) {
            player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&cThis is already started!"));
            return true;
        }

        if (arena.getPlayers().size() < arena.getType().getMinPlayers()) {
            player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&cNot enough players!"));
            return true;
        }

        arena.start();
        player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&bGame starting!"));
        return true;
    }
}
