package me.imsergioh.lobbycore.manager;

import me.imsergioh.lobbycore.LobbyCore;
import org.bukkit.Bukkit;

public class AutoSpawnManager {

    private static final LobbyCore plugin = LobbyCore.getPlugin();

    private static int max;
    private static int task;

    public AutoSpawnManager() {
        if(ConfigManager.isConfigOnConfig("autoSpawnManagerEnabled")){
            max = ConfigManager.getMainConfig().config().getInt("autoSpawnHeight");
            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        double altura = player.getLocation().getY();
                        if(altura <= max){
                            player.performCommand("spawn");
                        }
                    });
                }
            },0,40);
        }
    }

}
