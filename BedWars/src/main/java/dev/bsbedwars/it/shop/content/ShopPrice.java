package dev.bsbedwars.it.shop.content;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ShopPrice {

    DIAMOND("&b"),
    EMERALD("&a"),
    GOLD("&6"),
    IRON("&f"),;


    private final String color;



}
