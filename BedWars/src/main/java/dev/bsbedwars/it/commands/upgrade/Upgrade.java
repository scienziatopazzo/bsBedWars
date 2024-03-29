package dev.bsbedwars.it.commands.upgrade;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.shop.guis.ShopGUI;
import org.bukkit.entity.Player;

@CommandAlias("upgrade")
public class Upgrade extends BaseCommand {

    @Default
    public void onUpgrade(Player player) {
        new ShopGUI(BedWars.getInstance().getShopProvider().getCategory("Upgrades"), player).open(player);
    }

}
