package me.imsergioh.lobbycore.event;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.lobbycore.instance.PluginInventory;
import me.imsergioh.lobbycore.instance.PluginScoreboard;
import me.imsergioh.lobbycore.manager.AdminManager;
import me.imsergioh.lobbycore.manager.ConfigManager;
import me.imsergioh.lobbycore.manager.SpawnManager;
import me.imsergioh.lobbycore.manager.TabManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

public class LobbyEvents implements Listener {

    private static final LobbyCore plugin = LobbyCore.getPlugin();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage("");

        if(ConfigManager.isConfigOnConfig("customGamemodeSet")){
            Bukkit.getScheduler().runTaskLater(LobbyCore.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    event.getPlayer().setGameMode(GameMode.getByValue(ConfigManager.getMainConfig().config().getInt("customGamemode")));
                }
            },5);
        }

        if(ConfigManager.isConfigOnConfig("joinItemsEnabled")) {
            PluginInventory pluginInventory = plugin.getInventoriesManager().get(ConfigManager.getMainConfig().config().getString("joinItemsInventoriesName"));
            pluginInventory.setInventory(event.getPlayer());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        event.setQuitMessage("");
    }

    @EventHandler
    public void onDamage(WeatherChangeEvent event){
        event.setCancelled(ConfigManager.isConfigOnConfig("cancelWeatherChange"));
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            event.setCancelled(AdminManager.cancelLobbyEvent(player, "cancelDamage"));
        }
    }

    @EventHandler
    public void onDamage(FoodLevelChangeEvent event){
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            event.setCancelled(AdminManager.cancelLobbyEvent(player, "cancelDamage"));
            player.setHealth(20);
            player.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onDamage2(EntityDamageEvent event){
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            event.setCancelled(AdminManager.cancelLobbyEvent(player, "cancelDamage"));
        }
    }

    @EventHandler
    public void onDamage3(EntityDamageByBlockEvent event){
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            event.setCancelled(AdminManager.cancelLobbyEvent(player, "cancelDamage"));
        }
    }
    @EventHandler
    public void onDamage3(PlayerItemDamageEvent event){
        Player player = event.getPlayer();
        event.setCancelled(AdminManager.cancelLobbyEvent(player, "cancelDamage"));
    }


    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();

        event.setCancelled(AdminManager.cancelLobbyEvent(player, "cancelDropItem"));
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent event){
        Player player = event.getPlayer();

        event.setCancelled(AdminManager.cancelLobbyEvent(player, "cancelPickUp"));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();

        event.setCancelled(AdminManager.cancelLobbyEvent(player, "cancelBlockBreak"));
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();

        event.setCancelled(AdminManager.cancelLobbyEvent(player, "cancelBlockPlace"));
    }

}
