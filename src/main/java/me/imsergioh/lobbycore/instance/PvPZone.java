package me.imsergioh.lobbycore.instance;

import me.imsergioh.lobbycore.LobbyCore;
import me.imsergioh.lobbycore.event.PvPZoneEvents;
import me.imsergioh.lobbycore.util.PluginUtil;
import me.imsergioh.spigotcore.handler.MenuHandler;
import me.imsergioh.spigotcore.util.CollectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import us.smartmc.smartcorespigot.instance.CorePlayer;

import java.util.*;

public class PvPZone {

    private static LobbyCore plugin = LobbyCore.getPlugin();

    private static MenuHandler menuHandler;
    private static PluginConfig plConfig;
    private static HashMap<String, PvPZone> zones = new HashMap<>();
    public static PvPZone get(String name){
        return zones.get(name);
    }

    public static void setup(){
        plConfig = new PluginConfig(plugin, "", "pvpZones.yml");
        plConfig.saveConfig();

        Bukkit.getPluginManager().registerEvents(new PvPZoneEvents(), plugin);

        try {
            Set<String> names = plConfig.config().getConfigurationSection("zones").getKeys(false);

            for (String name : names) {
                new PvPZone(name);
            }
        } catch (Exception e){}

        menuHandler = new MenuHandler("pvpzone", plugin);
    }

    public static boolean canPvP(Player damager, Player target){
        PvPZone dZone = getZone(damager);
        PvPZone tZone = getZone(target);
        if(dZone != null && tZone != null){
            return dZone!=tZone;
        }
        return true;
    }

    private static void configDefaults(String name){
        String path = "zones."+name+".";
        plConfig.registerDefault(path+"randomTeleport", false);
        plConfig.registerDefault(path+"teleports", CollectionUtils.list("-720,65,-203,0,0"));
        plConfig.registerDefault(path+"enterLocation", "X,X,-226");
        plConfig.registerDefault(path+"comparator", "<");
    }

    public static void quitPlayer(Player player, boolean teleport){
        for(PvPZone zone : zones.values()){
            zone.quit(player, teleport);
        }
    }

    public static PvPZone isIntoZone(Player player){
        for(PvPZone zone : zones.values()){
            return zone.enteredZoneA(player);
        }
        return null;
    }

    public static PvPZone getZone(Player player){
        for(PvPZone zone : zones.values()){
            if(zone.contains(player)) {
                return zone;
            }
        }
        return null;
    }

    private String name;
    private List<Location> teleports = new ArrayList<>();

    private Location spawn;
    private String enteredLocation;
    private Set<Player> players = new HashSet<>();

    public PvPZone(String name){
        this.name = name;
        loadFromConfig();

        zones.put(name, this);
    }

    public void join(Player player){
        if(players.contains(player)) {
            return;
        }

        try {
            Location spawn = getSpawn();
            if(spawn != null) {
                player.teleport(spawn);
            }
            MenuHandler.getHandler("pvpzone").getCoreMenu("main").setInventory(player);
        } catch (Exception e){}
        players.add(player);
    }

    public boolean contains(Player player) {
        return players.contains(player);
    }

    public void quit(Player player, boolean teleport){
        if (players.contains(player)) {
            players.remove(player);
            if(teleport) {
                player.teleport(spawn);
            }
            MenuHandler.getHandler("main").getCoreMenu("joinItems").setInventory(player);
        }
    }

    public Location getSpawn(){
        int size = plConfig.config().getStringList("zones."+name+".teleports").size();
        return teleports.get(new Random().nextInt(size));
    }

    private void loadFromConfig(){
        configDefaults(name);
        plConfig.saveConfig();

        enteredLocation = plConfig.config().getString("zones."+name+".enterLocation");

        String teleportsPath = "zones."+name+".teleports";
        if(plConfig.config().getStringList(teleportsPath) != null){
            List<String> list = plConfig.config().getStringList(teleportsPath);
            for(String line : list){
                teleports.add(PluginUtil.stringToLocation(line));
            }
        }

        if(plConfig.config().contains("zones."+name+".spawn")) {
            spawn = PluginUtil.stringToLocation(plConfig.config().getString("zones."+name+".spawn"));
        }
    }

    public void setSpawn(Location location){
        spawn = location;
        plConfig.config().set("zones."+name+".spawn", PluginUtil.locationToString(location));
        plConfig.saveConfig();
    }

    public void addLocation(Location location){
        teleports.add(location);

        List<String> toSaveList = new ArrayList<>();

        if(teleports != null) {
            for (Location loc : teleports) {
                toSaveList.add(PluginUtil.locationToString(loc));
            }
        }
        toSaveList.add(PluginUtil.locationToString(location));
        plConfig.config().set("zones."+name+".teleports", toSaveList);
        plConfig.saveConfig();
    }

    private boolean hasPlayer(Player player){
        return players.contains(player);
    }

    private PvPZone enteredZoneA(Player player){
        int x = ((Number) player.getLocation().getX()).intValue();
        int y = ((Number) player.getLocation().getY()).intValue();
        int z = ((Number) player.getLocation().getZ()).intValue();

        int sumPlayer = getSum(x, y, z);
        int zoneSum = getZoneSum(player);

        boolean found = false;

        if(getComparator().equals(">")){
            if(sumPlayer>zoneSum){
                found = true;
            }
        } if(getComparator().equals("<")){
            if(sumPlayer<zoneSum){
                found = true;
            }
        } else {
            if(sumPlayer==zoneSum){
                found = true;
            }
        }

        if(found){
            return this;
        }
        return null;
    }

    private int getZoneSum(Player player){
        String copyEntered = formattedCopyEnteredString(player);
        String[] args = copyEntered.split(",");
        int[] numbers = new int[args.length];

        for(int i=0;i<args.length;i++){
            numbers[i] = Integer.parseInt(args[i]);
        }
        return getArraySum(numbers);
    }

    private int getArraySum(int[] array){
        int result = 0;
        for(int number : array){
            result += number;
        }
        return result;
    }
    private int getSum(int... numbers){
        int result = 0;
        for(int number : numbers){
            result += number;
        }
        return result;
    }

    private String formattedCopyEnteredString(Player player){
        int x = ((Number) player.getLocation().getX()).intValue();
        int y = ((Number) player.getLocation().getY()).intValue();
        int z = ((Number) player.getLocation().getZ()).intValue();

        int changed = 0;
        String copyEntered = enteredLocation;
        while (copyEntered.contains("X")) {
            String toChange = "";
            switch (changed) {
                case 0:
                    toChange = x + "";
                    break;
                case 1:
                    toChange = y + "";
                    break;
                case 2:
                    toChange = z + "";
                    break;
            }
            copyEntered = copyEntered.replaceFirst("X", toChange);
            changed++;
        }
        return copyEntered;
    }

    private String getComparator(){
        return plConfig.config().getString("zones."+name+".comparator");
    }


}
