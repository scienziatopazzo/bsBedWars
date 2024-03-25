package dev.bsbedwars.it.shop;

import dev.bsbedwars.it.shop.content.ShopCategory;
import dev.bsbedwars.it.shop.content.ShopItem;
import dev.bsbedwars.it.shop.content.UpgradeItem;
import dev.bsbedwars.it.shop.content.items.armor.DiamondArmor;
import dev.bsbedwars.it.shop.content.items.bloks.*;
import dev.bsbedwars.it.shop.content.items.bow.arrow.Arrow;
import dev.bsbedwars.it.shop.content.items.bow.type.Bow1;
import dev.bsbedwars.it.shop.content.items.bow.type.Bow2;
import dev.bsbedwars.it.shop.content.items.bow.type.Bow3;
import dev.bsbedwars.it.shop.content.items.special.*;
import dev.bsbedwars.it.shop.content.items.special.tower.Tower;
import dev.bsbedwars.it.shop.content.items.sword.DiamondSword;
import dev.bsbedwars.it.shop.content.items.armor.IronArmor;
import dev.bsbedwars.it.shop.content.items.sword.IronSword;
import dev.bsbedwars.it.shop.content.items.sword.Stick;
import dev.bsbedwars.it.shop.content.items.sword.StoneSword;
import dev.bsbedwars.it.shop.content.items.tools.Axe;
import dev.bsbedwars.it.shop.content.items.tools.Pickaxe;
import dev.bsbedwars.it.shop.content.items.tools.Shears;
import dev.bsbedwars.it.shop.content.items.upgrade.*;
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
    private final List<ShopItem> shopItems;
    private final List<UpgradeItem> upgradeItems;

    public ShopProvider() {
        this.shopFile = new GameFile("shop.yml");
        this.config = shopFile.getFileConfiguration();
        this.categoryList = new ArrayList<>();
        this.shopItems = new ArrayList<>();
        this.upgradeItems = new ArrayList<>();
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
        HashMap<Integer, UpgradeItem> upgradeItems = new HashMap<>();
        HashMap<Integer, ItemStack> shopContent = new HashMap<>();
        HashMap<Integer, String> shopCategory = new HashMap<>();

        for (String slot : items.getKeys(false)) {
            if(slot.contains("-")) {
                String[] slots = slot.split("-");
                for (int i = Integer.parseInt(slots[0]); i < Integer.parseInt(slots[1]) + 1; i++) {
                    ConfigurationSection itemSelection = items.getConfigurationSection(slot);
                    if(itemSelection.contains("sendCategory")) {
                        shopCategory.put(i, itemSelection.getString("sendCategory"));
                        continue;
                    }
                    if(itemSelection.contains("shopItem")) {
                        shopItems.put(i, getShopItem(itemSelection.getString("shopItem")));
                        continue;
                    }
                    shopContent.put(i, ItemFactory.load(itemSelection).build());
                }
                continue;
            }
            if(slot.contains(",")) {
                String[] slots = slot.split(",");
                for(String s : slots) {
                    ConfigurationSection itemSelection = items.getConfigurationSection(slot);
                    if(itemSelection.contains("sendCategory")) {
                        shopCategory.put(Integer.parseInt(s), itemSelection.getString("sendCategory"));
                        continue;
                    }
                    if(itemSelection.contains("shopItem")) {
                        shopItems.put(Integer.parseInt(s), getShopItem(itemSelection.getString("shopItem")));
                        continue;
                    }
                    shopContent.put(Integer.parseInt(s), ItemFactory.load(itemSelection).build());
                }
                continue;
            }
            ConfigurationSection itemSelection = items.getConfigurationSection(slot);
            if(itemSelection.contains("sendCategory")) {
                shopCategory.put(Integer.parseInt(slot), itemSelection.getString("sendCategory"));
                continue;
            }
            if(itemSelection.contains("shopItem")) {
                shopItems.put(Integer.parseInt(slot), getShopItem(itemSelection.getString("shopItem")));
                continue;
            }
            if(itemSelection.contains("upgradeItem")) {
                upgradeItems.put(Integer.parseInt(slot), getUpgradeItems(itemSelection.getString("upgradeItem")));
                continue;
            }
            shopContent.put(Integer.parseInt(slot), ItemFactory.load(itemSelection).build());

        }
        categoryList.add(
                new ShopCategory(
                        name,
                        itemDisplay,
                        shopItems,
                        upgradeItems,
                        shopContent,
                        shopCategory
                )
        );
    }

    private void regItems() {
        // Load Armor
        new DiamondArmor();
        new IronArmor();
        // Load Sword
        new DiamondSword();
        new IronSword();
        new StoneSword();
        new Stick();
        // Load Bow
        new Arrow();
        new Bow1();
        new Bow2();
        new Bow3();
        // Load Materials
        new Clay();
        new EndStone();
        new Glass();
        new Oak();
        new Obsidian();
        new Ladder();
        new Wool();
        // Tools
        new Pickaxe();
        new Axe();
        new Shears();
        // Specials
        new Tower();
        new Apple();
        new Egg();
        new Enderpearl();
        new Fireball();
        new Milk();
        new Sponge();
        new Tnt();
        new Water();
        // Upgrade
        new UpgradeArmor();
        new UpgradeForge();
        new UpgradeHealingPool();
        new UpgradeMine();
        new UpgradeSword();
        new UpgradeTrap();
    }

    public ShopCategory getCategory(String name) {
        return categoryList.stream().filter(category -> category.getId().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public ShopItem getShopItem(String name) {
        return shopItems.stream().filter(category -> category.getId().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public UpgradeItem getUpgradeItems(String name) {
        return upgradeItems.stream().filter(category -> category.getId().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

}
