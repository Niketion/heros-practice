package net.herospvp.plugins.practiceheros.listeners;

import net.herospvp.plugins.practiceheros.listeners.inventory.utils.Editor;
import net.herospvp.plugins.practiceheros.managers.KitManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class DoorOpenEvent
implements Listener {
    @EventHandler
    public void onDoorOpen(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.WOODEN_DOOR) {
            event.setCancelled(true);
            if (Editor.isEditorZone(event.getPlayer())) {
                Player player = event.getPlayer();
                new KitManager(player.getName(), Editor.getValue(event.getPlayer())).deserialize();
                Editor.forceRemove(event.getPlayer());
            }
        }
    }
}

