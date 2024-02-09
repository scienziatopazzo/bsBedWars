package dev.bsbedwars.it.hologram.event;

import dev.bsbedwars.it.utils.HologramFactory;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerHitHologram implements Listener {

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof ArmorStand) && !(event.getDamager() instanceof Player)) return;
        if(HologramFactory.HOLOGRAMS_CACHE.contains((ArmorStand) event.getDamager()))
            event.setCancelled(true);

    }

}
