package dev.bsbedwars.it.shop.content;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
@Getter
public abstract class ShopItem {


    private final String id;
    private final ItemStack itemDisplay;
    private final ItemStack itemStack;
    private final ShopPrice priceType;
    private final int price;


    public abstract void onClick(Player player);




}
