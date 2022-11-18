package me.imsergioh.lobbycore.manager;

import me.imsergioh.lobbycore.LobbyCore;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class CustomTimeManager {

    private static LobbyCore plugin = LobbyCore.getPlugin();

    public CustomTimeManager(long time){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                SpawnManager.getLobbyWorlds().stream().forEach(worldName -> {
                    try {
                        World world = Bukkit.getWorld(worldName);
                        world.setTime(time);
                    } catch (Exception e){}
                });
            }
        },0,10);
    }

}
