package dev.bsbedwars.it.commands.fly;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@CommandAlias("fly")
@CommandPermission("bedwars.admin")
public class FlyCommand extends BaseCommand {


    @Default
    public void onFly(Player player) {
        player.setFlying(!player.isFlying());
        player.setFlySpeed(2);
        player.setAllowFlight(!player.isFlying());
        player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&aFly " + (player.isFlying() ? "&aenabled" : "&cdisabled") + "!"));
    }

}