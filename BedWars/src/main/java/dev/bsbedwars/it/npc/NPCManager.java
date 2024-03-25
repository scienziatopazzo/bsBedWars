package dev.bsbedwars.it.npc;

import com.google.common.base.Enums;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.LocationUtil;
import lombok.Getter;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.List;

public class NPCManager {

    @Getter
    private static final HashMap<NPC, Usage> npcs = new HashMap<>();

    public static void create(FileConfiguration config) {
        ConfigurationSection selection = config.getConfigurationSection("villagers");
        for(String key : selection.getKeys(false)) {
            NPCManager.Usage usage = Enums.getIfPresent(NPCManager.Usage.class, key.toUpperCase()).orNull();
            ConfigurationSection selection2 = selection.getConfigurationSection(key);
            for(String key2 : selection2.getKeys(false)) {
                createShop(
                        usage,
                        new LocationUtil(null).deserialize(selection2.getString(key2))
                );
            }
        }
    }

    public static void removeALL() {
        npcs.forEach((npc, usage) -> npc.destroy());
        npcs.clear();
    }

    private static void createShop(Usage usage, Location location) {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, ChatUtils.makeLowercaseExceptFirst(usage.toString()), location);
        npc.setAlwaysUseNameHologram(true);
        npc.setName(ChatUtils.color("&e&lTEAM &b&l" + usage.toString()));
        npc.spawn(location);
        npc.setProtected(true);
        npc.setUseMinecraftAI(false);
        npc.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
        npcs.put(npc, usage);
    }




    public enum Usage {
        SHOP,
        UPGRADE;
    }
}
