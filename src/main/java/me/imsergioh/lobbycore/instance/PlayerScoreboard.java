package me.imsergioh.lobbycore.instance;

import me.imsergioh.lobbycore.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;

public class PlayerScoreboard {

    private final PluginScoreboard pluginScoreboard;

    private Scoreboard scoreboard;
    private Objective objective;

    private final Player player;

    private Map<String, String> teams = new HashMap<>();

    public PlayerScoreboard(PluginScoreboard pluginScoreboard, Player player) {
        this.pluginScoreboard = pluginScoreboard;
        this.player = player;
        parseFromConfig();
    }

    private void parseFromConfig(){
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("aaa", "bbb");
        objective.setDisplayName(pluginScoreboard.getDisplayName());
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        int scoreIndex = pluginScoreboard.getLines().size();

        for(String line : pluginScoreboard.getLines()){
            if(line.contains("%")){
                Team team = scoreboard.registerNewTeam("team"+teams.size());
                String entry = line.split("%")[0];
                String suffix = line.replaceFirst(entry, "");
                team.addEntry(ChatUtil.chatColor(entry));
                team.setSuffix(ChatUtil.chatColorWithVariables(player, suffix));
                teams.put(team.getName(), suffix);
                objective.getScore(ChatUtil.chatColor(entry)).setScore(scoreIndex);
            } else {
                Score score = objective.getScore(ChatUtil.chatColor(line));
                score.setScore(scoreIndex);
            }
            scoreIndex--;
        }
        player.setScoreboard(scoreboard);
        updateScoreboard();
    }

    public void updateScoreboard(){
        if(player.isOnline()) {
            teams.keySet().forEach(name -> {
                Team team = scoreboard.getTeam(name);
                team.setSuffix(ChatUtil.chatColorWithVariables(player, teams.get(name)));
            });
            return;
        }
        pluginScoreboard.unregister(player);
    }
}
