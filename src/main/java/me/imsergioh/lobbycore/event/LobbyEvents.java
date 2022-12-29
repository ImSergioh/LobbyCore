package me.imsergioh.lobbycore.event;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.lobbycore.instance.PvPZone;
import me.imsergioh.lobbycore.manager.AdminManager;
import me.imsergioh.lobbycore.manager.ConfigManager;
import me.imsergioh.spigotcore.handler.MenuHandler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import us.smartmc.smartcorespigot.event.custominstances.PlayerLanguageChangedEvent;
import us.smartmc.smartcorespigot.event.custominstances.PlayerReceivedBackendDataEvent;

public class LobbyEvents implements Listener {

    private static final LobbyCore plugin = LobbyCore.getPlugin();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage("");
        Player player = event.getPlayer();

        if(ConfigManager.isConfigOnConfig("customGamemodeSet")){
            Bukkit.getScheduler().runTaskLater(LobbyCore.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    event.getPlayer().setGameMode(GameMode.getByValue(ConfigManager.getMainConfig().config().getInt("customGamemode")));
                }
            },5);
        }
    }

    @EventHandler
    public void onCorePlayerLoad(PlayerReceivedBackendDataEvent event){
        Player player = event.getCorePlayer().getPlayer();

        updateJoinItemInventory(player);
    }

    @EventHandler
    public void changeInvFromLang(PlayerLanguageChangedEvent event){
        Player player = event.getCorePlayer().getPlayer();

        updateJoinItemInventory(player);

        if(PvPZone.getZone(player) != null){
            MenuHandler.getHandler("pvpzone").getCoreMenu("main").setInventory(player);
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

    @EventHandler(priority = EventPriority.LOW)
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
            player.setFoodLevel(20);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
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

    private static void updateJoinItemInventory(Player player){
        if(ConfigManager.isConfigOnConfig("joinItemsEnabled")) {
            String menuPath = ConfigManager.getMainConfig().config().getString("joinItemsMenuPath");
            MenuHandler mainHandler = MenuHandler.getHandler(menuPath);
            player.getInventory().clear();
            plugin.getLobbyMenuHandler().getCoreMenu(mainHandler, player, "joinItems").setInventory(player);
            player.updateInventory();
        }
    }

}
