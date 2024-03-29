package dev.bsbedwars.it.npc;


import com.google.common.base.Enums;
import dev.bsbedwars.it.npc.managers.NPC;
import dev.bsbedwars.it.utils.LocationUtil;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class NPCManager {

    @Getter
    private static final HashMap<UUID, Usage> npcs = new HashMap<>();

    public static void create(Player player, FileConfiguration config) {
        ConfigurationSection selection = config.getConfigurationSection("villagers");
        for(String key : selection.getKeys(false)) {
            NPCManager.Usage usage = Enums.getIfPresent(NPCManager.Usage.class, key.toUpperCase()).orNull();
            ConfigurationSection selection2 = selection.getConfigurationSection(key);
            for(String key2 : selection2.getKeys(false)) {
                /*
                createShop(
                        player,
                        usage,
                        new LocationUtil(null).deserialize(selection2.getString(key2))
                );
                 */
            }
        }
    }

    public static void removeALL() {
        npcs.forEach((npc, usage) -> NPC.removeNPC(npc));
        npcs.clear();
    }

    private static void createShop(Player player, Usage usage, Location location) {
        UUID uuid = UUID.randomUUID();
        UUID npc = NPC.createNPC(player, "PLAYER", uuid.toString(), uuid.toString());
        NPC.teleportHereNPC(uuid, location);
        npcs.put(npc, usage);
    }




    public enum Usage {
        SHOP,
        UPGRADE;
    }
}
