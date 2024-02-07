package dev.bsbedwars.it.commands.fake;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.commands.bedwars.subcommand.setup.SetUPGUI;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.entity.Player;

@CommandAlias("redoon")
@CommandPermission("bedwars.admin")
public class RedoONCommand extends BaseCommand {


    @Default
    public boolean onRedoON(Player player) {
        // Reload BedWars...
        BedWars.getInstance().getCommon().getJedisManager()
                .send("defChannel", BedWars.getInstance().getBedwarsUUID() + ";" + "PLUGINSTATUS " + ";" + "ENABLE");
        BedWars.getInstance().getArena().setStatus(BedWars.getInstance().getArena().getStatus());
        BedWars.getInstance().getArena().setType(BedWars.getInstance().getArena().getType());
        SetUPGUI.on = true;
        player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&aBedWars are now visible!"));
        return true;
    }
}
