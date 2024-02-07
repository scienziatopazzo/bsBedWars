package dev.bsbedwars.it.jedis;

import com.avaje.ebeaninternal.server.lib.util.NotFoundException;
import com.google.common.base.Enums;
import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.Common;
import dev.bsbedwars.it.bedwars.PluginStatus;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.commands.bedwars.subcommand.setup.SetUPGUI;
import dev.bsbedwars.it.lobby.LobbyManager;
import org.bukkit.Bukkit;

import java.util.Set;
import java.util.UUID;

public class JedisChannel implements JedisManager.MessageListener {

    /*

        update --> Name;PluginStatus

     */


    @Override
    @SuppressWarnings("all")
    public void onMessageReceived(String channel, String message) {

        String[] split = message.split(";");

        if(split.length != 2) // Is a update for a status of an bedwars (We don't care)
            return;

        String lobbyUUID = split[0];

        PluginStatus status = Enums
                .getIfPresent(PluginStatus.class, split[1].toUpperCase().trim())
                .orNull();

        if(status == null)
            throw new NotFoundException("Invalid jedis message sended: " + split[1] + "! (1)");

        LobbyManager lobbyManager = BedWars.getInstance().getLobbyManager();

        if(status == PluginStatus.ENABLE) {
            lobbyManager.getLobbys().add(lobbyUUID);

            String bedwarsUUID = BedWars.getInstance().getBedwarsUUID();
            if(!channel.trim().equalsIgnoreCase(bedwarsUUID.toString())) { // is a msg from the lobby
                Bukkit.getScheduler().runTaskLater(BedWars.getInstance(), () -> {
                    Common common = BedWars.getInstance().getCommon();
                    common.getJedisManager()
                            .send(lobbyUUID, bedwarsUUID + ";" + "PLUGINSTATUS " + ";" + "ENABLE");
                    common.getJedisManager()
                            .send(lobbyUUID, bedwarsUUID + ";" + "STATUS" + ";" + "LOBBY");
                    common.getJedisManager()
                            .send(lobbyUUID, bedwarsUUID + ";" + "TYPE" + ";" + "SOLO");
                }, 20L);
            }
        }else {
            lobbyManager.getLobbys().remove(lobbyUUID);
        }

        BedWars.getInstance().getCommon().getLogger()
                .log(
                        true,
                        String.format(
                                "&d&l[DEBUG] &8(%s) &bNew %s &8--> &b%s",
                                lobbyUUID,
                                "PLUGINSTATUS",
                                status.toString()
                        )
                );

    }

    public void updateStatus(Status status) {
        if(SetUPGUI.on)
            Common.getInstance().getJedisManager()
                    .send("defChannel", BedWars.getInstance().getBedwarsUUID() + ";" + "STATUS" + ";" + status.toString());
    }

    public void updateType(Type type) {
        if(SetUPGUI.on)
            Common.getInstance().getJedisManager()
                    .send("defChannel", BedWars.getInstance().getBedwarsUUID() + ";" + "TYPE" + ";" + type.toString());
    }

    public void updatePluginStatus(PluginStatus pluginStatus) {
        if(SetUPGUI.on)
            Common.getInstance().getJedisManager()
                    .send("defChannel", BedWars.getInstance().getBedwarsUUID() + ";" + "PLUGINSTATUS" + ";" + pluginStatus.toString());
    }




}
