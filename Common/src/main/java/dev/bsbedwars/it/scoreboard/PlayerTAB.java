package dev.bsbedwars.it.scoreboard;

import dev.bsbedwars.it.Common;
import dev.bsbedwars.it.utils.ChatUtils;
import lombok.Getter;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Getter
public class PlayerTAB {

    @Getter
    private static final HashMap<Player, PlayerTAB> players = new HashMap<>();

    private static final String VERSION;

    private final Player player;
    private String customDisplayName;
    private boolean useCustomDisplayName;
    private List<String> header;
    private List<String> footer;

    public PlayerTAB(Player player) {
        this.player = player;
        this.customDisplayName = "";
        this.useCustomDisplayName = false;
        players.put(player, this);
    }

    public void updateCustomDisplayName(String customDisplayName) {
        this.customDisplayName = customDisplayName;
        update();
    }

    public void updateUseCustomDisplayName(boolean useCustomDisplayName) {
        this.useCustomDisplayName = useCustomDisplayName;
        update();
    }

    public void updateHeader(List<String> header) {
        this.header = header;
        update();
    }


    public void updateFooter(List<String> footer) {
        this.footer = footer;
        update();
    }

    private void update() {
        if(useCustomDisplayName) {
            player.setPlayerListName(ChatUtils.color(customDisplayName) + player.getName());
        } else {
            if(Common.getInstance().getMain().getServer().getPluginManager().isPluginEnabled("LuckPerms"))
                player.setPlayerListName(ChatUtils.color(getPlayerPrefix() + player.getName()));
        }
        String headerString = (header != null) ? String.join("\n", header) : "";
        String footerString = (footer != null) ? String.join("\n", footer) : "";
        setTabHeaderFooter(headerString, footerString);
    }

    private void setTabHeaderFooter(String header, String footer) {
        try {
            Class<?> packetClass = Class.forName("net.minecraft.server." + VERSION + ".PacketPlayOutPlayerListHeaderFooter");
            Constructor<?> packetConstructor = packetClass.getDeclaredConstructor();
            Object packet = packetConstructor.newInstance();

            Class<?> chatComponentTextClass = Class.forName("net.minecraft.server." + VERSION + ".ChatComponentText");
            Constructor<?> chatComponentTextConstructor = chatComponentTextClass.getDeclaredConstructor(String.class);
            Object headerText = chatComponentTextConstructor.newInstance(ChatUtils.color(header));
            Object footerText = chatComponentTextConstructor.newInstance(ChatUtils.color(footer));

            Field headerField = packetClass.getDeclaredField("a");
            Field footerField = packetClass.getDeclaredField("b");
            headerField.setAccessible(true);
            footerField.setAccessible(true);

            headerField.set(packet, headerText);
            footerField.set(packet, footerText);

            Object craftPlayer = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = craftPlayer.getClass().getField("playerConnection").get(craftPlayer);
            playerConnection.getClass().getMethod("sendPacket", Class.forName("net.minecraft.server." + VERSION + ".Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPlayerPrefix() {
        final CachedMetaData metaData = Common.getInstance().getLuckPerms().getPlayerAdapter(Player.class).getMetaData(player);
        return ChatUtils.color(metaData.getPrefix() != null ? metaData.getPrefix() : "");
    }


    public static PlayerTAB getOrCreate(Player player) {
        return players.getOrDefault(player, new PlayerTAB(player));
    }

    public void delete() {
        players.remove(player);
    }

    static {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        VERSION = packageName.substring(packageName.lastIndexOf('.') + 1);
    }

}
