package me.imsergioh.lobbycore.instance;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.lobbycore.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import us.smartmc.smartcorespigot.instance.LanguageInstance;

import java.util.*;

public class PluginScoreboard {

    private LanguageInstance languageInstance;
    private final String displayName;
    private final int updateTicks;

    private final List<String> lines;

    private PluginConfig pluginConfig;
    private FileConfiguration config;

    private HashMap<UUID, PlayerScoreboard> scoreboards = new HashMap<>();

    private int task;

    public PluginScoreboard(PluginConfig pluginConfig, LanguageInstance languageInstance){
        this.languageInstance = languageInstance;
        this.pluginConfig = pluginConfig;
        config = pluginConfig.config();
        regDefaults();
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

    private void regDefaults(){
        boolean changed = false;
        if(!config.contains("title")){
            config.set("title", "&e&lLobbyCore v"+LobbyCore.getPlugin().getDescription().getVersion());
            changed = true;
        }

        if(!config.contains("updateTicks")){
            config.set("updateTicks", 20);
            changed = true;
        }

        if(!config.contains("lines")){
            List<String> list = new ArrayList<>();
            list.add("Line1");
            list.add("Line2");
            list.add("Line3");
            config.set("lines", list);
            changed = true;
        }

        if(changed) {
            pluginConfig.saveConfig();
        }
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

    public LanguageInstance getLanguageInstance() {
        return languageInstance;
    }
}
