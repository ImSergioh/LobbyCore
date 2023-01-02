package me.imsergioh.lobbycore;

import me.imsergioh.coreclient.util.ClientUtil;
import me.imsergioh.lobbycore.command.spawn;
import me.imsergioh.lobbycore.customcommands.admin;
import me.imsergioh.lobbycore.customcommands.pvpzoneleave;
import me.imsergioh.lobbycore.customcommands.setspawn;
import me.imsergioh.lobbycore.customcommands.test;
import me.imsergioh.lobbycore.event.*;
import me.imsergioh.lobbycore.instance.PluginConfig;
import me.imsergioh.lobbycore.instance.PvPZone;
import me.imsergioh.lobbycore.manager.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LobbyCore extends JavaPlugin {

    private static LobbyCore plugin;

    private SpawnManager spawnManager;

    private LobbyMenuHandler lobbyMenuHandler;

    @Override
    public void onEnable() {
        plugin = this;
        ConfigManager.setup();
        spawnManager = new SpawnManager("mainSpawn.yml");
        new AutoSpawnManager();

        if(ConfigManager.isConfigOnConfig("setCustomTime")){
            new CustomTimeManager(ConfigManager.getMainConfig().config().getLong("customTime"));
        }

        if(ConfigManager.isConfigOnConfig("joinItemsEnabled")){
            lobbyMenuHandler = new LobbyMenuHandler();
        }

        if(ConfigManager.isConfigOnConfig("pvpzones")){
            PvPZone.setup();
        }

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new SpawnEvents(), plugin);
        pm.registerEvents(new CustomCommandsEvents(), plugin);
        pm.registerEvents(new LobbyEvents(), plugin);
        pm.registerEvents(new ChatEvent(), plugin);

        getCommand("spawn").setExecutor(new spawn());

        CommandHandler.registerCommand("setspawn", new setspawn());
        CommandHandler.registerCommand("admin", new admin());
        CommandHandler.registerCommand("test", new test());

        getCommand("pvpzoneleavejajasalu7").setExecutor(new pvpzoneleave());

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    public LobbyMenuHandler getLobbyMenuHandler() {
        return lobbyMenuHandler;
    }

    public SpawnManager getSpawnManager() {
        return spawnManager;
    }

    public static LobbyCore getPlugin() {
        return plugin;
    }
}
