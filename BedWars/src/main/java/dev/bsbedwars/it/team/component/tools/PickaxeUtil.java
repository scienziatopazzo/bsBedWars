package dev.bsbedwars.it.team.component.tools;

import dev.bsbedwars.it.shop.content.ShopPrice;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class PickaxeUtil {


    public static Material getPickaxe(Player player) {
        Material[] pickaxeMaterials = {Material.WOOD_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE};

        for (Material material : pickaxeMaterials)
            if (player.getInventory().contains(material))
                return material;

        return null;
    }


    public static String getName(Material material) {
        switch (material) {
            case DIAMOND_PICKAXE:
                return "Pickaxe (Efficiency 3)";
            case GOLD_PICKAXE:
                return "Gold Pickaxe (Efficiency 2)";
            case IRON_PICKAXE:
                return "Iron Pickaxe (Efficiency 1)";
            case WOOD_PICKAXE:
                return "Wood Pickaxe";
            default:
                return "Unknown";
        }
    }

    public static int costPrice(Material material) {
        switch (material) {
            case DIAMOND_PICKAXE:
                return 6;
            case GOLD_PICKAXE:
                return 3;
            case IRON_PICKAXE:
            case WOOD_PICKAXE:
                return 10;
            default:
                return 0;
        }
    }

    public static ShopPrice costType(Material material) {
        switch (material) {
            case DIAMOND_PICKAXE:
            case GOLD_PICKAXE:
                return ShopPrice.GOLD;
            default:
                return ShopPrice.IRON;
        }
    }

    public static int getEnchant(Material material) {
        switch (material) {
            case DIAMOND_PICKAXE:
                return 3;
            case GOLD_PICKAXE:
                return 2;
            case IRON_PICKAXE:
                return 1;
            default:
                return 0;
        }
    }

    public static Material getNextPickaxe(Player player) {
        Material currentPickaxe = getPickaxe(player);

        if (currentPickaxe != null) {
            int currentAmount = player.getInventory().all(currentPickaxe).values().stream().mapToInt(ItemStack::getAmount).sum();
            Material[] pickaxeMaterials = {Material.WOOD_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE};
            int nextIndex = Arrays.asList(pickaxeMaterials).indexOf(currentPickaxe) + currentAmount;

            if (nextIndex < pickaxeMaterials.length) {
                return pickaxeMaterials[nextIndex];
            } else {
                return Material.AIR;
            }
        }

        return Material.WOOD_PICKAXE;
    }


    public static Material getPreviousPickaxe(Player player) {
        Material currentPickaxe = getPickaxe(player);

        if (currentPickaxe != Material.AIR) {
            int currentAmount = player.getInventory().all(currentPickaxe).values().stream().mapToInt(ItemStack::getAmount).sum();
            Material[] pickaxeMaterials = {Material.WOOD_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE};
            int previousIndex = Arrays.asList(pickaxeMaterials).indexOf(currentPickaxe) - currentAmount;

            if (previousIndex >= 0) {
                return pickaxeMaterials[previousIndex];
            } else {
                return Material.AIR;
            }
        }

        return Material.DIAMOND_PICKAXE;
    }

}
