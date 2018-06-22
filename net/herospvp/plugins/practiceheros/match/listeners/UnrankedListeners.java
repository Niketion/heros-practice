package net.herospvp.plugins.practiceheros.match.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import net.herospvp.plugins.practiceheros.PracticeHeros;
import net.herospvp.plugins.practiceheros.lang.Lang;
import net.herospvp.plugins.practiceheros.listeners.inventory.utils.Editor;
import net.herospvp.plugins.practiceheros.listeners.inventory.utils.InventoryUtils;
import net.herospvp.plugins.practiceheros.locations.LocationSerialize;
import net.herospvp.plugins.practiceheros.managers.ConfigManager;
import net.herospvp.plugins.practiceheros.managers.KitManager;
import net.herospvp.plugins.practiceheros.managers.scoreboard.ScoreboardManager;
import net.herospvp.plugins.practiceheros.managers.scoreboard.ScoreboardPrefactory;
import net.herospvp.plugins.practiceheros.match.MatchUtils;
import net.herospvp.plugins.practiceheros.match.Unranked;
import net.herospvp.plugins.practiceheros.match.kits.KitsUnranked;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class UnrankedListeners
implements Listener {
    private HashMap<String, String> nameKit = new HashMap();

    @EventHandler(priority=EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent event) {
        if (((Player)event.getWhoClicked()).getWorld().getName().equalsIgnoreCase("Lobby")) {
            final Player player = (Player)event.getWhoClicked();
            try {
                if (Editor.isEditorZone(player)) {
                    return;
                }
            }
            catch (NullPointerException nullPointerException) {
                // empty catch block
            }
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            if (event.getClickedInventory() != null && event.getClickedInventory().getTitle() != null && event.getCurrentItem() != null && event.getClickedInventory().getTitle().equals("Unranked") && event.getCurrentItem().getType() != Material.AIR) {
                final String nameKit = ChatColor.stripColor((String)event.getCurrentItem().getItemMeta().getDisplayName()).toUpperCase();
                player.closeInventory();
                this.nameKit.put(player.getName(), nameKit);
                Unranked.KIT_WAITING = nameKit;
                MatchUtils.TYPE_MATCH = "UNRANKED";
                int pingPlayer = ((CraftPlayer)player).getHandle().ping;
                if (new Unranked(nameKit).thereIsMatch()) {
                    final Player challenger = new Unranked(nameKit).getPlayer();
                    int pingChallenger = ((CraftPlayer)challenger).getHandle().ping;
                    player.sendMessage(Lang.CHALLENGER_FOUND.toString().replaceAll("%ping%", "" + pingChallenger + "").replaceAll("%nome%", challenger.getName()));
                    challenger.sendMessage(Lang.CHALLENGER_FOUND.toString().replaceAll("%ping%", "" + pingPlayer + "").replaceAll("%nome%", player.getName()));
                    MatchUtils.SCOREBOARD_WAITING.get(challenger.getName()).cancel();
                    final FileConfiguration config = new ConfigManager("arena").getConfig();
                    int maxNumber = config.getConfigurationSection("arene").getKeys(false).size();
                    int numberArena = 1;
                    new Unranked(nameKit).deleteWaiting();
                    for (String string : config.getConfigurationSection("arene").getKeys(false)) {
                        if (maxNumber == numberArena) break;
                        if (config.getBoolean("arene." + numberArena + ".open")) {
                            this.start(player, challenger, numberArena, nameKit);
                            return;
                        }
                        ++numberArena;
                    }
                    player.sendMessage(Lang.ARENAS_OCCUPIED.toString());
                    challenger.sendMessage(Lang.ARENAS_OCCUPIED.toString());
                    MatchUtils.SCOREBOARD_WAITING.put(player.getName(), new BukkitRunnable(){

                        public void run() {
                            for (int i = 1; i < config.getConfigurationSection("arene").getKeys(false).size(); ++i) {
                                if (!config.getBoolean("arene." + i + ".open")) continue;
                                UnrankedListeners.this.start(player, challenger, i, nameKit);
                                break;
                            }
                        }
                    }.runTaskTimer((Plugin)PracticeHeros.getInstance(), 0, 40));
                } else {
                    new Unranked(nameKit).createWaitingMatch(player);
                    player.sendMessage(Lang.SEARCH_CHALLENGER.toString());
                    player.getInventory().clear();
                    player.getInventory().setItem(8, InventoryUtils.itemStackBuilder(Material.REDSTONE, Lang.EXIT_FROM_WAITING_ITEM.toString().replaceAll("%type%", nameKit)));
                    ScoreboardPrefactory.WAITING(player);
                    MatchUtils.SCOREBOARD_WAITING.put(player.getName(), new BukkitRunnable(){

                        public void run() {
                            if (Unranked.TIME_WAITING != null) {
                                String timeWaiting;
                                int timeMinute = Integer.parseInt(Unranked.TIME_WAITING.split(":")[0]);
                                int timeSecond = Integer.parseInt(Unranked.TIME_WAITING.split(":")[1]);
                                Unranked.TIME_WAITING = timeSecond != 59 ? ((timeWaiting = String.valueOf((Object)(timeSecond + 1))).length() == 2 ? "" + timeMinute + ":" + timeWaiting : "" + timeMinute + ":0" + timeWaiting) : String.valueOf((Object)(timeMinute + 1)) + ":00";
                            } else {
                                Unranked.TIME_WAITING = "0:00";
                            }
                            ScoreboardManager.updateWaiting(player);
                        }
                    }.runTaskTimer((Plugin)PracticeHeros.getInstance(), 0, 20));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getPlayer().getWorld().getName().equals("Arene") && event.getItem() != null && event.getItem().getType() != null) {
            Material item = event.getItem().getType();
            if (item == Material.BOOK) {
                new KitManager(this.nameKit.get(event.getPlayer().getName())).serialize(event.getPlayer());
                this.nameKit.remove(event.getPlayer().getName());
            } else if (item == Material.ENCHANTED_BOOK) {
                new KitManager(event.getPlayer().getName(), this.nameKit.get(event.getPlayer().getName())).serialize(event.getPlayer());
                this.nameKit.remove(event.getPlayer().getName());
            }
        }
    }

    private void start(Player player, Player challenger, int numberArena, String nameKit) {
        FileConfiguration config = new ConfigManager("arena").getConfig();
        String message = Lang.ARENA_FOUNDED.toString().replaceAll("%id%", "" + numberArena + "").replaceAll("%creator%", config.getString("arene." + numberArena + ".creator"));
        player.sendMessage(message);
        challenger.sendMessage(message);
        new InventoryUtils(player).clearInventory();
        new InventoryUtils(challenger).clearInventory();
        player.teleport(new LocationSerialize(player).serialize(new ConfigManager("arena"), "arene." + numberArena + ".1"));
        challenger.teleport(new LocationSerialize(player).serialize(new ConfigManager("arena"), "arene." + numberArena + ".2"));
        if (new ConfigManager("kits/" + player.getName()).getFile().exists() && new ConfigManager("kits/" + player.getName()).getConfig().getStringList(nameKit) != null) {
            player.getInventory().setItem(1, InventoryUtils.itemStackBuilder(Material.ENCHANTED_BOOK, "&6Custom kit " + Lang.RIGHT_CLICK.toString()));
        }
        if (new ConfigManager("kits/" + challenger.getName()).getFile().exists() && new ConfigManager("kits/" + challenger.getName()).getConfig().getStringList(nameKit) != null) {
            challenger.getInventory().setItem(1, InventoryUtils.itemStackBuilder(Material.ENCHANTED_BOOK, "&6Custom kit " + Lang.RIGHT_CLICK.toString()));
        }
        player.getInventory().setItem(0, InventoryUtils.itemStackBuilder(Material.BOOK, "&aDefault kit " + (Object)((Object)Lang.RIGHT_CLICK)));
        challenger.getInventory().setItem(0, InventoryUtils.itemStackBuilder(Material.BOOK, "&aDefault kit " + (Object)((Object)Lang.RIGHT_CLICK)));
        new ConfigManager("arena").set("arene." + numberArena + ".open", false);
        KitsUnranked.valueOf(nameKit).addNumber();
        MatchUtils.TYPE_MATCH_IN_GAME.put(player.getName(), "Unranked:" + nameKit + ":" + challenger.getName() + ":" + numberArena);
        MatchUtils.PLAYER_QUIT_CHECK.put(player.getName(), challenger.getName());
        MatchUtils.FREEZE_START.add(player.getName());
        MatchUtils.FREEZE_START.add(challenger.getName());
        PracticeHeros.getInstance().getServer().getScheduler().scheduleSyncDelayedTask((Plugin)PracticeHeros.getInstance(), () -> {
            player.sendMessage(Lang.SECOND_3.toString());
            challenger.sendMessage(Lang.SECOND_3.toString());
            PracticeHeros.getInstance().getServer().getScheduler().scheduleSyncDelayedTask((Plugin)PracticeHeros.getInstance(), () -> {
                player.sendMessage(Lang.SECOND_2.toString());
                challenger.sendMessage(Lang.SECOND_2.toString());
                player.showPlayer(challenger);
                challenger.showPlayer(player);
                PracticeHeros.getInstance().getServer().getScheduler().scheduleSyncDelayedTask((Plugin)PracticeHeros.getInstance(), () -> {
                    player.sendMessage(Lang.SECOND_1.toString());
                    challenger.sendMessage(Lang.SECOND_1.toString());
                    PracticeHeros.getInstance().getServer().getScheduler().scheduleSyncDelayedTask((Plugin)PracticeHeros.getInstance(), () -> {
                        MatchUtils.FREEZE_START.remove(player.getName());
                        MatchUtils.FREEZE_START.remove(challenger.getName());
                    }
                    , 20);
                }
                , 20);
            }
            , 20);
        }
        , 20);
        if (MatchUtils.SCOREBOARD_WAITING.containsKey(player.getName())) {
            MatchUtils.SCOREBOARD_WAITING.remove(player.getName()).cancel();
        }
    }

}

