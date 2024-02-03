package dev.bsbedwars.it.jedis;

import com.avaje.ebeaninternal.server.lib.util.NotFoundException;
import com.google.common.base.Enums;
import com.google.common.base.Objects;
import dev.bsbedwars.it.Lobby;
import dev.bsbedwars.it.bedwars.*;
import dev.bsbedwars.it.jedis.JedisManager;
import dev.bsbedwars.it.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

import javax.persistence.Lob;

public class JedisChannel implements JedisManager.MessageListener {

    /*

        update --> Name;Status;Status | Name;Type;Type | Name;PluginStatus;PluginStatus

     */


    @Override
    @SuppressWarnings("all")
    public void onMessageReceived(String channel, String message) {

        String[] split = message.split(";");

        if(split.length == 2) // Is a update for a status of an lobby (We don't care)
            return;

        String bedwarsUUID = split[0];

        MessageType messageType = Enums
                .getIfPresent(MessageType.class, split[1].toUpperCase().trim())
                .orNull();

        if(messageType == null)
            throw new NotFoundException("Invalid jedis message sended: " + split[1] + "! (1)");

        Object enumInizialized = Enums
                .getIfPresent(messageType.getEnumClass(), split[2].toUpperCase().trim())
                .orNull();

        if(enumInizialized == null)
            throw new NotFoundException("Invalid jedis message sended: " + split[2] + "! (2)");

        BedWarsManager manager = Lobby.getInstance().getBedWarsManager();

        BedWars bedWars = manager.getBedWars().stream()
                .filter(bw -> bw.getName().equalsIgnoreCase(bedwarsUUID))
                .findFirst()
                .orElse(null);


        Logger logger = Lobby.getInstance().getCommon().getLogger();

        switch (messageType) {
            case STATUS:
                if(bedWars == null)
                    throw new NotFoundException("Invalid jedis message sended: " + split[1] + "! (3)");
                bedWars.setStatus((Status) enumInizialized);
                break;
            case TYPE:
                if(bedWars == null)
                    throw new NotFoundException("Invalid jedis message sended: " + split[1] + "! (3)");
                bedWars.setType((Type) enumInizialized);
                break;
            case PLUGINSTATUS:
                if(enumInizialized == PluginStatus.ENABLE) {
                    manager.getBedWars().add(
                            new BedWars(bedwarsUUID, null, null)
                    );
                }else {
                    manager.getBedWars().removeIf(bw -> bw.getName().equalsIgnoreCase(bedwarsUUID));
                }
                break;
            default:
                throw new NotFoundException("Invalid jedis message sended: " + split[1] + "! (4)");
        }

        switch (messageType) {
            case PLUGINSTATUS:
                if(enumInizialized == PluginStatus.ENABLE) {
                    if(!channel.trim().equalsIgnoreCase(Lobby.getInstance().getLobbyUUID().toString()))
                        send(bedwarsUUID); // if do all in this, get error beause there is the break;
                }
        }

        if(Lobby.getInstance().getConfigFile().getFileConfiguration().isBoolean("debug")) {
            logger.log(
                    true,
                    String.format(
                            "&d&l[DEBUG] &8(%s) &bNew %s &8--> &b%s",
                            bedwarsUUID,
                            messageType.toString(),
                            enumInizialized.toString()
                    )
            );
        }





    }





    private void send(String bedwarsUUID) {
        Bukkit.getScheduler().runTaskLater(Lobby.getInstance(), () -> {
            Lobby.getInstance().getCommon().getJedisManager()
                    .send(bedwarsUUID.trim(), Lobby.getInstance().getLobbyUUID() + ";" + "ENABLE");
        }, 20L);
    }



}
