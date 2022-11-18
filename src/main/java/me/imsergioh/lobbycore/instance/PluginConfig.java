package me.imsergioh.lobbycore.instance;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class PluginConfig {

    private final JavaPlugin plugin;

    private final File file;
    private FileConfiguration config;

    public PluginConfig(JavaPlugin plugin, String configSubpath, String configName){
        this.plugin = plugin;
        file = new File(plugin.getDataFolder()+"/"+configSubpath, configName);
        if(!file.exists()){
            setupFile();
        }
        reloadConfig();
    }

    public PluginConfig registerDefault(String path, Object value){
        if(!config.contains(path)){
            config.set(path, value);
        }
        return this;
    }

    public String fileName(){
        return file.getName();
    }

    public FileConfiguration config() {
        return config;
    }

    public void saveConfig(){
        try {
            config.save(file);
        } catch (Exception e){e.printStackTrace();}
    }
    public void reloadConfig(){
        config = YamlConfiguration.loadConfiguration(file);
    }

    private void setupFile(){
        file.getParentFile().mkdirs();
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
