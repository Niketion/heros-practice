package net.herospvp.plugins.practiceheros.listeners;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageEvent
implements Listener {
    @EventHandler
    public void on(EntityDamageEvent event) {
        if (event.getEntity().getWorld().getName().equals("Lobby")) {
            event.setCancelled(true);
        }
    }
}

