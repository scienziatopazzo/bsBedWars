package dev.bsbedwars.it.commands.stop;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.entity.Player;

@CommandAlias("stop")
@CommandPermission("bedwars.admin")
public class StopCommand extends BaseCommand {


    @Default
    public boolean onStop(Player player) {
        Arena arena = BedWars.getInstance().getArena();

        if(arena.getStatus() == Status.LOBBY) {
            player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&cThis is not started!"));
            return true;
        }

        arena.stop();
        player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&cGame Stopped!"));
        return true;
    }
}

