package me.imsergioh.lobbycore.customcommands;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.lobbycore.instance.CustomCommand;
import me.imsergioh.lobbycore.manager.AdminManager;
import me.imsergioh.lobbycore.manager.SpawnManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class admin implements CustomCommand {

    private static LobbyCore plugin = LobbyCore.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if(sender.hasPermission("lobbycore.admin")){
            if(args.length == 0){
                if(sender instanceof Player){
                    Player player = (Player) sender;
                    AdminManager.toggleAdmin(player);
                } else {
                    sender.sendMessage("Especifica un jugador.");
                }
                return true;
            }

            try {
                Player target = Bukkit.getPlayer(args[0]);
                AdminManager.toggleAdmin(target);
            } catch (Exception e){
                sender.sendMessage(ChatColor.RED+"Jugador no encontrado.");
            }

            return true;
        }
        return false;
    }
}
