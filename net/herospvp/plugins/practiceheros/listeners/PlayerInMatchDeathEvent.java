package net.herospvp.plugins.practiceheros.listeners;

import java.util.HashMap;
import net.herospvp.plugins.practiceheros.lang.Lang;
import net.herospvp.plugins.practiceheros.listeners.connection.utils.ConnectionUtils;
import net.herospvp.plugins.practiceheros.locations.LocationPrefactory;
import net.herospvp.plugins.practiceheros.managers.ConfigManager;
import net.herospvp.plugins.practiceheros.match.MatchUtils;
import net.herospvp.plugins.practiceheros.match.kits.KitsUnranked;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerInMatchDeathEvent
implements Listener {
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        new LocationPrefactory(player).teleportLobby();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getEntity() != null && event.getEntity().getKiller() != null && event.getEntity().getWorld().getName().equals("Arene")) {
            Player player = event.getEntity();
            Player killer = event.getEntity().getKiller();
            event.setKeepInventory(true);
            event.setDeathMessage(null);
            HashMap<String, String> match = MatchUtils.TYPE_MATCH_IN_GAME;
            if (match.containsKey(killer.getName())) {
                if (!this.isRanked(killer, match)) {
                    this.unranked(player, killer, match);
                }
            } else if (match.containsKey(player.getName()) && !this.isRanked(player, match)) {
                this.unranked(player, killer, match);
            }
        }
    }

    private void unranked(Player player, Player killer, HashMap<String, String> match) {
        killer.sendMessage(Lang.YOU_WIN_MATCH_UNRANKED.toString().replaceAll("%player%", player.getName()));
        player.sendMessage(Lang.YOU_LOST_MATCH_UNRANKED.toString().replaceAll("%player%", killer.getName()).replaceAll("%health%", "" + (int)killer.getHealth() / 2 + ""));
        if (MatchUtils.PLAYER_QUIT_CHECK.containsKey(player.getName())) {
            MatchUtils.PLAYER_QUIT_CHECK.remove(player.getName());
        } else {
            MatchUtils.PLAYER_QUIT_CHECK.remove(killer.getName());
        }
        new ConfigManager("arena").set("arene." + match.get(killer.getName()).split(":")[3] + ".open", true);
        ConnectionUtils.hidePlayer(player, killer);
        ConnectionUtils.hidePlayer(killer, player);
        KitsUnranked.valueOf(match.get(killer.getName()).split(":")[1]).removeNumber();
        match.remove(killer.getName());
        killer.setHealth(20.0);
        new LocationPrefactory(killer).teleportLobby();
        player.setHealth(20.0);
        new LocationPrefactory(player).teleportLobby();
        if (MatchUtils.TYPE_MATCH_IN_GAME.containsKey(player.getName())) {
            MatchUtils.TYPE_MATCH_IN_GAME.remove(player.getName());
        } else {
            MatchUtils.TYPE_MATCH_IN_GAME.remove(killer.getName());
        }
    }

    private boolean isRanked(Player player, HashMap<String, String> match) {
        return !match.get(player.getName()).split(":")[0].equals("Unranked");
    }
}

