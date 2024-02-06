package dev.bsbedwars.it.team.component;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class TeamUpgrade {

    private final HashMap<String, Object> upgrades = new HashMap<>();

    public void addUpgrade(String key, Object value) {
        upgrades.remove(key);
        upgrades.put(key, value);
    }

    public Object getUpgrade(String key) {
        return upgrades.get(key);
    }

    public boolean getBooleanUpgrade(String key) {
        return (boolean) getUpgrade(key);
    }

    public int getIntUpgrade(String key) {
        return (int) getUpgrade(key);
    }


}
