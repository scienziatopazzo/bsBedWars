package dev.bsbedwars.it;


import dev.bsbedwars.it.gui.OnClickGUI;
import dev.bsbedwars.it.jedis.JedisManager;
import dev.bsbedwars.it.utils.BungeeChannelApi;
import dev.bsbedwars.it.utils.Logger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Common  {

    @Getter
    private static Common instance;

    private final JavaPlugin main;
    private final JedisManager jedisManager;
    private final String prefix;
    private final Logger logger;
    private final BungeeChannelApi bungeeApi;

    public Common(JavaPlugin main) {
        instance = this;
        this.main = main;
        this.prefix = "&8[&bbsBedwars&8] &a";
        this.logger = new Logger();

        this.jedisManager = new JedisManager("127.0.0.1", 6379);
        Bukkit.getPluginManager().registerEvents(new OnClickGUI(), main);

        this.bungeeApi = BungeeChannelApi.of(main);

    }

}
