package dev.bsbedwars.it.team.component.tools;

import dev.bsbedwars.it.shop.content.ShopPrice;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class AxeUtil {


    public static Material getAxe(Player player) {
        Material[] pickaxeMaterials = {Material.WOOD_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE};

        for (Material material : pickaxeMaterials)
            if (player.getInventory().contains(material))
                return material;

        return null;
    }


    public static String getName(Material material) {
        switch (material) {
            case DIAMOND_AXE:
                return "Axe (Efficiency 3)";
            case GOLD_AXE:
                return "Gold Axe (Efficiency 2)";
            case IRON_AXE:
                return "Iron Axe (Efficiency 1)";
            case WOOD_AXE:
                return "Wood Axe";
            default:
                return "Unknown";
        }
    }

    public static int costPrice(Material material) {
        switch (material) {
            case DIAMOND_AXE:
                return 6;
            case GOLD_AXE:
                return 3;
            case IRON_AXE:
            case WOOD_AXE:
                return 10;
            default:
                return 0;
        }
    }

    public static ShopPrice costType(Material material) {
        switch (material) {
            case DIAMOND_AXE:
            case GOLD_AXE:
                return ShopPrice.GOLD;
            default:
                return ShopPrice.IRON;
        }
    }

    public static int getEnchant(Material material) {
        switch (material) {
            case DIAMOND_AXE:
                return 3;
            case GOLD_AXE:
                return 2;
            case IRON_AXE:
                return 1;
            default:
                return 0;
        }
    }

    public static Material getNextAxe(Player player) {
        Material currentAxe = getAxe(player);

        if (currentAxe != null) {
            int currentAmount = player.getInventory().all(currentAxe).values().stream().mapToInt(ItemStack::getAmount).sum();
            Material[] pickaxeMaterials = {Material.WOOD_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE};
            int nextIndex = Arrays.asList(pickaxeMaterials).indexOf(currentAxe) + currentAmount;

            if (nextIndex < pickaxeMaterials.length) {
                return pickaxeMaterials[nextIndex];
            } else {
                return Material.AIR;
            }
        }

        return Material.WOOD_AXE;
    }


    public static Material getPreviousAxe(Player player) {
        Material currentAxe = getAxe(player);

        if (currentAxe != Material.AIR) {
            int currentAmount = player.getInventory().all(currentAxe).values().stream().mapToInt(ItemStack::getAmount).sum();
            Material[] pickaxeMaterials = {Material.WOOD_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE};
            int previousIndex = Arrays.asList(pickaxeMaterials).indexOf(currentAxe) - currentAmount;

            if (previousIndex >= 0) {
                return pickaxeMaterials[previousIndex];
            } else {
                return Material.AIR;
            }
        }

        return Material.DIAMOND_AXE;
    }
}
