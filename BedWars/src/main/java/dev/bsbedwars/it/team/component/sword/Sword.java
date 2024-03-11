package dev.bsbedwars.it.team.component.sword;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.team.Team;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.ItemFactory;
import org.apache.commons.lang.UnhandledException;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Objects;

public class Sword {

    public static void giveWoodSword(Player player) {
        player.getInventory().setItem(0, new ItemFactory(new ItemStack(Material.WOOD_SWORD)).name(ChatUtils.color("&bWood Sword")).setUnbreakable(true).build());
    }


    public static void updateSword(Player player) {
        ItemStack[] newContent = player.getInventory().getContents();

        for (int i = 0; i < newContent.length; i++) {
            ItemStack itemStack = newContent[i];
            if (itemStack == null) continue;
            if (!itemStack.getType().toString().contains("SWORD")) continue;
            if (itemStack.getType() == Material.DIAMOND_SWORD || itemStack.getType() == Material.IRON_SWORD || itemStack.getType() == Material.STONE_SWORD) {
                newContent = Arrays.asList(newContent).stream()
                        .peek(it -> {
                            if(it != null) {
                                Material swordType = it.getType();
                                if (    swordType == Material.WOOD_SWORD ||
                                        (itemStack.getType() == Material.IRON_SWORD && (swordType == Material.STONE_SWORD)) ||
                                        (itemStack.getType() == Material.DIAMOND_SWORD && (swordType == Material.IRON_SWORD || swordType == Material.STONE_SWORD))) {
                                    it.setType(Material.AIR);
                                }
                            }
                        })
                        .toArray(ItemStack[]::new);
            }

            Team team = BedWars.getInstance().getArena().getTeam(player);
            try {
                ItemFactory itemFactory = new ItemFactory(itemStack);
                if (team.getTeamUpgrade().getIntUpgrade("sharpness") != 0)
                    itemFactory.addEnchant(Enchantment.DAMAGE_ALL, team.getTeamUpgrade().getIntUpgrade("sharpness"));
                itemFactory.setUnbreakable(true);
                newContent[i] = itemFactory.build();
            } catch (UnhandledException | ConcurrentModificationException ignored) { }
        }

        player.getInventory().setContents(newContent);
    }


}
