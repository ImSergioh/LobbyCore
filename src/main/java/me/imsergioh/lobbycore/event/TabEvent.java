package me.imsergioh.lobbycore.event;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.lobbycore.instance.PluginScoreboard;
import me.imsergioh.lobbycore.manager.ConfigManager;
import me.imsergioh.lobbycore.manager.TabManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import us.smartmc.smartcorespigot.event.custominstances.PlayerLanguageChangedEvent;
import us.smartmc.smartcorespigot.event.custominstances.PlayerReceivedBackendDataEvent;

public class TabEvent implements Listener {

    private static LobbyCore plugin = LobbyCore.getPlugin();

    @EventHandler
    public void onReceiveData(PlayerReceivedBackendDataEvent event){
        Player player = event.getCorePlayer().getPlayer();
        updateTab(player);
    }

    @EventHandler
    public void onChangeLanguage(PlayerLanguageChangedEvent event){
        Player player = event.getCorePlayer().getPlayer();
        updateTab(player);
    }

    private void updateTab(Player player){
        if(ConfigManager.isConfigOnConfig("customTagsEnabled")){
            TabManager.updateTagTeams(player);
            plugin.getTabManager().sendTablist(player);
        }
    }

}
