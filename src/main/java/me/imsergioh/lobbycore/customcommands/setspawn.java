package me.imsergioh.lobbycore.customcommands;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.lobbycore.instance.CustomCommand;
import me.imsergioh.lobbycore.manager.SpawnManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setspawn implements CustomCommand {

    private static LobbyCore plugin = LobbyCore.getPlugin();
    private static SpawnManager spawnManager = plugin.getSpawnManager();

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if(sender.hasPermission("*")){
            if(sender instanceof Player){
                Player player = (Player) sender;
                spawnManager.setSpawn(player.getLocation(), player.getEyeLocation().getYaw(), player.getEyeLocation().getPitch());
                player.sendMessage(ChatColor.GREEN+"Spawn establecido!");
                return true;
            }
            sender.sendMessage("Comando para administradores.");
            return true;
        }
        return false;
    }
}
