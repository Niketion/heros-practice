package net.herospvp.plugins.practiceheros.listeners;

import java.util.ArrayList;
import net.herospvp.plugins.practiceheros.match.MatchUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerFreezedEvent
implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        try {
            if (MatchUtils.FREEZE_START.contains(event.getPlayer().getName())) {
                event.getPlayer().teleport(event.getFrom());
            }
        }
        catch (NullPointerException nullPointerException) {
            // empty catch block
        }
    }
}

