package dev.bsbedwars.it.shop.content;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

@RequiredArgsConstructor
@Getter
public class ShopCategory {

    private final String id;
    private final ItemStack itemDisplay;
    private final HashMap<Integer, ShopItem> shopItems;
    private final HashMap<Integer, ItemStack> shopContent;
    private final HashMap<Integer, String> shopCategory;

}
