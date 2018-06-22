package net.herospvp.plugins.practiceheros.listeners.connection;

import java.util.List;
import net.herospvp.plugins.practiceheros.listeners.connection.utils.ConnectionUtils;
import net.herospvp.plugins.practiceheros.listeners.inventory.utils.Editor;
import net.herospvp.plugins.practiceheros.listeners.inventory.utils.InventoryUtils;
import net.herospvp.plugins.practiceheros.locations.LocationPrefactory;
import net.herospvp.plugins.practiceheros.managers.ConfigManager;
import net.herospvp.plugins.practiceheros.managers.scoreboard.ScoreboardManager;
import net.herospvp.plugins.practiceheros.managers.scoreboard.ScoreboardPrefactory;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent
implements Listener {
    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();
        if (new ConfigManager("noedit/quit").getConfig().getStringList("quit").contains(player.getName())) {
            Editor.forceRemove(event.getPlayer());
            List<String> list = new ConfigManager("noedit/quit").getConfig().getStringList("quit");
            list.remove(player.getName());
            new ConfigManager("noedit/quit").set("quit", list);
        }
        new InventoryUtils(player).clearInventory();
        player.setHealth(20);
        player.setFoodLevel(20);
        ConnectionUtils.joinItem(player);
        new ScoreboardManager("join", "&6Practice", ScoreboardPrefactory.LOBBY_SCOREBOARD(player)).scoreboard(player);
        for (Player players : Bukkit.getServer().getOnlinePlayers()) {
            ConnectionUtils.hidePlayer(players, player);
            ConnectionUtils.hidePlayer(player, players);
        }
        new LocationPrefactory(event.getPlayer()).teleportSpawn();
    }
}

