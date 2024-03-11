package dev.bsbedwars.it.generators.runnable;

import dev.bsbedwars.it.generators.Generator;
import dev.bsbedwars.it.generators.GeneratorType;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
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
            secondsRemaining--;
            if(generator.getType() != GeneratorType.BASE)
                generator.getHologram().change(Arrays.asList("&b" + generator.getType().toString() + " &8(" + generator.getLevel() + ")", "&bSeconds left: &c" + secondsRemaining));
            return;
        }

        spawn(generator.getLocation(), generator.getType().getMaterials(), generator.getLevel()); // spawn items
        secondsRemaining = (generator.getType().getSeconds() - generator.getType().getSecondsRemoveLevel() * generator.getLevel());
        if(generator.getType() != GeneratorType.BASE)
            generator.getHologram().change(1, "&bSeconds left: &c" + secondsRemaining);

    }


    private void spawn(Location location, HashMap<ItemStack, Integer> itemChances, int level) {

        // At every level, increase the chances of the rarest item (25% for level)
        Map.Entry<ItemStack, Integer> rarestItem = itemChances.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .orElse(null);

        if (rarestItem != null) {
            int increasePercentage = (int) (rarestItem.getValue() * 0.25 * level);
            rarestItem.setValue(rarestItem.getValue() + increasePercentage);
        }

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


