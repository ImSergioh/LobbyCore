package me.imsergioh.lobbycore.manager;

import me.imsergioh.lobbycore.instance.PluginConfig;
import me.imsergioh.lobbycore.instance.PluginInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class InventoriesManager {

    private static Map<String, InventoriesManager> registries = new HashMap<>();
    private final JavaPlugin plugin;
    private final Map<String, PluginInventory> inventories = new HashMap<>();

    public InventoriesManager(JavaPlugin plugin){
        this.plugin = plugin;
        registries.put(plugin.getName(), this);

        File invDir = new File(plugin.getDataFolder()+"/inventories");
        Arrays.stream(Objects.requireNonNull(invDir.listFiles())).forEach(file -> {
            PluginConfig config = new PluginConfig(plugin, "inventories", file.getName());
            System.out.println("Parsing inventory '"+config.fileName()+"'..");
            PluginInventory pluginInventory = new PluginInventory(config.config().getString("name"), config.config().getInt("slots"));
            pluginInventory.parseConfigSection(config.config().getConfigurationSection("items"));
            inventories.put(config.fileName(), pluginInventory);
            System.out.println("Inventory config registered: "+config.fileName()+"!");
        });
    }

    public PluginInventory get(String name){
        return inventories.get(name);
    }

    public static InventoriesManager getFromPluginName(String name){
        return registries.get(name);
    }

}
