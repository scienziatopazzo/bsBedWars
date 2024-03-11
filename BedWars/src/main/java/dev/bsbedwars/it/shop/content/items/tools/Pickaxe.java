package dev.bsbedwars.it.shop.content.items.tools;

import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.team.component.tools.PickaxeUtil;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class Pickaxe extends ShopItem {

    public Pickaxe() {
        super(
                "Pickaxe",
                null,
                0,
                true
        );
    }

    @Override
    public ItemFactory getItemDisplayName(Player player) {
        Material material = PickaxeUtil.getNextPickaxe(player) == Material.AIR ? Material.DIAMOND_PICKAXE : PickaxeUtil.getNextPickaxe(player);
        setPrice(PickaxeUtil.costPrice(material));
        setPriceType(PickaxeUtil.costType(material));
        return new ItemFactory(material).name("&b" + PickaxeUtil.getName(material)).addEnchant(Enchantment.DIG_SPEED, PickaxeUtil.getEnchant(material)).hideEnchant(true).setUnbreakable(true);
    }

    @Override
    public boolean onClick(Player player) {
        Material material = PickaxeUtil.getPickaxe(player);
        Material nextMaterial = PickaxeUtil.getNextPickaxe(player);
        if(nextMaterial == Material.AIR)
            return false;
        if (material != null)
            player.getInventory().remove(material);
        player.getInventory().addItem(new ItemFactory(nextMaterial).name("&b" + PickaxeUtil.getName(nextMaterial)).addEnchant(Enchantment.DIG_SPEED, PickaxeUtil.getEnchant(nextMaterial)).setUnbreakable(true).build());
        return true;
    }



}

