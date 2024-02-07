package dev.bsbedwars.it.commands.fake;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.commands.bedwars.subcommand.setup.SetUP;
import dev.bsbedwars.it.commands.bedwars.subcommand.setup.SetUPGUI;
import dev.bsbedwars.it.utils.ChatUtils;
import org.bukkit.entity.Player;

@CommandAlias("fakeoff")
@CommandPermission("bedwars.admin")
public class FakeOFFCommand extends BaseCommand {


    @Default
    public boolean onFakeOff(Player player) {
        BedWars.getInstance().getCommon().getJedisManager()
                .send("defChannel", BedWars.getInstance().getBedwarsUUID() + ";" + "PLUGINSTATUS " + ";" + "DISABLE");
        SetUPGUI.on = false;
        player.sendMessage(ChatUtils.color(ChatUtils.prefix() + "&aBedWars are now not visible!"));
        return true;
    }
}
