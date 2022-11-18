package me.imsergioh.lobbycore.event;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.lobbycore.manager.ConfigManager;
import me.imsergioh.lobbycore.manager.SpawnManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SpawnEvents implements Listener {

    private static LobbyCore plugin = LobbyCore.getPlugin();
    private static SpawnManager spawnManager = plugin.getSpawnManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        tryTeleportToSpawn(event.getPlayer());
    }

    private void tryTeleportToSpawn(Player player) {
        if(ConfigManager.isConfigOnConfig("teleportOnJoin")) {
            if (spawnManager.isSpawnSet()) {
                player.teleport(spawnManager.getSpawnLocation());
            }
        }
    }

}
