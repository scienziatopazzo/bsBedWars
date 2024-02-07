package dev.bsbedwars.it.commands.gamemode;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@CommandAlias("gmc")
@CommandPermission("bedwars.admin")
public class GMC extends BaseCommand {


    @Default
    public void onCreative(Player player) {
        player.setGameMode(GameMode.CREATIVE);
        player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&aCreative mode enabled!"));
    }

}

