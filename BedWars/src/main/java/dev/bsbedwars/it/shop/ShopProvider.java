package dev.bsbedwars.it.shop;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.shop.content.ShopCategory;
import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.items.DiamondArmor;
import dev.bsbedwars.it.shop.content.items.DiamondSword;
import dev.bsbedwars.it.shop.content.items.IronArmor;
import dev.bsbedwars.it.shop.content.items.IronSword;
import dev.bsbedwars.it.utils.GameFile;
import dev.bsbedwars.it.utils.ItemFactory;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class ShopProvider {

    private final GameFile shopFile;
    private final FileConfiguration config;
    private final List<ShopCategory> categoryList;
    private final List<ShopItem> items;

    public ShopProvider() {
        this.shopFile = new GameFile("component/shop.yml");
        this.config = shopFile.getFileConfiguration();
        this.categoryList = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    public void load() {
        regItems();

        ConfigurationSection section = config.getConfigurationSection("category");

        for (String name : section.getKeys(false))
            loadCategory(name);
    }

    private void loadCategory(String name) {
        ItemStack itemDisplay = ItemFactory.load(config.getConfigurationSection("category." + name))
                .name("&b" + name)
                .setLore(
                        config.getStringList("itemLore.category")
                ).build();

        ConfigurationSection items = config.getConfigurationSection(name);

        HashMap<Integer, ShopItem> shopItems = new HashMap<>();
        HashMap<Integer, ItemStack> shopContent = new HashMap<>();
        HashMap<Integer, String> shopCategory = new HashMap<>();

        for (String slot : items.getKeys(false)) {
            ConfigurationSection itemSelection = items.getConfigurationSection(slot);
            if(itemSelection.contains("sendCategory")) {
                shopCategory.put(Integer.parseInt(slot), itemSelection.getString("sendCategory"));
                continue;
            }
            if(itemSelection.contains("shopItem")) {
                shopItems.put(Integer.parseInt(slot), getShopItem(itemSelection.getString("shopItem")));
                continue;
            }
            shopContent.put(Integer.parseInt(slot), ItemFactory.load(itemSelection).build());
        }
        categoryList.add(
                new ShopCategory(
                        name,
                        itemDisplay,
                        shopItems,
                        shopContent,
                        shopCategory
                )
        );
    }

    private void regItems() {
        new DiamondArmor();
        new DiamondSword();
        new IronArmor();
        new IronSword();
    }

    public ShopCategory getCategory(String name) {
        return categoryList.stream().filter(category -> category.getId().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public ShopItem getShopItem(String name) {
        return items.stream().filter(category -> category.getId().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

}
