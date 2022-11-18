package me.imsergioh.lobbycore.instance;

import me.imsergioh.lobbycore.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class PluginInventory {

    private final String title;
    private final int slots;

    private Inventory inventory;

    public PluginInventory(String title, int slots){
        this.title = title;
        this.slots = slots;
        inventory = Bukkit.createInventory(null, slots, ChatUtil.chatColor(title));
    }

    public void parseConfigSection(ConfigurationSection section){
        Set<String> names = section.getKeys(false);
        names.stream().forEach(slot -> {
            int intSlot = Integer.parseInt(slot);
            Material material = Material.getMaterial(section.getString(slot+".material"));
            int amount = section.getInt(slot+".amount");
            int data = section.getInt(slot+".data");
            PluginItem item = new PluginItem(material, amount, (byte) data);
            if(section.contains(slot+".name")){
                String itemName = section.getString(slot+".name");
                item.setName(itemName);
            }

            if(section.contains(slot+".lores")){
                item.setLores(section.getStringList(slot+".lores"));
            }

            if(section.contains(slot+".commands")){
                item.setCommands(section.getStringList(slot+".commands"));
            }

            item.reloadItemMeta();
            inventory.setItem(intSlot, item.getItemStack());
        });
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Player player){
        player.getInventory().clear();
        player.getInventory().setContents(inventory.getContents());
    }

}
