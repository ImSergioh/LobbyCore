package me.imsergioh.lobbycore.manager;

import me.imsergioh.lobbycore.instance.CustomCommand;
import me.imsergioh.lobbycore.util.ChatUtil;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class CommandHandler {

    private static HashMap<String, CustomCommand> commands = new HashMap<>();

    public static void registerCommand(String name, CustomCommand command){
        commands.put(name, command);
    }

    public static boolean tryExecuteCommand(CommandSender sender, String label){
        String name = label.split(" ")[0].replaceFirst("/", "");
        if(commands.containsKey(name)){
            String[] args = new String[0];
            if(label.contains(" ")){
                args = label.replaceFirst("/"+name+" ", "").split(" ");
            }
            try {
                return commands.get(name).onCommand(sender, label, args);
            } catch (Exception e){
                sender.sendMessage(ChatUtil.chatColor("&4Error ocurred by executing this command"));
            }
        }
        return false;
    }

}
