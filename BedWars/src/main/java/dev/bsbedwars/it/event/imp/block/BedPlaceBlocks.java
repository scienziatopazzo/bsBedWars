package dev.bsbedwars.it.event.imp.block;

import dev.bsbedwars.it.BedWars;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BedPlaceBlocks implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (event.getAction().toString().contains("RIGHT") && isPlaceableBlock(player.getItemInHand().getType())) {
            if (clickedBlock != null && clickedBlock.getType().toString().contains("BED")) {
                event.setCancelled(true);

                Block blockAboveBed = clickedBlock.getRelative(0, 1, 0);
                if (blockAboveBed.getType() == Material.AIR && event.getBlockFace() == BlockFace.UP) {
                    blockAboveBed.setType(player.getItemInHand().getType());
                    blockAboveBed.setData(player.getItemInHand().getData().getData());
                    BedWars.getInstance().getArena().getBlockPlaced().add(blockAboveBed);

                    ItemStack handItem = player.getItemInHand();
                    handItem.setAmount(handItem.getAmount() - 1);
                    player.setItemInHand(handItem);
                }
            }
        }
    }

    private boolean isPlaceableBlock(Material material) {
        switch (material) {
            case WOOL:
            case WOOD:
            case OBSIDIAN:
            case CLAY:
            case GLASS:
            case STAINED_CLAY:
            case STAINED_GLASS:
            case STAINED_GLASS_PANE:
            case ENDER_STONE:
                return true;
            default:
                return false;
        }
    }

}
