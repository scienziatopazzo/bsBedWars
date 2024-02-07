package dev.bsbedwars.it.commands.gamemode;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@CommandAlias("gmsp")
@CommandPermission("bedwars.admin")
public class GMSP extends BaseCommand {


    @Default
    public void onSpectator(Player player) {
        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&aSpectator mode enabled!"));
    }

}
