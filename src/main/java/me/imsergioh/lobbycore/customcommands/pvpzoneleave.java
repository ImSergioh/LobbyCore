package me.imsergioh.lobbycore.customcommands;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.lobbycore.instance.CustomCommand;
import me.imsergioh.lobbycore.instance.PluginConfig;
import me.imsergioh.lobbycore.instance.PvPZone;
import me.imsergioh.lobbycore.manager.AdminManager;
import me.imsergioh.lobbycore.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class pvpzoneleave implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        PvPZone.quitPlayer((Player) sender, true);
        return false;
    }
}
