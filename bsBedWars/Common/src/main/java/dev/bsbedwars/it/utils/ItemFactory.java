package dev.bsbedwars.it.utils;

import com.google.common.collect.Maps;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import javax.xml.soap.MessageFactory;
import java.util.*;

public class ItemFactory {


    private ItemStack item;
    private ItemMeta meta;

    private static HashMap<ItemStack, ItemFactory> items = new HashMap<>();

    public static ItemFactory getItemBuilder(ItemStack item) {
        return items.get(item);
    }

    public static List<ItemStack> adjustAmountOfItems(Collection<ItemStack> items) {
        List<ItemStack> b = new ArrayList<>();
        for (ItemStack item : items) {
            int newAmount = item.getAmount();
            ItemStack tempItem;
            while (newAmount > 64) {
                tempItem = item.clone();
                tempItem.setAmount(64);
                b.add(tempItem);
                newAmount -= 64;
            }
            if (newAmount > 0) {
                tempItem = item.clone();
                tempItem.setAmount(newAmount);
                b.add(tempItem);
            }
        }

        return b;
    }

    public ItemFactory(Material material, int amount) {
        item = new ItemStack(material, amount);
        meta = item.getItemMeta();
    }

    public ItemFactory(Material material) {
        this(material, 1);
    }

    public ItemFactory(Material material, short damage) {
        item = new ItemStack(material, 1, damage);
        meta = item.getItemMeta();
    }

    public ItemFactory(ItemStack items) {
        item = items;
        meta = item.getItemMeta();
    }

    public ItemFactory(ItemStack items, int amount) {
        item = items;
        if (amount > 64 || amount < 0) amount = 64;
        item.setAmount(amount);
        meta = item.getItemMeta();
    }

    public ItemFactory name(String name) {
        meta.setDisplayName(
                ChatUtils.color(name)
        );
        return this;
    }

    public String getName() {
        return meta.getDisplayName();
    }

    public ItemFactory setDurability(int damage) {
        item.setDurability((short) damage);
        return this;
    }

    public List<String> getLore() {
        return meta.getLore();
    }

    public ItemFactory setLore(String... lore) {
        return setLore(
                ChatUtils.color(lore)
        );
    }

    public ItemFactory setLore(List<String> lore) {
        meta.setLore(
                ChatUtils.color(lore)
        );
        return this;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        Map<Enchantment, Integer> values = Maps.newHashMap();
        values.putAll(meta.getEnchants());
        return values;
    }

    public ItemFactory addLore(List<String> lores) {
        List<String> newLore = meta.getLore();
        newLore.addAll(
                ChatUtils.color(lores)
        );

        meta.setLore(newLore);
        return this;
    }

    public ItemFactory setFlags(ItemFlag... flags) {
        for (ItemFlag flag : flags)
            meta.addItemFlags(flag);
        return this;
    }


    public ItemFactory addEnchant(Enchantment ench, int level) {
        meta.addEnchant(ench, level, true);
        return this;
    }

    public ItemFactory setSkull(String owner) {
        SkullMeta meta = (SkullMeta) this.meta;
        meta.setOwner(owner);
        this.meta = meta;
        return this;
    }

    public ItemFactory setPlayerSkull(String playerName) {
        SkullMeta meta = (SkullMeta) this.meta;
        meta.setOwner(playerName);
        this.meta = meta;
        return this;
    }



    public ItemFactory setMeta(ItemMeta meta) {
        this.meta = meta;
        return this;
    }

    public ItemFactory spawner(EntityType entityType) {
        BlockStateMeta blockMeta = (BlockStateMeta) meta;
        BlockState blockState = blockMeta.getBlockState();
        CreatureSpawner spawner = (CreatureSpawner) blockState;

        spawner.setSpawnedType(entityType);
        blockMeta.setBlockState(spawner);

        item.setItemMeta(blockMeta);
        meta = item.getItemMeta();
        return this;
    }

    public ItemFactory setLeatherColor(int red, int green, int blue) {
        LeatherArmorMeta im = (LeatherArmorMeta) meta;
        im.setColor(Color.fromRGB(red, green, blue));
        return this;
    }


    public ItemFactory setGlowing(boolean glowing) {
        if (glowing) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.removeEnchant(Enchantment.DURABILITY);
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }

    public ItemFactory setAmount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        items.put(item, this);
        return item;
    }
    

    @Override
    public ItemFactory clone() {
        try {
            return (ItemFactory) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
