package me.imsergioh.lobbycore.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class ChatUtil {

    public static String chatColor(String message){
        return message = message.replace("&", "§");
    }

    public static String chatColorWithVariables(Player player, String message){
        message = PlaceholderAPI.setPlaceholders(player, message);
        return chatColor(message);
    }

}
