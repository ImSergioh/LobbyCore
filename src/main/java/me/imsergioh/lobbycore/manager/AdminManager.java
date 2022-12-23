package me.imsergioh.lobbycore.manager;

import me.imsergioh.lobbycore.instance.PvPZone;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AdminManager {

    private static Set<UUID> adminList = new HashSet<>();

    public static boolean cancelLobbyEvent(Player player, String configPath){
        if(ConfigManager.isConfigOnConfig(configPath)) {
            World world = player.getWorld();
            if (SpawnManager.isLobbyWorld(world)) {
                if (AdminManager.isAdmin(player)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isAdmin(Player player){
        return adminList.contains(player.getUniqueId());
    }

    public static boolean toggleAdmin(Player player){
        if(!adminList.contains(player.getUniqueId())){
            return enable(player);
        }
        return disable(player);
    }

    private static boolean enable(Player player){
        adminList.add(player.getUniqueId());
        player.sendMessage(ChatColor.GREEN+"Modo administrador activado.");
        return true;
    }

    private static boolean disable(Player player){
        adminList.remove(player.getUniqueId());
        player.sendMessage(ChatColor.RED+"Modo administrador desactivado.");
        return false;
    }

}
