/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  net.herospvp.plugins.coinheros.coin.Coin
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerQuitEvent
 */
package net.herospvp.plugins.practiceheros.listeners.connection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.herospvp.plugins.practiceheros.lang.Lang;
import net.herospvp.plugins.practiceheros.locations.LocationPrefactory;
import net.herospvp.plugins.practiceheros.managers.ConfigManager;
import net.herospvp.plugins.practiceheros.match.MatchUtils;
import net.herospvp.plugins.practiceheros.match.kits.KitsRanked;
import net.herospvp.plugins.practiceheros.match.kits.KitsUnranked;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent
implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (MatchUtils.PLAYER_QUIT_CHECK.containsKey(event.getPlayer().getName())) {
            Player target = Bukkit.getPlayerExact((String)MatchUtils.PLAYER_QUIT_CHECK.get(event.getPlayer().getName()));
            this.quit(target, event.getPlayer());
        } else if (this.getKeyFromValue(MatchUtils.PLAYER_QUIT_CHECK, event.getPlayer().getName()) != null) {
            Player target = Bukkit.getPlayerExact((String)((String)this.getKeyFromValue(MatchUtils.PLAYER_QUIT_CHECK, event.getPlayer().getName())));
            this.quit(event.getPlayer(), target);
        }
    }

    private void quit(Player target, Player player) {
        HashMap<String, String> match;
        target.sendMessage(Lang.PLAYER_QUIT_FROM_MATCH.toString());



        /* COIN ADD/REMOVE SYSTEM REMOVED */



        if ((match = MatchUtils.TYPE_MATCH_IN_GAME).containsKey(player.getName())) {
            switch (match.get(player.getName()).split(":")[0]) {
                case "Unranked": {
                    KitsUnranked.valueOf(match.get(player.getName()).split(":")[1]).removeNumber();
                }
                case "Ranked": {
                    KitsRanked.valueOf(match.get(player.getName()).split(":")[1]).removeNumber();
                }
            }
            target.setHealth(20.0);
            new LocationPrefactory(target).teleportLobby();
            new ConfigManager("arena").set("arene." + MatchUtils.TYPE_MATCH_IN_GAME.get(player.getName()).split(":")[3] + ".open", true);
            match.remove(player.getName());
        } else {
            new ConfigManager("arena").set("arene." + MatchUtils.TYPE_MATCH_IN_GAME.get(target.getName()).split(":")[3] + ".open", true);
            match.remove(target.getName());
        }
        MatchUtils.PLAYER_QUIT_CHECK.remove(player.getName());
    }

    private Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (!hm.get(o).equals(value)) continue;
            return o;
        }
        return null;
    }
}

