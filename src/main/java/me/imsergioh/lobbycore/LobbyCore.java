package me.imsergioh.lobbycore;

import me.imsergioh.lobbycore.command.spawn;
import me.imsergioh.lobbycore.customcommands.admin;
import me.imsergioh.lobbycore.customcommands.setspawn;
import me.imsergioh.lobbycore.customcommands.test;
import me.imsergioh.lobbycore.event.*;
import me.imsergioh.lobbycore.instance.PluginConfig;
import me.imsergioh.lobbycore.manager.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LobbyCore extends JavaPlugin {

    private static LobbyCore plugin;

    private SpawnManager spawnManager;
    private InventoriesManager inventoriesManager;
    private ScoreboardManager scoreboardManager;
    private TabManager tabManager;
    private TagsManager tagsManager;

    @Override
    public void onEnable() {
        plugin = this;
        ConfigManager.setup();
        spawnManager = new SpawnManager("mainSpawn.yml");
        inventoriesManager = new InventoriesManager(plugin);
        scoreboardManager = new ScoreboardManager(plugin);
        new AutoSpawnManager();

        if(ConfigManager.isConfigOnConfig("setCustomTime")){
            new CustomTimeManager(ConfigManager.getMainConfig().config().getLong("customTime"));
        }

        if(ConfigManager.isConfigOnConfig("customTagsEnabled")){
            PluginConfig config = new PluginConfig(plugin, "", ConfigManager.getMainConfig().config().getString("customTagsConfigName"));
            tagsManager = new TagsManager(plugin, config);
        }

        if(ConfigManager.isConfigOnConfig("customTabEnabled")){
            PluginConfig config = new PluginConfig(plugin, "", ConfigManager.getMainConfig().config().getString("customTabConfigName"));
            tabManager = new TabManager(plugin, config);
        }

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new SpawnEvents(), plugin);
        pm.registerEvents(new CustomCommandsEvents(), plugin);
        pm.registerEvents(new LobbyEvents(), plugin);
        pm.registerEvents(new PluginItemEvents(), plugin);
        pm.registerEvents(new ChatEvent(), plugin);
        pm.registerEvents(new ScoreboardEvent(), plugin);
        pm.registerEvents(new TabEvent(), plugin);

        getCommand("spawn").setExecutor(new spawn());

        CommandHandler.registerCommand("setspawn", new setspawn());
        CommandHandler.registerCommand("admin", new admin());
        CommandHandler.registerCommand("test", new test());

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public void onDisable() {

    }

    public TagsManager getTagsManager() {
        return tagsManager;
    }

    public TabManager getTabManager() {
        return tabManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public InventoriesManager getInventoriesManager() {
        return inventoriesManager;
    }

    public SpawnManager getSpawnManager() {
        return spawnManager;
    }

    public static LobbyCore getPlugin() {
        return plugin;
    }
}
