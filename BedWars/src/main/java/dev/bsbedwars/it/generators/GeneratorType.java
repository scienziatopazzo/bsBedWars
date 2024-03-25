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
            80,
            20,
            2
    ),
    EMERALD(
            new HashMap<ItemStack, Integer>() {{
                put(new ItemFactory(Material.EMERALD, 1).name("&aEmerald").build(), 100);
            }},
            80,
            20,
            2
    ),
    BASE(
            new HashMap<ItemStack, Integer>() {{
                put(new ItemFactory(Material.IRON_INGOT, 2).name("&fIron").build(), 70);
                put(new ItemFactory(Material.GOLD_INGOT, 1).name("&6Gold").build(), 30);
            }},
            4,
            1,
            4
    );

    private final HashMap<ItemStack, Integer> materials;
    private final int seconds; // 1 SECONDS = 1/4 SECONDS
    private final int secondsRemoveLevel; // How seconds remove level. Example level 1 = 10s if secondsRemoveLevel = 2s, level 2 = 8s
    private final int maxLevel;
}
