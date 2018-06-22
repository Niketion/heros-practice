package net.herospvp.plugins.practiceheros.listeners.inventory;

import net.herospvp.plugins.practiceheros.PracticeHeros;
import net.herospvp.plugins.practiceheros.lang.Lang;
import net.herospvp.plugins.practiceheros.listeners.inventory.utils.Editor;
import net.herospvp.plugins.practiceheros.listeners.inventory.utils.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class ChestOpenCloseEvent
implements Listener {
    @EventHandler(priority=EventPriority.HIGHEST)
    public void onChestOpen(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block chest = event.getClickedBlock();
            Player player = event.getPlayer();
            if (chest.getType() == Material.CHEST) {
                event.setCancelled(true);
                new InventoryUtils(player).menuEditor();
            }
        }
    }

    @EventHandler
    public void onChestClose(InventoryCloseEvent event) {
        try {
            if (Editor.isEditorZone((Player)event.getPlayer())) {
                Inventory chest = event.getInventory();
                final Player player = (Player)event.getPlayer();
                if (chest.getType() == InventoryType.CHEST) {
                    int chestContent = 0;
                    for (int i = 0; i <= 44; ++i) {
                        if (chest.getItem(i) == null) continue;
                        ++chestContent;
                    }
                    if (chestContent != 0) {
                        PracticeHeros.getInstance().getServer().getScheduler().scheduleAsyncDelayedTask((Plugin)PracticeHeros.getInstance(), new Runnable(){

                            @Override
                            public void run() {
                                new InventoryUtils(player).menuEditor();
                            }
                        }, 3);
                        player.sendMessage(Lang.AMOUNT_ITEM_EDITOR.toString());
                    }
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException chest) {
            // empty catch block
        }
    }

}

