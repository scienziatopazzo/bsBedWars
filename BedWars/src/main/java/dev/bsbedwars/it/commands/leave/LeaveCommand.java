package dev.bsbedwars.it.commands.leave;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import dev.bsbedwars.it.BedWars;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CommandAlias("leave|l")
public class LeaveCommand extends BaseCommand {

    @Default
    public boolean onLeave(Player player) {

        List<String> lobbys = new ArrayList<>(BedWars.getInstance().getLobbyManager().getLobbys());
        Collections.shuffle(lobbys);

        BedWars.getInstance().getCommon().getBungeeApi().connect(player, lobbys.stream().findFirst().orElse(null));

        return true;
    }

}
