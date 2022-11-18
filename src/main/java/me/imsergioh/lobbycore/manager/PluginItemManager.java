package me.imsergioh.lobbycore.manager;

import me.imsergioh.lobbycore.instance.PluginInventory;
import me.imsergioh.lobbycore.instance.PluginItem;
import me.imsergioh.lobbycore.util.PluginUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PluginItemManager {

    private static Set<PluginItem> items = new HashSet<>();

    public static void executeCommands(Player player, List<String> commands){
        commands.forEach(label -> {
            executeCommand(player, label);
        });
    }

    public static void executeCommand(Player player, String label){
        String name = label.split(" ")[0];
        String[] args = label.replaceFirst(name+" ", "").split(" ");
        switch (name.toLowerCase()){
            case "server":
                PluginUtil.sendPlayerToServer(player, args[0]);
                break;
            case "command":
                String command = "";
                for(int i=0;i<args.length;i++){
                    if(i >= args.length-1) {
                        command += args[i] + " ";
                    } else {
                        command += args[i];
                    }
                }
                player.performCommand(command);
                break;
            case "openinventory":
                String pluginName = args[0].split("/")[0];
                String inventoryName = args[0].split("/")[1];
                if(!inventoryName.contains(".yml")){
                    inventoryName += ".yml";
                }
                InventoriesManager manager = InventoriesManager.getFromPluginName(pluginName);
                PluginInventory inventory = manager.get(inventoryName);
                player.openInventory(inventory.getInventory());

                break;
            case "setinventory":
                pluginName = args[0].split("/")[0];
                inventoryName = args[0].split("/")[1];
                if(!inventoryName.contains(".yml")){
                    inventoryName += ".yml";
                }
                manager = InventoriesManager.getFromPluginName(pluginName);
                if(manager != null){
                    inventory = manager.get(inventoryName);
                    inventory.setInventory(player);
                }
                break;
        }
    }

    public static void reg(PluginItem item){
        items.add(item);
    }

    public static void executeCommands(Player player, ItemStack itemStack){
        for(PluginItem item : items){
            if(item.getItemStack().equals(itemStack)){
                List<String> list = item.getCommands();
                list.forEach(label -> {
                    executeCommand(player, label);
                });
            }
        }
    }

    public static boolean isPluginItemWithCommands(ItemStack itemStack){
        for(PluginItem item : items){
            if(item.getItemStack().equals(itemStack)){
                return item.hasCommands();
            }
        }
        return false;
    }

    public static boolean isPluginItem(ItemStack itemStack){
        for(PluginItem all : items){
            if(all.getItemStack().equals(itemStack)){
                return true;
            }
        }
        return false;
    }

}
