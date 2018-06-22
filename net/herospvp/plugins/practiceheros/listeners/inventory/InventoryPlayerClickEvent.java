package net.herospvp.plugins.practiceheros.listeners.inventory;

import net.herospvp.plugins.practiceheros.lang.Lang;
import net.herospvp.plugins.practiceheros.listeners.inventory.utils.Editor;
import net.herospvp.plugins.practiceheros.listeners.inventory.utils.InventoryUtils;
import net.herospvp.plugins.practiceheros.locations.LocationPrefactory;
import net.herospvp.plugins.practiceheros.managers.ConfigManager;
import net.herospvp.plugins.practiceheros.managers.MenuManager;
import net.herospvp.plugins.practiceheros.managers.scoreboard.ScoreboardManager;
import net.herospvp.plugins.practiceheros.managers.scoreboard.ScoreboardPrefactory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;

public class InventoryPlayerClickEvent
implements Listener {
    public static String KIT;

    @EventHandler(priority=EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent event) {
        if (((Player)event.getWhoClicked()).getWorld().getName().equalsIgnoreCase("Lobby")) {
            Player player = (Player)event.getWhoClicked();
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
            try {
                String title = event.getClickedInventory().getTitle();
                if (MenuManager.isMenu(event.getInventory())) {
                    switch (title) {
                        case "Modifica kit": {
                            if (event.getCurrentItem().getType() == Material.AIR) break;
                            player.setLevel(event.getSlot());
                            KIT = ChatColor.stripColor((String)event.getCurrentItem().getItemMeta().getDisplayName()).toUpperCase();
                            Editor.addEditor(player, ChatColor.stripColor((String)event.getCurrentItem().getItemMeta().getDisplayName()).toUpperCase());
                            new ScoreboardManager("editor", "&6Editor", Lang.SCOREBOARD_EDITOR.getStrings()).scoreboard(player);
                            new LocationPrefactory(player).teleportEditSpawn();
                            new InventoryUtils(player).clearInventory();
                            return;
                        }
                        case "Impostazioni": {
                            if (event.getCurrentItem().getType() == Material.AIR || event.getCurrentItem().getType() != Material.BEACON) break;
                            if (new ConfigManager("settings/scoreboard/" + player.getName()).getConfig().getBoolean("enable")) {
                                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                                new ConfigManager("settings/scoreboard/" + player.getName()).set("enable", false);
                                player.sendMessage(Lang.SCOREBOARD_DISABLED.toString());
                                break;
                            }
                            if (new ConfigManager("settings/scoreboard/" + player.getName()).getConfig().getBoolean("enable")) break;
                            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                            new ScoreboardManager("join1", "&6Practice", ScoreboardPrefactory.LOBBY_SCOREBOARD(player)).scoreboard(player);
                            new ConfigManager("settings/scoreboard/" + player.getName()).set("enable", true);
                            player.sendMessage(Lang.SCOREBOARD_ENABLED.toString());
                        }
                    }
                }
            }
            catch (NullPointerException title) {
                // empty catch block
            }
        }
    }
}

