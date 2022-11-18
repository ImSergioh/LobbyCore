package me.imsergioh.lobbycore.event;

import me.imsergioh.lobbycore.manager.AdminManager;
import me.imsergioh.lobbycore.manager.PluginItemManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PluginItemEvents implements Listener {

    @EventHandler
    public void onClickItem(PlayerInteractEvent event){
        if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR
        || event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK){
            ItemStack itemStack = event.getPlayer().getItemInHand();
            if(PluginItemManager.isPluginItemWithCommands(itemStack)){
                event.setCancelled(!AdminManager.isAdmin(event.getPlayer()));
                PluginItemManager.executeCommands(event.getPlayer(), itemStack);
            }
        }
    }

    @EventHandler
    public void onInvClickItem(InventoryClickEvent event){
        if(event.getWhoClicked() instanceof Player){
            Player player = (Player) event.getWhoClicked();
            ItemStack itemStack = event.getCurrentItem();
            if(PluginItemManager.isPluginItem(itemStack)){
                event.setCancelled(!AdminManager.isAdmin(player));
                if(PluginItemManager.isPluginItemWithCommands(itemStack)){
                    PluginItemManager.executeCommands(player, itemStack);
                }
            }
        }

    }
}
