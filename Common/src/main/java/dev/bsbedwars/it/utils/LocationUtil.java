package dev.bsbedwars.it.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.material.Bed;
import org.bukkit.util.NumberConversions;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class LocationUtil {


    private final Location location;


    public void set(FileConfiguration config, String patch) {
        String toString = "";
        if (location.getBlock().getState().getData() instanceof Bed) {
            toString += ((Bed) location.getBlock().getState().getData()).toString() + "|";
        }
        toString += location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
        config.set(patch, toString);
    }


    public Location deserialize(String location) {
        if(location.contains("|"))
            location = location.split("\\|")[1];
        String[] split = location.split(",");

        return new Location(
                Bukkit.getWorld(split[0]),
                NumberConversions.toDouble(split[1]),
                NumberConversions.toDouble(split[2]),
                NumberConversions.toDouble(split[3]),
                NumberConversions.toFloat(split[4]),
                NumberConversions.toFloat(split[5])
        );
    }




    public Location toCenterLocation(Location original) {
        Location copy = original.clone();
        copy.setX(original.getBlockX() + 0.5);
        copy.setY(original.getBlockY() + 0.5);
        copy.setZ(original.getBlockZ() + 0.5);
        return copy;
    }








    public static List<Block> placeBed(String deserialized) {

        List<Block> blocks = new ArrayList<>();

        String[] bedSplit = deserialized.split("\\|");
        String bedString = bedSplit[0];
        Location bedLocation = new LocationUtil(null).deserialize(bedSplit[1]);
        BedInfo bedInfo = deserializeB(bedString);

        Block bedBlock = bedLocation.getBlock();


        bedBlock.setType(Material.BED_BLOCK);

        Bed bedData = (Bed) bedBlock.getState().getData();
        bedData.setHeadOfBed(bedInfo.isHeadOfBed());
        bedData.setFacingDirection(bedInfo.getFacing());

        bedBlock.getState().update();

        Location otherBedLocation = bedLocation.clone();
        otherBedLocation.add(bedData.getFacing().getModX(), bedData.getFacing().getModY(), bedData.getFacing().getModZ());

        otherBedLocation.getBlock().setType(Material.BED_BLOCK);

        otherBedLocation.getBlock().getState().update();

        blocks.add(bedBlock);
        blocks.add(otherBedLocation.getBlock());

        return blocks;
    }



    private static BedInfo deserializeB(String bedString) {
        String[] parts = bedString.split(" of ");
        String positionInfo = parts[0];
        String bedInfo = parts[1];

        boolean isHead = positionInfo.equals("HEAD");
        String[] bedParts = bedInfo.split(" facing ");
        String facingDirection = bedParts[1];

        return new BedInfo(isHead, BlockFace.valueOf(facingDirection));
    }

    private static class BedInfo {
        private final boolean isHeadOfBed;
        @Getter
        private final BlockFace facing;

        public BedInfo(boolean isHeadOfBed, BlockFace facing) {
            this.isHeadOfBed = isHeadOfBed;
            this.facing = facing;
        }

        public boolean isHeadOfBed() {
            return isHeadOfBed;
        }

    }



}
