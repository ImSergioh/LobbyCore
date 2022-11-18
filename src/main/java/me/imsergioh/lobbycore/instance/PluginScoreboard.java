package me.imsergioh.lobbycore.instance;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.lobbycore.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class PluginScoreboard {

    private final String displayName;
    private final int updateTicks;

    private final List<String> lines;

    private FileConfiguration config;

    private HashMap<UUID, PlayerScoreboard> scoreboards = new HashMap<>();

    private int task;

    public PluginScoreboard(PluginConfig pluginConfig){
        config = pluginConfig.config();
        displayName = ChatUtil.chatColor(config.getString("title"));
        updateTicks = config.getInt("updateTicks");
        lines = config.getStringList("lines");

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(LobbyCore.getPlugin(), new Runnable() {
            @Override
            public void run() {
                try {
                    if (scoreboards.isEmpty()) {
                        return;
                    }
                    scoreboards.values().forEach(PlayerScoreboard::updateScoreboard);
                } catch (Exception e) {}
            }
        },20, updateTicks);
    }

    public void register(Player player){
        if(scoreboards.containsKey(player.getUniqueId())){
            return;
        }
        scoreboards.put(player.getUniqueId(), new PlayerScoreboard(this, player));
    }

    public void unregister(Player player){
        scoreboards.remove(player.getUniqueId());
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getLines() {
        return lines;
    }
}
