package me.imsergioh.lobbycore.manager;

import me.imsergioh.lobbycore.instance.PluginConfig;
import me.imsergioh.lobbycore.util.ChatUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TagsManager {

    private final JavaPlugin plugin;
    private final FileConfiguration config;

    public TagsManager(JavaPlugin plugin, PluginConfig pluginConfig){
        this.plugin = plugin;
        this.config = pluginConfig.config();
        pluginConfig.registerDefault("prefix", "%luckperms_prefix%")
                .registerDefault("suffix","%luckperms_suffix%");
        pluginConfig.saveConfig();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public String getPrefix(Player p) {
        return ChatUtil.chatColorWithVariables(p, config.getString("prefix"));
    }

    public String getSuffix(Player p) {
        return ChatUtil.chatColorWithVariables(p, config.getString("suffix"));
    }
}
