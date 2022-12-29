package me.imsergioh.lobbycore.manager;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.lobbycore.instance.PluginConfig;
import me.imsergioh.lobbycore.util.ChatUtil;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.apache.logging.log4j.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import us.smartmc.smartcorespigot.SmartCoreSpigot;
import us.smartmc.smartcorespigot.instance.CorePlayer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TabManager {

    private static final LobbyCore plugin = LobbyCore.getPlugin();
    private static final TagsManager tagsManager = plugin.getTagsManager();

    private int task;
    private static FileConfiguration config;

    public TabManager(JavaPlugin plugin, PluginConfig pluginConfig){
        config = pluginConfig.config();

        List<String> list = new ArrayList<>();
        list.add("Line1");
        list.add("Line2");
        List<String> list1 = list;
        list1.add("Footer");

        for(String langName : SmartCoreSpigot.getPlugin().getLanguageHandler().langNames()){
            pluginConfig.registerDefault("tablistHeader_"+langName, list);
            pluginConfig.registerDefault("tablistFooter_"+langName, list1);
        }

        pluginConfig.saveConfig();

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if(CorePlayer.get(player) != null && CorePlayer.get(player).getLanguage() != null) {
                        updateAll(player);
                    }
                });
            }
        },0, 20*3);
    }

    public static void updateAll(Player player){
        sendTablist(player);
        if (ConfigManager.isConfigOnConfig("customTagsEnabled")) {
            updateTagTeams(player);
        }
    }

    private static void updateTagTeams(Player player){
        Scoreboard scoreboard = player.getScoreboard();
        Objective obj;
        if(scoreboard.getObjective("tags") == null) {
            obj = scoreboard.registerNewObjective("tags", "bbb");
        } else {
            obj = scoreboard.getObjective("tags");
        }
        obj.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        Bukkit.getOnlinePlayers().forEach(p -> {
            Team team;
            if(scoreboard.getTeam(p.getName()) == null) {
                team = scoreboard.registerNewTeam(p.getName());
            } else {
                team = scoreboard.getTeam(p.getName());
            }
            String prefix = tagsManager.getPrefix(p);
            String suffix = tagsManager.getSuffix(p);
            String playerListName = tagsManager.getPlayerListName(p);

            team.setPrefix(prefix);
            team.setSuffix(suffix);
            p.setPlayerListName(playerListName);
            team.addPlayer(p);
        });
    }

    private static void sendTablist(Player p){
        CraftPlayer craftplayer = (CraftPlayer) p;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        String headerStr = "";
        String footerStr = "";
        for(String all : config.getStringList("tablistHeader_"+ CorePlayer.get(p).getLanguage().getName())) {
            headerStr += all+"\n";
        } for(String all : config.getStringList("tablistFooter_"+ CorePlayer.get(p).getLanguage().getName())) {
            footerStr += all+"\n";
        }
        headerStr = replaceLast(headerStr, "\n", "");
        footerStr = replaceLast(footerStr, "\n", "");
        IChatBaseComponent header = IChatBaseComponent.ChatSerializer.a("{\"text\": \""+ ChatUtil.chatColorWithVariables(p, headerStr)+"\"}");
        IChatBaseComponent footer = IChatBaseComponent.ChatSerializer.a("{\"text\": \""+ChatUtil.chatColorWithVariables(p, footerStr)+"\"}");
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

        try {
            Field headerField = packet.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(packet, header);
            headerField.setAccessible(!headerField.isAccessible());

            Field footerField = packet.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(packet, footer);
            footerField.setAccessible(!footerField.isAccessible());
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        connection.sendPacket(packet);
    }

    private static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)"+regex+"(?!.*?"+regex+")", replacement);
    }

}
