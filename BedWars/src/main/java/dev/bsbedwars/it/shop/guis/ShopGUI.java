package dev.bsbedwars.it.shop.guis;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.gui.AbstractGUI;
import dev.bsbedwars.it.shop.content.ShopCategory;
import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.GameFile;
import dev.bsbedwars.it.utils.ItemFactory;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Bed;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ShopGUI extends AbstractGUI {

    public ShopGUI(ShopCategory category, Player player) {
        super("Shop " + category.getId(), 54);
        category.getShopContent().forEach((slot, item) -> {
            setItem(item, slot);
        });
        category.getShopCategory().forEach((slot, name) -> {
            setItem(BedWars.getInstance().getShopProvider().getCategory(name).getItemDisplay(), slot);
        });
        category.getShopItems().forEach((slot, item) -> {
            try {
                setItem(
                        item.getItemDisplayName(player).setLore(
                                BedWars.getInstance().getShopProvider().getConfig().getStringList("itemLore.shopItem").stream()
                                        .map(s -> s.replace("%color%", item.getPriceType().getColor()))
                                        .map(s -> s.replace("%price%", String.valueOf(item.getPrice())))
                                        .map(s -> s.replace("%type%", item.getPriceType().toString()))
                                        .collect(Collectors.toList())).build(),
                        slot);
            }catch (NullPointerException e) {
                throw new NullPointerException("Item in slot " + slot + " is null!");
            }
        });
    }

    @Override
    public boolean onClick(int slot, ItemStack itemStack, Player player) {
        ShopCategory category = BedWars.getInstance().getShopProvider().getCategory(player.getOpenInventory().getTitle().split(" ")[1]);
        category.getShopItems().computeIfPresent(slot, (s, item) -> {
            item.getItemDisplayName(player);
            if(player.getInventory().contains(item.getPriceType().getMaterial(), item.getPrice())) {
                ItemStack it = Arrays.stream(player.getInventory().getContents())
                        .filter(Objects::nonNull)
                        .filter(i -> i.getType() == item.getPriceType().getMaterial() && i.getAmount() >= item.getPrice())
                        .findFirst().orElse(null);
                if(it == null) { // the item is split in plus slots
                    ChatUtils.sendMessage(player, new GameFile("messages.yml").getFileConfiguration(), "shop_denied", player.getName(), String.valueOf(item.getPrice()), item.getPriceType().getColor(), item.getPriceType().toString(), item.getId());
                    sound(player, Sound.VILLAGER_NO);
                    return item;
                }
                int futureAmount = it.getAmount() - item.getPrice();
                if(futureAmount == 0) {
                    it.setType(Material.AIR);
                    item.onClick(player);
                    return item;
                }
                it.setAmount(futureAmount);

                if(!item.onClick(player)) {
                    ChatUtils.sendMessage(player, new GameFile("messages.yml").getFileConfiguration(), "shop_error", player.getName(), String.valueOf(item.getPrice()), item.getPriceType().getColor(), item.getPriceType().toString(), item.getId());
                    sound(player, Sound.VILLAGER_NO);
                    return item;
                }
                ChatUtils.sendMessage(player, new GameFile("messages.yml").getFileConfiguration(), "shop_success", player.getName(), String.valueOf(item.getPrice()), item.getPriceType().getColor(), item.getPriceType().toString(), item.getPriceType().getMaterial().toString(), item.getId());
                sound(player, Sound.ORB_PICKUP);
                if(item.isRequireReload())
                    new ShopGUI(BedWars.getInstance().getShopProvider().getCategory(player.getOpenInventory().getTitle().split(" ")[1]), player).open(player);
                return item;
            }
            ChatUtils.sendMessage(player, new GameFile("messages.yml").getFileConfiguration(), "shop_denied", player.getName(), String.valueOf(item.getPrice()), item.getPriceType().getColor(), item.getPriceType().toString(), item.getId());
            sound(player, Sound.VILLAGER_NO);
            return item;
        });
        category.getShopCategory().computeIfPresent(slot, (s, item) -> {
            new ShopGUI(BedWars.getInstance().getShopProvider().getCategory(item), player).open(player);
            return item;
        });
        return true;
    }

}
