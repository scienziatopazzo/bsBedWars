package dev.bsbedwars.it.shop.content.items.special.tower.component.runnable;

import dev.bsbedwars.it.BedWars;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class BlocksPlaced {

    private BukkitTask task;

    public BlocksPlaced(Block chest, String coord, Player player, boolean isLadder, int ladderData) {
        this.task = new BukkitRunnable() {
            int index = 0;

            @Override
            public void run() {
                if (index >= 3) {
                    cancel();
                    return;
                }

                String[] parts = coord.split(", ");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]) + index;
                int z = Integer.parseInt(parts[2]);

                Block block = chest.getLocation().clone().add(x, y, z).getBlock();

                if (isLadder) {
                    block.setType(Material.LADDER);
                    block.setData((byte) ladderData);
                } else {
                    block.setType(Material.WOOL);
                    block.setData(BedWars.getInstance().getArena().getTeam(player).getDyeColor().getData());
                }
                BedWars.getInstance().getArena().getBlockPlaced().add(block);
                index++;
            }
        }.runTaskTimer(BedWars.getInstance(), 0L, 1L);
    }
}
