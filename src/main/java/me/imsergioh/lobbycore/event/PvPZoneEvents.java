package me.imsergioh.lobbycore.event;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.lobbycore.instance.PvPZone;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PvPZoneEvents implements Listener {

    private static LobbyCore plugin = LobbyCore.getPlugin();

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        PvPZone zone = PvPZone.isIntoZone(event.getPlayer());
        if(zone != null){
            zone.join(event.getPlayer());
        } else {
            PvPZone.quitPlayer(event.getPlayer(), false);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        PvPZone.quitPlayer(event.getPlayer(), false);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void pvp(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            boolean cancel = PvPZone.canPvP((Player) event.getDamager(), (Player) event.getEntity());
            event.setCancelled(cancel);
            event.setDamage(2);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        event.setDeathMessage("");
        event.getDrops().clear();

        if(event.getEntity().getKiller() != null){
            event.getEntity().getKiller().setHealth(20);
            Player killer = event.getEntity().getKiller();
            killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*3, 0));
        }

        PvPZone zone = PvPZone.getZone(event.getEntity());
        if(zone != null){
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    event.getEntity().spigot().respawn();
                    event.getEntity().performCommand("pvpzoneleavejajasalu7");
                }
            },0);
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        PvPZone zone = PvPZone.getZone(player);

        if(zone != null){
            zone.quit(player, true);
        }
    }

}
