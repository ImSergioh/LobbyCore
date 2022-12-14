package me.imsergioh.lobbycore.manager;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.spigotcore.handler.MenuHandler;
import me.imsergioh.spigotcore.instance.CoreMenu;
import org.bukkit.entity.Player;
import us.smartmc.smartcorespigot.instance.CorePlayer;
import us.smartmc.smartcorespigot.instance.LanguageInstance;

public class LobbyMenuHandler {

    private static final LobbyCore plugin = LobbyCore.getPlugin();

    private final MenuHandler mainHandler;

    public LobbyMenuHandler(){
        mainHandler = new MenuHandler("main", plugin);
    }

    public CoreMenu getCoreMenu(MenuHandler menuHandler, Player player, String name){
        LanguageInstance languageInstance = CorePlayer.get(player).getLanguage();
        return menuHandler.getCoreMenu(name);
    }

    public MenuHandler getMainHandler() {
        return mainHandler;
    }

    public static MenuHandler getMenuHandler(String path){
        return MenuHandler.getHandler(path);
    }

}
