package me.imsergioh.lobbycore.manager;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.lobbycore.instance.PluginConfig;

import java.util.Arrays;
import java.util.List;

public class ConfigManager {

    private static final LobbyCore plugin = LobbyCore.getPlugin();

    private static PluginConfig mainConfig;

    public static void setup(){
        mainConfig = new PluginConfig(plugin, "",
                "config.yml")
                .registerDefault("teleportOnJoin", true)
                .registerDefault("lobbyWorlds", strList("world", "lobby", "spawn"))
                .registerDefault("cancelDamage", true)
                .registerDefault("cancelDropItem", true)
                .registerDefault("cancelPickUp", true)
                .registerDefault("cancelBlockBreak", true)
                .registerDefault("cancelBlockPlace", true)
                .registerDefault("cancelWeatherChange", true)
                .registerDefault("setCustomTime", true)
                .registerDefault("customTime", 1000L)
                .registerDefault("customGamemodeSet", false)
                .registerDefault("customGamemode", 2)
                .registerDefault("joinItemsEnabled", false)
                .registerDefault("joinItemsMenuPath", "main")
                .registerDefault("mainScoreboardEnabled", true)
                .registerDefault("mainScoreboardName", "mainScoreboard.yml")
                .registerDefault("customTabEnabled", true)
                .registerDefault("customTabConfigName", "tab.yml")
                .registerDefault("customTagsEnabled", true)
                .registerDefault("customTagsConfigName", "tags.yml")
                .registerDefault("autoSpawnManagerEnabled", false)
                .registerDefault("autoSpawnHeight", 30)
                .registerDefault("chatFormat", "%luckperms_prefix%%1$s%luckperms_suffix%&f: &7%2$s")
                .registerDefault("pvpzones", true);
        mainConfig.saveConfig();
        SpawnManager.setLobbyWorlds(mainConfig.config().getStringList("lobbyWorlds"));
    }

    public static boolean isConfigOnConfig(String path){
        return mainConfig.config().getBoolean(path);
    }

    private static List<String> strList(String... values){
        return Arrays.asList(values);
    }

    public static PluginConfig getMainConfig() {
        return mainConfig;
    }
}
