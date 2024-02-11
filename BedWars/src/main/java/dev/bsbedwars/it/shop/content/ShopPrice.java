package dev.bsbedwars.it.shop.content;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@RequiredArgsConstructor
@Getter
public enum ShopPrice {

    DIAMOND("&b", Material.DIAMOND),
    EMERALD("&a", Material.EMERALD),
    GOLD("&6", Material.GOLD_INGOT),
    IRON("&f", Material.IRON_INGOT),;


    private final String color;
    private final Material material;



}
