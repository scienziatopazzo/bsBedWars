package dev.bsbedwars.it.npc;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.shop.guis.ShopGUI;
import net.citizensnpcs.api.event.CitizensGetSelectedNPCEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCListener implements Listener {


    @EventHandler
    public void onClick(NPCRightClickEvent event) {
        Player player = event.getClicker();
        NPC npc = event.getNPC();
        NPCManager.Usage usage = NPCManager.getNpcs().get(npc);
        if(usage == NPCManager.Usage.SHOP)
            new ShopGUI(BedWars.getInstance().getShopProvider().getCategory("main"), player).open(player);
        if(usage == NPCManager.Usage.UPGRADE)
            new ShopGUI(BedWars.getInstance().getShopProvider().getCategory("upgrade"), player).open(player);
    }
}
