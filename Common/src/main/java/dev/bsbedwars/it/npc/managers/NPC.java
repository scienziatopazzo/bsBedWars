package dev.bsbedwars.it.npc.managers;

import com.mojang.authlib.GameProfile;
import dev.bsbedwars.it.Common;
import dev.bsbedwars.it.npc.enums.NPCType;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NPC {

    @Getter
    private static final Map<UUID, EntityPlayer> npcs = new HashMap<>();

    public static UUID createNPC(Player player, String npcType, String npcId, String name) {
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) player.getWorld()).getHandle();
        PlayerInteractManager playerInteractManager = new PlayerInteractManager(nmsWorld);
        UUID uuid = UUID.fromString(npcId);
        GameProfile gameProfile = new GameProfile(uuid, name);
        EntityPlayer npc;

        switch (NPCType.valueOf(npcType.toUpperCase())) {
            case PLAYER:
            default:
                npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, playerInteractManager);
                break;
        }

        npc.setLocation(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(),
                player.getLocation().getYaw(), player.getLocation().getPitch());

        Player npcPlayer = npc.getBukkitEntity().getPlayer();
        npcPlayer.setPlayerListName(name);
        npcPlayer.setGameMode(GameMode.CREATIVE);

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));

        npcs.put(npc.getUniqueID(), npc);
        return uuid;
    }

    public static void removeNPC(UUID npcUUID) {
        EntityPlayer npc = npcs.remove(npcUUID);
        if (npc != null) {
            PlayerConnection connection = ((CraftPlayer) Bukkit.getOnlinePlayers().iterator().next()).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
        }
    }

    public static void teleportHereNPC(UUID npcUUID, Location location) {
        EntityPlayer npc = npcs.get(npcUUID);
        if (npc != null)
            npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

    }
}
