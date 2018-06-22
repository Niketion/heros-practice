package net.herospvp.plugins.practiceheros.listeners;

import net.herospvp.plugins.practiceheros.locations.LocationPrefactory;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerOutMapEvent
implements Listener {
    @EventHandler
    public void onPlayerOutMap(PlayerMoveEvent event) {
        if (event.getTo().getWorld().getName().equals("Lobby") && event.getTo().getY() <= 95.0) {
            new LocationPrefactory(event.getPlayer()).teleportSpawn();
        }
    }
}

