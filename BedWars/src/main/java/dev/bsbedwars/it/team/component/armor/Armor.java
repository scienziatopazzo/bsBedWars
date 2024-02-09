package dev.bsbedwars.it.team.component.armor;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.team.Team;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.ItemFactory;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.UnhandledException;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
public enum Armor {
    LEATHER(new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS)),
    IRON(new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)),
    DIAMOND(new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.DIAMOND_BOOTS));

    private final ItemStack chestplate;
    private final ItemStack leggings;
    private final ItemStack boots;

 
    public static void updatePlayerArmor(Player player, Armor lastArmor) {
        Team gameTeam = BedWars.getInstance().getArena().getTeam(player);
        Armor armor = null;
        if(lastArmor == Armor.LEATHER)
            armor = Armor.LEATHER;
        if(lastArmor == Armor.IRON)
            armor = Armor.IRON;
        if(lastArmor == Armor.DIAMOND)
            armor = Armor.LEATHER;
        player.getInventory().setHelmet(getColoredArmor(player, armor.getSetupItem(new ItemStack(Material.LEATHER_HELMET), gameTeam)));
        player.getInventory().setChestplate(getColoredArmor(player, armor.getSetupItem(new ItemStack(Material.LEATHER_CHESTPLATE), gameTeam)));
        player.getInventory().setLeggings(getColoredArmor(player, armor.getLeggings(gameTeam)));
        player.getInventory().setBoots(getColoredArmor(player, armor.getBoots(gameTeam)));
    }

    private static ItemStack getColoredArmor(Player player, ItemStack itemStack) {
        Team team = BedWars.getInstance().getArena().getTeam(player);
        if (!itemStack.getType().name().contains("LEATHER")) return itemStack;
        return new ItemFactory(itemStack).setLeatherColor(team.getColor().getColor()).build();
    }

    public static void giveWoodSword(Player player) {
        Team team = BedWars.getInstance().getArena().getTeam(player);
        player.getInventory().setItem(0, new ItemFactory(new ItemStack(Material.WOOD_SWORD)).name(ChatColor.GREEN + "Spada di Legno").setUnbreakable(true).build());
    }

    public static void updateSword(Player player) {
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            ItemStack itemStack = player.getInventory().getContents()[i];
            if(itemStack == null) continue;
            if(!itemStack.getType().toString().contains("SWORD")) continue;
            if(itemStack.getType() == Material.DIAMOND_SWORD || itemStack.getType() == Material.IRON_SWORD)
                player.getInventory().setContents(
                        Arrays.asList(player.getInventory().getContents()).stream()
                                .filter(Objects::nonNull) // else do nullpointer exception
                                .filter(it -> it.getType() != Material.WOOD_SWORD)
                                .toArray(ItemStack[]::new)
                );
            Team team = BedWars.getInstance().getArena().getTeam(player);
            try {
                ItemFactory itemFactory = new ItemFactory(player.getInventory().getItem(i));
                if (team.getTeamUpgrade().getIntUpgrade("sharpness") != 0)
                    itemFactory.addEnchant(Enchantment.DAMAGE_ALL, team.getTeamUpgrade().getIntUpgrade("sharpness"));
                itemFactory.setUnbreakable(true);
                player.getInventory().setItem(i, itemFactory.build());
            }catch (UnhandledException e) {
                // I know but is not important
            }
        }
    }

    private ItemStack getChestPlate(Team team) {
        return getSetupItem(chestplate.clone(), team);
    }

    private ItemStack getLeggings(Team team) {
        return getSetupItem(leggings.clone(), team);
    }

    private ItemStack getBoots(Team team) {
        return getSetupItem(boots.clone(), team);
    }

    private ItemStack getSetupItem(ItemStack itemStack, Team team) {
        ItemFactory itemFactory = new ItemFactory(itemStack).setUnbreakable(true).name(ChatUtils.color("&aArmor"));
        if (team.getTeamUpgrade().getIntUpgrade("protection") != 0)
            itemFactory.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, team.getTeamUpgrade().getIntUpgrade("protection"));
        return itemFactory.build();
    }

    public static Armor getArmor(Player player) {
        ItemStack leggins = player.getInventory().getLeggings();

        if(leggins == null)
            return Armor.LEATHER;

        Material material = leggins.getType();

        if(material == null)
            return Armor.LEATHER;

        if (material.name().contains("LEATHER"))
            return Armor.LEATHER;
        if (material.name().contains("IRON"))
            return Armor.IRON;
        if (material.name().contains("DIAMOND"))
            return Armor.DIAMOND;

        return Armor.LEATHER;
    }



}
