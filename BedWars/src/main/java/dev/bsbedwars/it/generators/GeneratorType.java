package dev.bsbedwars.it.generators;

import dev.bsbedwars.it.bedwars.Type;
import dev.bsbedwars.it.utils.ItemFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

@RequiredArgsConstructor
@Getter
public enum GeneratorType {

    DIAMOND(
            new HashMap<ItemStack, Integer>() {{
                put(new ItemFactory(Material.DIAMOND, 1).name("&bDiamond").build(), 100);
            }},
            20,
            5,
            2
    ),
    EMERALD(
            new HashMap<ItemStack, Integer>() {{
                put(new ItemFactory(Material.EMERALD, 1).name("&bDiamond").build(), 100);
            }},
            20,
            5,
            2
    ),
    BASE(
            new HashMap<ItemStack, Integer>() {{
                put(new ItemFactory(Material.IRON_INGOT, 2).name("&bDiamond").build(), 70);
                put(new ItemFactory(Material.GOLD_INGOT, 1).name("&bDiamond").build(), 30);
            }},
            1,
            0,
            3
    );

    private final HashMap<ItemStack, Integer> materials;
    private final int seconds;
    private final int secondsRemoveLevel; // How seconds remove level. Example level 1 = 10s if secondsRemoveLevel = 2s, level 2 = 8s
    private final int maxLevel;
}
