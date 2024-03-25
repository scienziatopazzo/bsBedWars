package dev.bsbedwars.it.hologram;

import dev.bsbedwars.it.utils.ChatUtils;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Hologram extends AbstractHologram {

    private final List<ArmorStand> hologram; // holograms of lines

    public Hologram(Location location, List<String> lines, String name, JavaPlugin plugin) {
        super(location, ChatUtils.color(lines), name, plugin);
        this.hologram = new ArrayList<>();
        this.lines = new ArrayList<>(lines); // Ensure that lines is an ArrayList
    }

    @Override
    public void create() {
        lines = ChatUtils.color(lines);
        for (int i = 0; i < lines.size(); i++) {
            ArmorStand armorStand = (ArmorStand) location.getWorld().spawn(location.clone().add(0, 0.3 * i, 0), ArmorStand.class);
            armorStand.setVisible(false);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(lines.get(i));
            armorStand.setGravity(false);
            hologram.add(armorStand);
        }
    }

    @Override
    public void delete() {
        for (ArmorStand armorStand : hologram) {
            armorStand.remove();
        }
        hologram.clear();
    }

    @Override
    public void addLine(int line, String content) {
        content = ChatUtils.color(content);

        ArmorStand armorStand = (ArmorStand) location.getWorld().spawn(location.clone().add(0, 0.3 * line, 0), ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(content);
        armorStand.setGravity(false);
        hologram.add(armorStand);

        List<String> updatedLines = new ArrayList<>(lines);
        updatedLines.add(line, content);
        lines = updatedLines;

        updateLines();

        // reload
        delete();
        create();
    }


    @Override
    public void addLine(String content) {
        content = ChatUtils.color(content);
        addLine(0, content);
    }

    @Override
    public void removeLine(int line) {
        if (line >= 0 && line < hologram.size()) {
            hologram.get(line).remove();
            hologram.remove(line);
            lines.remove(line);
            updateLines();
        }
    }



    @Override
    public void change(List<String> lines) {
        // Remove any extra holograms if the new list is shorter
        for (int i = lines.size(); i < hologram.size(); i++) {
            removeLine(i);
        }

        for (int i = 0; i < lines.size(); i++) {
            String content = ChatUtils.color(lines.get(i));
            if (i < hologram.size()) {
                change(i, content);
            } else {
                addLine(i, content);
            }
        }

        updateLines();
    }


    @Override
    public void change(int line, String content) {
        content = ChatUtils.color(content);
        if (line >= 0 && line < hologram.size()) {
            hologram.get(line).setCustomName(content);
        }
    }

    @Override
    public void move(Location newLocation) {
        this.location = newLocation;
        updateLines();
    }

    private void updateLines() {
        for (int i = 0; i < hologram.size(); i++) {
            hologram.get(i).teleport(location.clone().add(0, 0.3 * i, 0));
        }
    }





}
