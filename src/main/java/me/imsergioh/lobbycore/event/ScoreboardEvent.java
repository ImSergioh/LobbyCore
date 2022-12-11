package me.imsergioh.lobbycore.event;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.lobbycore.instance.PluginScoreboard;
import me.imsergioh.lobbycore.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import us.smartmc.smartcorespigot.event.custominstances.PlayerLanguageChangedEvent;
import us.smartmc.smartcorespigot.event.custominstances.PlayerReceivedBackendDataEvent;

public class ScoreboardEvent implements Listener {

    private static LobbyCore plugin = LobbyCore.getPlugin();

    @EventHandler
    public void onReceiveData(PlayerReceivedBackendDataEvent event){
        Player player = event.getCorePlayer().getPlayer();
        updateScoreboard(player);
    }

    @EventHandler
    public void onChangeLanguage(PlayerLanguageChangedEvent event){
        Player player = event.getCorePlayer().getPlayer();
        updateScoreboard(player);
    }

    private void updateScoreboard(Player player){
        if(ConfigManager.isConfigOnConfig("mainScoreboardEnabled")){
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    plugin.getScoreboardManager().unregister(player);
                    PluginScoreboard pluginScoreboard = plugin.getScoreboardManager().getFromName(player, ConfigManager.getMainConfig().config().getString("mainScoreboardName"));
                    pluginScoreboard.register(player);
                }
            },0);
        }
    }

}
