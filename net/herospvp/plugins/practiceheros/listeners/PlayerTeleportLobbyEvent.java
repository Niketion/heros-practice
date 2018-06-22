package net.herospvp.plugins.practiceheros.listeners;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportLobbyEvent
implements Listener {
    @EventHandler
    public void on(PlayerTeleportEvent event) {
        if (event.getTo().getWorld().getName().equals("Lobby")) {
            event.getPlayer().setFoodLevel(20);
        }
    }
}

