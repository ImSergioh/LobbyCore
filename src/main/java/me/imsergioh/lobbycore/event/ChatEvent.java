package me.imsergioh.lobbycore.event;

import me.imsergioh.lobbycore.manager.ConfigManager;
import me.imsergioh.lobbycore.util.ChatUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        String message = event.getMessage();
        event.setFormat("%2$s");
        event.setMessage(ChatUtil.chatColorWithVariables(event.getPlayer(), ConfigManager.getMainConfig().config().getString("chatFormat")).replaceAll("%message%", message));
    }
}
