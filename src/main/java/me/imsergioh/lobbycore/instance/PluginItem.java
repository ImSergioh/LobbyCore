package me.imsergioh.lobbycore.instance;

import me.imsergioh.lobbycore.manager.PluginItemManager;
import me.imsergioh.lobbycore.util.ChatUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PluginItem {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    private List<String> commands = new ArrayList<>();

    public PluginItem(Material material, int amount, byte data){
        this.itemStack = new ItemStack(material, amount, data);
        this.itemMeta = itemStack.getItemMeta();
        PluginItemManager.reg(this);
    }

    public void setName(String name){
        itemMeta.setDisplayName(ChatUtil.chatColor(name));
        reloadItemMeta();
    }

    public void setLores(List<String> lores){
        List<String> list = new ArrayList<>();
        for(String all : lores){
            list.add(ChatUtil.chatColor("&7"+all));
        }
        itemMeta.setLore(list);
        reloadItemMeta();
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public void reloadItemMeta(){
        itemStack.setItemMeta(itemMeta);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public boolean hasCommands(){
        return !commands.isEmpty();
    }

    public List<String> getCommands() {
        return commands;
    }
}
