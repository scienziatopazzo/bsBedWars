package dev.bsbedwars.it.shop.guis;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.bedwars.Type;
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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        category.getUpgradeItems().forEach((slot, item) -> {
            try {
                Integer[] prices = item.getPrices().get(BedWars.getInstance().getArena().getType());
                Integer[] newPrices = Stream.concat(Stream.of(0), Arrays.stream(prices))
                        .toArray(Integer[]::new);
                int level = item.getLevel(BedWars.getInstance().getArena().getTeam(player));
                int price = newPrices[level];
                List<String> lore = new ArrayList<>();
                for(String loreplus : item.getLines())
                    lore.add("&7" + loreplus);
                lore.add("");
                /*
                if(prices.length == 1) {
                    lore.add("&7Cost: &b" + prices[0] + " Diamonds");
                } else {
                    for (int i = 0; i < prices.length; i++) {
                        if(price >= prices[i]) {
                            lore.add("&7&mLevel &f&m" + intToRoman(i + 1) + " &7&m-  &7&mCost: &b&m" + prices[i] + " Diamonds");
                            continue;
                        }
                        lore.add("&7Level &f" + intToRoman(i + 1) + " &7-  &7Cost: &b" + prices[i] + " Diamonds");
                    }
                }
                 */
                for (int i = 0; i < newPrices.length; i++) {
                    if(i == 0) continue;
                    if(price >= newPrices[i]) {
                        if(newPrices.length == 2) {
                            lore.add("&7&mCost: &b&m" + newPrices[i] + " Diamonds");
                        }else {
                            lore.add("&7&mLevel &f&m" + intToRoman(i) + " &7&m- &7&mCost: &b&m" + newPrices[i] + " Diamonds");
                        }
                        continue;
                    }
                    if(newPrices.length == 2) {
                        lore.add("&7Cost: &b" + newPrices[i] + " Diamonds");
                    }else {
                        lore.add("&7Level &f" + intToRoman(i) + " &7- &7Cost: &b" + newPrices[i] + " Diamonds");
                    }
                }
                lore.add("");
                setItem(
                        new ItemFactory(item.getDisplayMaterial()).name("&a" + item.getId() + " " + intToRoman(level)).setLore(lore).build(),
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
                    HashMap<String, String> placeholder = new HashMap<>();
                    placeholder.put("player", player.getName());
                    placeholder.put("price", String.valueOf(item.getPrice()));
                    placeholder.put("price_type_color", item.getPriceType().getColor());
                    placeholder.put("price_type", item.getPriceType().toString());
                    placeholder.put("id", item.getId());
                    ChatUtils.sendMessage(player, new GameFile("messages.yml").getFileConfiguration(), "shop_denied", placeholder);
                    sound(player, Sound.VILLAGER_NO);
                    return item;
                }
                int futureAmount = it.getAmount() - item.getPrice();
                if(futureAmount <= 0) {
                    it.setType(Material.AIR);
                    item.onClick(player);
                    return item;
                } else {
                    it.setAmount(futureAmount);
                }

                if(!item.onClick(player)) {
                    HashMap<String, String> placeholder = new HashMap<>();
                    placeholder.put("player", player.getName());
                    placeholder.put("price", String.valueOf(item.getPrice()));
                    placeholder.put("price_type_color", item.getPriceType().getColor());
                    placeholder.put("price_type", item.getPriceType().toString());
                    placeholder.put("id", item.getId());
                    ChatUtils.sendMessage(player, new GameFile("messages.yml").getFileConfiguration(), "shop_error", placeholder);
                    sound(player, Sound.VILLAGER_NO);
                    return item;
                }
                HashMap<String, String> placeholder = new HashMap<>();
                placeholder.put("player", player.getName());
                placeholder.put("price", String.valueOf(item.getPrice()));
                placeholder.put("price_type_color", item.getPriceType().getColor());
                placeholder.put("price_type_material", item.getPriceType().getMaterial().toString());
                placeholder.put("price_type", item.getPriceType().toString());
                placeholder.put("id", item.getId());
                ChatUtils.sendMessage(player, new GameFile("messages.yml").getFileConfiguration(), "shop_success", placeholder);
                sound(player, Sound.ORB_PICKUP);
                if(item.isRequireReload())
                    new ShopGUI(BedWars.getInstance().getShopProvider().getCategory(player.getOpenInventory().getTitle().split(" ")[1]), player).open(player);
                return item;
            }
            HashMap<String, String> placeholder = new HashMap<>();
            placeholder.put("player", player.getName());
            placeholder.put("price", String.valueOf(item.getPrice()));
            placeholder.put("price_type_color", item.getPriceType().getColor());
            placeholder.put("price_type", item.getPriceType().toString());
            placeholder.put("id", item.getId());
            ChatUtils.sendMessage(player, new GameFile("messages.yml").getFileConfiguration(), "shop_denied", placeholder);
            sound(player, Sound.VILLAGER_NO);
            return item;
        });
        category.getUpgradeItems().computeIfPresent(slot, (s, item) -> {
            Type type = BedWars.getInstance().getArena().getType();
            int price = 0;
            try {
                Integer[] prices = item.getPrices().get(type);
                price = prices[item.getLevel(BedWars.getInstance().getArena().getTeam(player))];
            }catch (ArrayIndexOutOfBoundsException e) {
                HashMap<String, String> placeholder = new HashMap<>();
                placeholder.put("player", player.getName());
                placeholder.put("price", String.valueOf(price));
                placeholder.put("price_type_color", "&b");
                placeholder.put("price_type", String.valueOf(Material.DIAMOND));
                placeholder.put("id", item.getId());
                ChatUtils.sendMessage(player, new GameFile("messages.yml").getFileConfiguration(), "shop_error", placeholder);
                sound(player, Sound.VILLAGER_NO);
                return item;
            }
            if(player.getInventory().contains(Material.DIAMOND, price)) {
                int finalPrice = price;
                ItemStack it = Arrays.stream(player.getInventory().getContents())
                        .filter(Objects::nonNull)
                        .filter(i -> i.getType() == Material.DIAMOND && i.getAmount() >= finalPrice)
                        .findFirst().orElse(null);
                if(it == null) { // the item is split in plus slots
                    HashMap<String, String> placeholder = new HashMap<>();
                    placeholder.put("player", player.getName());
                    placeholder.put("price", String.valueOf(price));
                    placeholder.put("price_type_color", "&b");
                    placeholder.put("price_type", String.valueOf(Material.DIAMOND));
                    placeholder.put("id", item.getId());
                    ChatUtils.sendMessage(player, new GameFile("messages.yml").getFileConfiguration(), "shop_denied", placeholder);
                    sound(player, Sound.VILLAGER_NO);
                    return item;
                }
                int futureAmount = it.getAmount() - price;
                if(futureAmount <= 0) {
                    it.setType(Material.AIR);
                    item.onClick(BedWars.getInstance().getArena().getTeam(player));
                    return item;
                } else {
                    it.setAmount(futureAmount);
                }

                if(!item.onClick(BedWars.getInstance().getArena().getTeam(player))) {
                    HashMap<String, String> placeholder = new HashMap<>();
                    placeholder.put("player", player.getName());
                    placeholder.put("price", String.valueOf(price));
                    placeholder.put("price_type_color", "&b");
                    placeholder.put("price_type", String.valueOf(Material.DIAMOND));
                    placeholder.put("id", item.getId());
                    ChatUtils.sendMessage(player, new GameFile("messages.yml").getFileConfiguration(), "shop_error", placeholder);
                    sound(player, Sound.VILLAGER_NO);
                    return item;
                }
                HashMap<String, String> placeholder = new HashMap<>();
                placeholder.put("player", player.getName());
                placeholder.put("price", String.valueOf(price));
                placeholder.put("price_type_color", "&b");
                placeholder.put("price_type_material", String.valueOf(Material.DIAMOND));
                placeholder.put("price_type", String.valueOf(Material.DIAMOND));
                placeholder.put("id", item.getId());
                ChatUtils.sendMessage(player, new GameFile("messages.yml").getFileConfiguration(), "shop_success", placeholder);
                sound(player, Sound.ORB_PICKUP);
                new ShopGUI(BedWars.getInstance().getShopProvider().getCategory(player.getOpenInventory().getTitle().split(" ")[1]), player).open(player);
                return item;
            }
            return item;
        });
        category.getShopCategory().computeIfPresent(slot, (s, item) -> {
            new ShopGUI(BedWars.getInstance().getShopProvider().getCategory(item), player).open(player);
            return item;
        });
        return true;
    }

    private static String intToRoman(int num) {
        if(num == 0)
            return "";
        if (num < 1 || num > 3999)
            throw new IllegalArgumentException("Input is out of range (1-3999)");

        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return thousands[num / 1000] + hundreds[(num % 1000) / 100] + tens[(num % 100) / 10] + ones[num % 10];
    }

}
