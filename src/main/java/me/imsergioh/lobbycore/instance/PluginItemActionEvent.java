package me.imsergioh.lobbycore.instance;

import org.bukkit.entity.Player;

public interface PluginItemActionEvent {

    void onEvent(Player player, PluginItem pluginItem, String label);

}
