package me.imsergioh.lobbycore.instance;

import org.bukkit.command.CommandSender;

public interface CustomCommand {

    boolean onCommand(CommandSender sender, String label, String[] args);

}
