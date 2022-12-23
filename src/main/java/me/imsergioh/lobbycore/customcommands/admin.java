package me.imsergioh.lobbycore.customcommands;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.lobbycore.instance.CustomCommand;
import me.imsergioh.lobbycore.instance.PluginConfig;
import me.imsergioh.lobbycore.instance.PvPZone;
import me.imsergioh.lobbycore.manager.AdminManager;
import me.imsergioh.lobbycore.manager.SpawnManager;
import me.imsergioh.lobbycore.util.ChatUtil;
import me.imsergioh.spigotcore.instance.PluginConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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

            if(args[0].equalsIgnoreCase("reload")){
                PluginConfig.reloadAllConfigs();
                PluginConfiguration.reloadAll();
                sender.sendMessage(ChatUtil.chatColor("&aRecargado correctamente!"));
                return true;
            }

            if(args[0].equalsIgnoreCase("pvpzone")){
                if(args.length >= 2){
                    switch (args[1].toLowerCase()){
                        case "create":
                            try {
                                new PvPZone(args[2]);
                                sender.sendMessage(ChatUtil.chatColor("&aCreated! (Don't forget to addspawns and setspawn)"));
                            } catch (Exception e){
                                e.printStackTrace();
                                sender.sendMessage(ChatUtil.chatColor("&cCorrect usage: /admin pvpzone create <name>"));
                            }
                            break;
                        case "addspawn":
                            try {
                                Player player = (Player) sender;
                                Location location = player.getLocation();
                                location.setYaw(player.getEyeLocation().getYaw());
                                location.setPitch(player.getEyeLocation().getPitch());
                                PvPZone zone = PvPZone.get(args[2]);
                                zone.addLocation(((Player) sender).getLocation());
                                player.sendMessage(ChatUtil.chatColor("&aAdded!"));
                            } catch (Exception e){
                                e.printStackTrace();
                                sender.sendMessage(ChatUtil.chatColor("&cCorrect usage: /admin pvpzone addspawn <name>"));
                            }
                            break;
                        case "setspawn":
                            try {
                                Player player = (Player) sender;
                                Location location = player.getLocation();
                                location.setYaw(player.getEyeLocation().getYaw());
                                location.setPitch(player.getEyeLocation().getPitch());
                                PvPZone zone = PvPZone.get(args[2]);
                                zone.setSpawn(((Player) sender).getLocation());
                                player.sendMessage(ChatUtil.chatColor("&aSet!"));
                            } catch (Exception e){
                                e.printStackTrace();
                                sender.sendMessage(ChatUtil.chatColor("&cCorrect usage: /admin pvpzone setspawn <name>"));
                            }
                            break;
                    }
                    return true;
                }
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
