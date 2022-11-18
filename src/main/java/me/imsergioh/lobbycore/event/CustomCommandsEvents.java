package me.imsergioh.lobbycore.event;

import me.imsergioh.lobbycore.manager.CommandHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class CustomCommandsEvents implements Listener {

    @EventHandler
    public void onPlayerCommandProccess(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();
        event.setCancelled(CommandHandler.tryExecuteCommand(player, event.getMessage()));
    }

    @EventHandler
    public void onConsoleCommand(ServerCommandEvent event){
        event.setCancelled(CommandHandler.tryExecuteCommand(event.getSender(), event.getCommand()));
    }
}
