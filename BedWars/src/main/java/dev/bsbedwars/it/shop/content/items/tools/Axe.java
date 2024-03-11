package dev.bsbedwars.it.shop.content.items.tools;

import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.team.component.tools.AxeUtil;
import dev.bsbedwars.it.team.component.tools.AxeUtil;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class Axe extends ShopItem {

    public Axe() {
        super(
                "Axe",
                null,
                0,
                true
        );
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        Material material = AxeUtil.getNextAxe(player) == Material.AIR ? Material.DIAMOND_AXE : AxeUtil.getNextAxe(player);
        setPrice(AxeUtil.costPrice(material));
        setPriceType(AxeUtil.costType(material));
        return new ItemFactory(material).name("&b" + AxeUtil.getName(material)).addEnchant(Enchantment.DIG_SPEED, AxeUtil.getEnchant(material)).hideEnchant(true).setUnbreakable(true);
    }

    @Override
    public boolean onClick(Player player) {
        Material material = AxeUtil.getAxe(player);
        Material nextMaterial = AxeUtil.getNextAxe(player);
        if(nextMaterial == Material.AIR)
            return false;
        if (material != null)
            player.getInventory().remove(material);
        player.getInventory().addItem(new ItemFactory(nextMaterial).name("&b" + AxeUtil.getName(nextMaterial)).addEnchant(Enchantment.DIG_SPEED, AxeUtil.getEnchant(nextMaterial)).setUnbreakable(true).build());
        return true;
    }


}

