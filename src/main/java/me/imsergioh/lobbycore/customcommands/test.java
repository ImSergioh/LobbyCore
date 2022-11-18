package me.imsergioh.lobbycore.customcommands;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.lobbycore.instance.CustomCommand;
import me.imsergioh.lobbycore.manager.AdminManager;
import me.imsergioh.lobbycore.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class test implements CustomCommand {

    private static LobbyCore plugin = LobbyCore.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if(sender.hasPermission("*")){
            Player player = (Player) sender;
            test(player);
            return true;
        }
        return false;
    }

    private void test(Player player){
        Scoreboard scoreboard = player.getScoreboard();
        Objective obj = scoreboard.registerNewObjective("playerList", "bbb");
        obj.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        Team team = scoreboard.registerNewTeam(player.getName());
        team.setPrefix(ChatUtil.chatColorWithVariables(player, "%luckperms_prefix%")+" ");
        team.setSuffix(" "+ChatUtil.chatColorWithVariables(player, "%luckperms_suffix%"));
        team.addPlayer(player);
    }

}
