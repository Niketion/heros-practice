package net.herospvp.plugins.practiceheros.listeners.connection;

import java.util.HashMap;
import java.util.List;
import net.herospvp.plugins.practiceheros.PracticeHeros;
import net.herospvp.plugins.practiceheros.listeners.inventory.utils.Editor;
import net.herospvp.plugins.practiceheros.managers.ConfigManager;
import net.herospvp.plugins.practiceheros.match.MatchUtils;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

public class GeneralConnectionEvent
implements Listener {
    private static final ConfigManager CONFIG = new ConfigManager("noedit/quit");

    @EventHandler(priority=EventPriority.LOWEST)
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity().getWorld().getName().equals("Lobby")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (Editor.isEditorZone(event.getPlayer())) {
            List<String> list = CONFIG.getConfig().getStringList("quit");
            list.add(event.getPlayer().getName());
            CONFIG.set("quit", list);
            Editor.removeEditor(event.getPlayer());
        }
        if (MatchUtils.SCOREBOARD_WAITING.get(event.getPlayer().getName()) != null) {
            MatchUtils.SCOREBOARD_WAITING.get(event.getPlayer().getName()).cancel();
            MatchUtils.SCOREBOARD_WAITING.remove(event.getPlayer().getName());
        }
        for (Player from : PracticeHeros.getInstance().getServer().getOnlinePlayers()) {
            from.hidePlayer(event.getPlayer());
            EntityPlayer nmsFrom = ((CraftPlayer)from).getHandle();
            EntityPlayer nmsHiding = ((CraftPlayer)event.getPlayer()).getHandle();
            nmsFrom.playerConnection.sendPacket((Packet)PacketPlayOutPlayerInfo.removePlayer((EntityPlayer)nmsHiding));
        }
        event.setQuitMessage(null);
    }
}

