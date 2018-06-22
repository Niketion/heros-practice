package net.herospvp.plugins.practiceheros.managers.scoreboard;

import java.text.SimpleDateFormat;
import java.util.Date;
import net.herospvp.plugins.practiceheros.elo.ELOPlayer;
import net.herospvp.plugins.practiceheros.lang.Lang;
import net.herospvp.plugins.practiceheros.managers.ConfigManager;
import net.herospvp.plugins.practiceheros.match.MatchUtils;
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

public class ScoreboardPrefactory {
    public static String[] LOBBY_SCOREBOARD(Player player) {
        return new String[]{"&r",
                "&f&l> &fPlayer",
                "&e" + player.getName(),
                "&f", "&f&l> &f" + Lang.MONEY.toString(),
                "&e" + String.valueOf(1 /*Herospvp's coin removed API*/),
                "&r&r",
                "&f&l>&f ELO",
                "&aBuildUHC &7(" + new ELOPlayer(player, "builduhc").getElo() + ")",
                "&aNoDebuff &7(" + new ELOPlayer(player, "nodebuff").getElo() + ")",
                "&aCombo &7(" + new ELOPlayer(player, "combo").getElo() + ")",
                "&aOP &7(" + new ELOPlayer(player, "op").getElo() + ")",
                "&f&f",
                "&e(" + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + "&e)",
                "&f&f&f"};
    }

    public static void WAITING(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("waiting", "dummy");
        objective.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)Lang.SCOREBOARD_NAME_WAITING.toString()));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Team timeWaiting = scoreboard.registerNewTeam("tempoinattesa");
        timeWaiting.addEntry((Object)ChatColor.RED + "");
        timeWaiting.setPrefix((Object)ChatColor.YELLOW + Unranked.TIME_WAITING);
        for (int i = 0; i < Lang.SCOREBOARD_WAITING.getStrings().length; ++i) {
            if (i == 5) continue;
            objective.getScore(Bukkit.getOfflinePlayer((String)ChatColor.translateAlternateColorCodes((char)'&', (String)Lang.SCOREBOARD_WAITING.getStrings()[i].replaceAll("%nomekit%", Unranked.KIT_WAITING).replaceAll("%typeMatch%", MatchUtils.TYPE_MATCH)))).setScore(- i);
        }
        scoreboard.getTeam("tempoinattesa").setPrefix((Object)ChatColor.YELLOW + MatchUtils.TYPE_MATCH);
        objective.getScore((Object)ChatColor.RED + "").setScore(-5);
        if (!new ConfigManager("settings/scoreboard/" + player.getName()).getConfig().getBoolean("enable")) {
            return;
        }
        player.setScoreboard(scoreboard);
    }
}

