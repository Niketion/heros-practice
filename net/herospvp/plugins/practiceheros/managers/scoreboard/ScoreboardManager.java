package net.herospvp.plugins.practiceheros.managers.scoreboard;

import java.io.File;
import net.herospvp.plugins.practiceheros.listeners.inventory.InventoryPlayerClickEvent;
import net.herospvp.plugins.practiceheros.managers.ConfigManager;
import net.herospvp.plugins.practiceheros.match.Unranked;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardManager {
    private Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

    public ScoreboardManager(String nameObject, String displayName, String[] strings) {
        Objective objective = this.scoreboard.registerNewObjective(nameObject, "dummy");
        objective.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)displayName));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        for (int i = 0; i < strings.length; ++i) {
            objective.getScore(Bukkit.getOfflinePlayer((String)ChatColor.translateAlternateColorCodes((char)'&', (String)strings[i].replaceAll("%kiteditor%", InventoryPlayerClickEvent.KIT)))).setScore(- i);
        }
    }

    public void scoreboard(Player player) {
        if (!new ConfigManager("settings/scoreboard/" + player.getName()).getFile().exists()) {
            new ConfigManager("settings/scoreboard/" + player.getName()).set("enable", true);
        }
        if (!new ConfigManager("settings/scoreboard/" + player.getName()).getConfig().getBoolean("enable")) {
            return;
        }
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        player.setScoreboard(this.scoreboard);
    }

    public static void updateWaiting(Player player) {
        if (!new ConfigManager("settings/scoreboard/" + player.getName()).getConfig().getBoolean("enable")) {
            return;
        }
        Scoreboard board = player.getScoreboard();
        board.getTeam("tempoinattesa").setPrefix((Object)ChatColor.YELLOW + Unranked.TIME_WAITING);
    }
}

