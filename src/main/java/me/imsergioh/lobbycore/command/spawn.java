package me.imsergioh.lobbycore.command;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.lobbycore.manager.SpawnManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class spawn implements CommandExecutor {

    private static LobbyCore plugin = LobbyCore.getPlugin();
    private static SpawnManager spawnManager = plugin.getSpawnManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(!spawnManager.isSpawnSet()) {
                player.sendMessage(ChatColor.RED+"El spawn no está establecido.");
                return true;
            }
            player.teleport(spawnManager.getSpawnLocation());
        }
        return false;
    }
}
