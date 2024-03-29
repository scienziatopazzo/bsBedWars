package dev.bsbedwars.it.commands.shop;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.shop.guis.ShopGUI;
import org.bukkit.entity.Player;

@CommandAlias("shop")
public class ShopCommand extends BaseCommand {

    @Default
    public void onShop(Player player) {
        new ShopGUI(BedWars.getInstance().getShopProvider().getCategory("QuickBuy"), player).open(player);
    }

}
