package dev.bsbedwars.it.arena.runnable;

import dev.bsbedwars.it.BedWars;
import dev.bsbedwars.it.arena.Arena;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.generators.Generator;
import dev.bsbedwars.it.generators.GeneratorType;
import dev.bsbedwars.it.team.Team;
import dev.bsbedwars.it.team.component.runnable.UpdateTeamRunnable;
import dev.bsbedwars.it.team.component.armor.Armor;
import dev.bsbedwars.it.team.component.TeamColor;
import dev.bsbedwars.it.team.component.sword.Sword;
import dev.bsbedwars.it.utils.ChatUtils;
import dev.bsbedwars.it.utils.LocationUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class StartingRunnable extends BukkitRunnable {

    private final Arena arena;
    private int second;

    public StartingRunnable(Arena arena, int second){
        this.arena = arena;
        this.second = second;
    }

    @Override
    public void run() {
        arena.setStatus(Status.STARTING);


        if (arena.getPlayers().size() < arena.getType().getMinPlayers()) {
            arena.setStatus(Status.LOBBY);
            for (Player player : arena.getPlayers()){
                ChatUtils.sendMessage(player, arena.getMessageConfig(), "starting_cancelled", String.valueOf(second));
                ChatUtils.sendTitle(player, arena.getMessageConfig(), "starting_cancelled_title", String.valueOf(second));
            }
            cancel();
            return;
        }


        if(second < 0){
            // Create teams
            List<Team> teams = createTeams();
            arena.setTeams(teams);

            // SetUP teams
            for (Team team : arena.getTeams()) {
                // Teleport to spawn
                for (Player player : team.getPlayers()) {
                    player.teleport(team.getSpawnLocation());
                    ChatUtils.sendMessage(player, arena.getMessageConfig(), "started_private_msg");
                    player.getInventory().clear();
                    Sword.giveWoodSword(player);
                    Armor.updatePlayerArmor(player, Armor.LEATHER);
                }
            }
            for (Player player : arena.getPlayers())
                player.setGameMode(GameMode.SURVIVAL);

            // Create generators
            GeneratorType[] selections = {GeneratorType.DIAMOND, GeneratorType.EMERALD};

            for (GeneratorType generatorType : selections) {
                FileConfiguration config = arena.getGeneratorsFile().getFileConfiguration();
                ConfigurationSection selection = config.getConfigurationSection(generatorType.toString());

                if(selection == null)
                    return;

                for (String key : selection.getKeys(false))
                    arena.getGenerators().add(
                            new Generator(
                                    new LocationUtil(null).deserialize(selection.getString(key + ".location")),
                                    generatorType,
                                    1
                            )
                    );
            }

            arena.getGenerators().forEach(Generator::start);


            arena.setStatus(Status.GAME);
            new UpdateTeamRunnable(arena).runTaskTimerAsynchronously(BedWars.getInstance(), 20, 20);
            arena.getBlockPlaced().clear();
            cancel();
            return;
        }



        for (Player player : arena.getPlayers()){
            if(second == 10 || second <= 5)
                ChatUtils.sendTitle(player, arena.getMessageConfig(), "starting_title", String.valueOf(second));
            ChatUtils.sendMessage(player, arena.getMessageConfig(), "starting", String.valueOf(second));
        }


        second--;

    }

    private List<Team> createTeams() {
        List<Player> players = arena.getPlayers();
        Collections.shuffle(players);

        int playersPerTeam = arena.getType().getPlayerForTeam();
        int totalTeams = (int) Math.ceil(players.size() / playersPerTeam);

        List<Team> teamsMap = new ArrayList<>();
        for (int i = 0; i < totalTeams; i++) {
            TeamColor teamColor = TeamColor.values()[i % TeamColor.values().length];
            List<Player> teamPlayers = players.subList(i * playersPerTeam, (i + 1) * playersPerTeam);
            Team team = new Team(
                    arena,
                    teamColor,
                    teamPlayers
            );
            team.getBedLocation().getBlock().setType(Material.BED_BLOCK);
            teamsMap.add(team);
        }

        return teamsMap;
    }

}
