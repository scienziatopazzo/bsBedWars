package dev.bsbedwars.it.arena.runnable;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.utils.ItemFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
@RequiredArgsConstructor
public class EggRunnable extends BukkitRunnable {

    private final Player player;
    private final Egg projectile;


    @Override
    public void run() {


        Location loc = getProjectile().getLocation();

        if (projectile.isDead()
                || player.getLocation().distance(getProjectile().getLocation()) > 27
                || player.getLocation().getY() - getProjectile().getLocation().getY() > 9) {
            cancel();
            return;
        }

        if (player.getLocation().distance(loc) > 4.0D) {

            Block b2 = loc.clone().subtract(0.0D, 2.0D, 0.0D).getBlock();
            if (b2.getType() == Material.AIR) {
                b2.setType(Material.WOOL);
                b2.setData(BedWars.getInstance().getArena().getTeam(player).getDyeColor().getData());
                BedWars.getInstance().getArena().getBlockPlaced().add(b2);
            }

            Block b3 = loc.clone().subtract(1.0D, 2.0D, 0.0D).getBlock();
            if (b3.getType() == Material.AIR) {
                b3.setData(BedWars.getInstance().getArena().getTeam(player).getDyeColor().getData());
                BedWars.getInstance().getArena().getBlockPlaced().add(b3);
            }

            Block b4 = loc.clone().subtract(0.0D, 2.0D, 1.0D).getBlock();
            if (b4.getType() == Material.AIR) {
                b4.setData(BedWars.getInstance().getArena().getTeam(player).getDyeColor().getData());
                BedWars.getInstance().getArena().getBlockPlaced().add(b4);
            }

        }


    }
}
