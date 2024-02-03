package dev.bsbedwars.it.command;

import dev.bsbedwars.it.utils.ChatUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@Getter
public abstract class BWSubCommand {


    public abstract void execute(Player player, String[] args);


    public void send(Player player, String message) {
        player.sendMessage(ChatUtils.color(message));
    }



}
