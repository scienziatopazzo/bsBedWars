package dev.bsbedwars.it.commands.gamemode;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@CommandAlias("gms")
@CommandPermission("bedwars.admin")
public class GMS extends BaseCommand {


    @Default
    public void onSurvival(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&aSurvival mode enabled!"));
    }

}
