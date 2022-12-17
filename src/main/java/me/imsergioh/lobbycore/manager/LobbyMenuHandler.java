package me.imsergioh.lobbycore.manager;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.spigotcore.handler.MenuHandler;
import me.imsergioh.spigotcore.instance.CoreMenu;
import org.bukkit.entity.Player;
import us.smartmc.smartcorespigot.SmartCoreSpigot;
import us.smartmc.smartcorespigot.instance.CorePlayer;
import us.smartmc.smartcorespigot.instance.LanguageInstance;

public class LobbyMenuHandler {

    private static final LobbyCore plugin = LobbyCore.getPlugin();

    private final MenuHandler mainHandler;

    public LobbyMenuHandler(){
        mainHandler = new MenuHandler("main", plugin);
        createMenu(mainHandler, "joinItems", true);
    }

    public void createMenu(MenuHandler menuHandler, String name, boolean forUniquePlayers){
        for(String langName : SmartCoreSpigot.getPlugin().getLanguageHandler().langNames()){
            System.out.println("Creating menu: "+name);
            registerMenu(menuHandler, name+"_"+langName, forUniquePlayers);
        }
    }

    public void openMenu(MenuHandler menuHandler, Player player, String name){
        CoreMenu coreMenu = getCoreMenu(menuHandler, player, name);
        coreMenu.openInventory(player);
    }

    public CoreMenu getCoreMenu(MenuHandler menuHandler, Player player, String name){
        LanguageInstance languageInstance = CorePlayer.get(player).getLanguage();
        return menuHandler.getCoreMenu(name+"_"+languageInstance.getName());
    }

    private void registerMenu(MenuHandler menuHandler, String name, boolean forUniquePlayers){
        menuHandler.loadMenu(name, forUniquePlayers);
    }

    public MenuHandler getMainHandler() {
        return mainHandler;
    }

    public static MenuHandler getMenuHandler(String path){
        return MenuHandler.getHandler(path);
    }

}
