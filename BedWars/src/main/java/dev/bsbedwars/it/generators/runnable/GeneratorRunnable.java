package dev.bsbedwars.it.generators.runnable;

import dev.bsbedwars.it.generators.Generator;
import dev.bsbedwars.it.generators.GeneratorType;
import dev.bsbedwars.it.utils.ItemFactory;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class GeneratorRunnable extends BukkitRunnable {

    private final Generator generator;
    private int secondsRemaining;

    @Override
    public void run() {

        if(generator.getLocation() == null || generator.getType() == null) {
            cancel();
            return;
        }


        if(secondsRemaining != 0) {
            secondsRemaining = secondsRemaining - 4;
            if(generator.getType() != GeneratorType.BASE)
                generator.getHologram().change(Arrays.asList("&b" + generator.getType().toString() + " &8(" + generator.getLevel() + ")", "&bSeconds left: &c" + secondsRemaining / 4));
            return;
        }
        if(generator.getLevel() == 3 && generator.getType() == GeneratorType.BASE || generator.getLevel() == 4 && generator.getType() == GeneratorType.BASE) {
            HashMap<ItemStack, Integer> drop = new HashMap<>(generator.getType().getMaterials());
            drop.put(new ItemFactory(Material.EMERALD, 1).name("&eEmerald").build(), 35);
            spawn(generator.getLocation(), drop, generator.getLevel()); // spawn items
        } else {
            spawn(generator.getLocation(), generator.getType().getMaterials(), generator.getLevel()); // spawn items
        }
        secondsRemaining = (generator.getType().getSeconds() - generator.getType().getSecondsRemoveLevel() * generator.getLevel());
        if(generator.getType() != GeneratorType.BASE)
            generator.getHologram().change(1, "&bSeconds left: &c" + secondsRemaining / 4);

    }


    private void spawn(Location location, HashMap<ItemStack, Integer> itemChances, int level) {
        int totalPercentage = itemChances.values().stream().mapToInt(Integer::intValue).sum();
        int randomValue = (int) (Math.random() * totalPercentage);

        for (Map.Entry<ItemStack, Integer> entry : itemChances.entrySet()) {
            randomValue -= entry.getValue();
            if (randomValue < 0) {
                Item droppedItem = location.getWorld().dropItem(location, entry.getKey());
                droppedItem.setVelocity(new Vector(0, 0, 0));
                break;
            }
        }
    }

}


