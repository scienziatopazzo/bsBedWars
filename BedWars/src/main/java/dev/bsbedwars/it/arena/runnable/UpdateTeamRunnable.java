package dev.bsbedwars.it.arena.runnable;

import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.team.component.armor.Armor;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class UpdateTeamRunnable extends BukkitRunnable {

    private final Arena arena;

    @Override
    public void run() {
        if(arena.getStatus() != Status.GAME) {
            cancel();
            return;
        }
        for (Player player : arena.getPlayers()) {
            Armor.updateSword(player);
            Armor.updatePlayerArmor(player, Armor.getArmor(player));
        }

    }
}
