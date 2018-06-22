package net.herospvp.plugins.practiceheros.listeners.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class GeneralPlayerInventoryEvent
implements Listener {
    @EventHandler
    public void onInventoryInteract(InventoryInteractEvent event) {
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}

