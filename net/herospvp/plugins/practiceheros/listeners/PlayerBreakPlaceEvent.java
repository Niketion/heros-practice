package net.herospvp.plugins.practiceheros.listeners;

import java.util.List;
import net.herospvp.plugins.practiceheros.managers.ConfigManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerBreakPlaceEvent
implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer().getWorld().getName().equals("Arene")) {
            List list = new ConfigManager("noedit/build").getConfig().getStringList("build." + event.getPlayer().getName());
            list.add("" + event.getBlock().getX() + ":" + event.getBlock().getY() + ":" + event.getBlock().getZ());
            new ConfigManager("noedit/build").set("build." + event.getPlayer().getName(), list);
            List listOnEnable = new ConfigManager("noedit/build").getConfig().getStringList("onEnable");
            listOnEnable.add(event.getPlayer().getName() + ":" + event.getBlock().getX() + ":" + event.getBlock().getY() + ":" + event.getBlock().getZ());
            new ConfigManager("noedit/build").set("onEnable", listOnEnable);
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getFrom().getWorld().getName().equals("Arene") && new ConfigManager("noedit/build").getConfig().getStringList("build." + event.getPlayer().getName()) != null) {
            for (String blocks : new ConfigManager("noedit/build").getConfig().getStringList("build." + event.getPlayer().getName())) {
                event.getFrom().getWorld().getBlockAt(Integer.valueOf(blocks.split(":")[0]).intValue(), Integer.valueOf(blocks.split(":")[1]).intValue(), Integer.valueOf(blocks.split(":")[2]).intValue()).setType(Material.AIR);
            }
            new ConfigManager("noedit/build").set("build." + event.getPlayer().getName(), null);
            List listOnEnable = new ConfigManager("noedit/build").getConfig().getStringList("onEnable");
            for (String blocks : new ConfigManager("noedit/build").getConfig().getStringList("onEnable")) {
                if (!blocks.split(":")[0].equals(event.getPlayer().getName())) continue;
                listOnEnable.remove(blocks.split(":")[0] + ":" + blocks.split(":")[1] + ":" + blocks.split(":")[2] + ":" + blocks.split(":")[3]);
            }
            new ConfigManager("noedit/build").set("onEnable", listOnEnable);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getMaterial() == Material.LAVA_BUCKET || event.getMaterial() == Material.WATER_BUCKET && event.getPlayer().getWorld().getName().equals("Arene")) {
            if (event.getClickedBlock().getType() == Material.COBBLESTONE || event.getClickedBlock().getType() == Material.WOOD || event.getClickedBlock().getType() == Material.GLASS || event.getClickedBlock().getType() == Material.OBSIDIAN) {
                event.setCancelled(true);
                return;
            }
            List list = new ConfigManager("noedit/build").getConfig().getStringList("build." + event.getPlayer().getName());
            int Y = event.getClickedBlock().getY() + 1;
            list.add("" + event.getClickedBlock().getX() + ":" + Y + ":" + event.getClickedBlock().getZ());
            new ConfigManager("noedit/build").set("build." + event.getPlayer().getName(), list);
            List listOnEnable = new ConfigManager("noedit/build").getConfig().getStringList("onEnable");
            listOnEnable.add(event.getPlayer().getName() + ":" + event.getClickedBlock().getX() + ":" + Y + ":" + event.getClickedBlock().getZ());
            new ConfigManager("noedit/build").set("onEnable", listOnEnable);
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        event.setCancelled(true);
    }
}

