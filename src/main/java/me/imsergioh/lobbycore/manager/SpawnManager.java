package me.imsergioh.lobbycore.manager;

import me.imsergioh.lobbycore.LobbyCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpawnManager {

    private static LobbyCore plugin = LobbyCore.getPlugin();

    private Location spawnLocation;

    private File file;
    private FileConfiguration config;

    private static List<String> lobbyWorlds = new ArrayList<>();

    public SpawnManager(String configName){
        setupConfig(configName);
    }

    private void setupConfig(String configName){
        file = new File(plugin.getDataFolder(), configName);
        file.getParentFile().mkdirs();

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (Exception e){e.printStackTrace();}
        }

        config = YamlConfiguration.loadConfiguration(file);
        tryLoadSpawn();
    }

    public boolean isSpawnSet(){
        return spawnLocation != null;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    private void tryLoadSpawn(){
        if(config.contains("spawnLocation")){
            spawnLocation = getLocationFromStr(config.getString("spawnLocation"));
        }
    }

    public void setSpawn(Location location, float yaw, float pitch){
        this.spawnLocation = location;
        spawnLocation.setYaw(yaw);
        spawnLocation.setPitch(pitch);
        config.set("spawnLocation", getStringFromLoc(location, yaw, pitch));
        saveConfig();
    }

    private void saveConfig(){
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void reloadConfig(){
        config = YamlConfiguration.loadConfiguration(file);
    }

    private static Location getLocationFromStr(String value){
        String[] args = value.split(";");

        return new Location(Bukkit.getWorld(args[0]),
                Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]),
                Float.parseFloat(args[4]), Float.parseFloat(args[5]));
    }

    private static String getStringFromLoc(Location location, float yaw, float pitch){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(location.getWorld().getName()+";");
        stringBuilder.append(location.getX()+";"+location.getY()+";"+location.getZ()+";");
        stringBuilder.append(yaw+";"+pitch+";");
        return stringBuilder.toString();
    }

    public static boolean isLobbyWorld(World world){
        return lobbyWorlds.contains(world.getName());
    }

    public static void setLobbyWorlds(List<String> lobbyWorlds) {
        SpawnManager.lobbyWorlds = lobbyWorlds;
    }

    public static List<String> getLobbyWorlds() {
        return lobbyWorlds;
    }
}
