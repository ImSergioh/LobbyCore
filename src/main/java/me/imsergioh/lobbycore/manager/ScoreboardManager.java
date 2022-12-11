package me.imsergioh.lobbycore.manager;

import me.imsergioh.lobbycore.instance.PluginConfig;
import me.imsergioh.lobbycore.instance.PluginInventory;
import me.imsergioh.lobbycore.instance.PluginScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import us.smartmc.smartcorespigot.SmartCoreSpigot;
import us.smartmc.smartcorespigot.instance.CorePlayer;
import us.smartmc.smartcorespigot.instance.LanguageInstance;

import java.util.*;

public class ScoreboardManager {

    private JavaPlugin plugin;

    private final List<String> list;

    private static Map<String, PluginScoreboard> scoreboards = new HashMap<>();
    private final PluginConfig pluginConfig;

    public ScoreboardManager(JavaPlugin plugin){
        this.plugin = plugin;
        pluginConfig = new PluginConfig(plugin, "", "scoreboards.yml");

        // REGISTER A LIST FOR SCOREBOARDS (MULTILANG IMPLEMENTATION)
        if(!pluginConfig.config().contains("list")) {
            List<String> list = new ArrayList<>();
            list.add("mainScoreboard");
            pluginConfig.registerDefault("list", list);
        }
        pluginConfig.saveConfig();
        list = pluginConfig.config().getStringList("list");


        for(String scoreboardName : list){
            for(String langName : SmartCoreSpigot.getPlugin().getLanguageHandler().langNames()){
                PluginConfig config = new PluginConfig(plugin, "scoreboards", scoreboardName+"_"+langName+".yml");
                System.out.println("Parsing scoreboard '"+config.fileName()+"'..");
                PluginScoreboard pluginScoreboard = new PluginScoreboard(config, SmartCoreSpigot.getPlugin().getLanguageHandler().get(langName));
                scoreboards.put(config.fileName().replace(".yml", ""), pluginScoreboard);
                System.out.println("Scoreboard config registered: "+config.fileName()+"!");
            }
        }
    }

    public PluginScoreboard getFromName(Player player, String name){
        LanguageInstance languageInstance = CorePlayer.get(player).getLanguage();
        return scoreboards.get(name+"_"+languageInstance.getName());
    }

    public void unregister(Player player){
        for(PluginScoreboard all : scoreboards.values()){
            all.unregister(player);
        }
    }

}
