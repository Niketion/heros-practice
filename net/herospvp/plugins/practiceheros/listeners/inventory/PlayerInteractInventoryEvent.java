package net.herospvp.plugins.practiceheros.listeners.inventory;

import java.io.File;
import java.util.HashMap;
import net.herospvp.plugins.practiceheros.lang.Lang;
import net.herospvp.plugins.practiceheros.listeners.inventory.utils.Editor;
import net.herospvp.plugins.practiceheros.locations.LocationPrefactory;
import net.herospvp.plugins.practiceheros.managers.MenuManager;
import net.herospvp.plugins.practiceheros.match.MatchUtils;
import net.herospvp.plugins.practiceheros.match.Unranked;
import net.herospvp.plugins.practiceheros.match.kits.KitsRanked;
import net.herospvp.plugins.practiceheros.match.kits.KitsUnranked;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

public class PlayerInteractInventoryEvent
implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        try {
            Material type = event.getItem().getType();
            Player player = event.getPlayer();
            if (event.getPlayer().getWorld().getName().equals("Lobby")) {
                if (Editor.isEditorZone(player)) {
                    return;
                }
                if (type == Material.DIAMOND_SWORD) {
                    MenuManager menu = new MenuManager(1, "Ranked");
                    menu.setItem(0, "&eNoDebuff", KitsRanked.NODEBUFF.getNumber(), Material.POTION, 16389, "&7In game:&a " + KitsRanked.NODEBUFF.getNumber());
                    menu.setItem(1, "&dBuildUHC", KitsRanked.BUILDUHC.getNumber(), Material.LAVA_BUCKET, 0, "&7In game:&a " + KitsRanked.BUILDUHC.getNumber());
                    menu.setItem(2, "&6Combo", KitsRanked.COMBO.getNumber(), Material.RAW_FISH, 3, "&7In game:&a " + KitsRanked.COMBO.getNumber());
                    menu.setItem(3, "&bOP", KitsRanked.OP.getNumber(), Material.GOLDEN_APPLE, 1, "&7In game:&a " + KitsRanked.OP.getNumber());
                    menu.openMenu(player);
                } else if (type == Material.BOOK) {
                    MenuManager menu = new MenuManager(1, "Modifica kit");
                    if (new File("plugins/PracticeHeros/kits/default/").listFiles() != null) {
                        int i = 0;
                        for (File file : new File("plugins/PracticeHeros/kits/default/").listFiles()) {
                            menu.setItem(i, "&6" + file.getName().replaceAll(".yml", ""), 1, Material.PAPER, 0, "&7Modifica il kit &e" + file.getName().replaceAll(".yml", ""));
                            ++i;
                        }
                    }
                    menu.openMenu(player);
                } else if (type == Material.GOLD_SWORD) {
                    MenuManager menu = new MenuManager(1, "Unranked");
                    menu.setItem(0, "&eNoDebuff", KitsUnranked.NODEBUFF.getNumber(), Material.POTION, 16389, "&7In game:&a " + KitsUnranked.NODEBUFF.getNumber());
                    menu.setItem(1, "&dBuildUHC", KitsUnranked.BUILDUHC.getNumber(), Material.LAVA_BUCKET, 0, "&7In game:&a " + KitsUnranked.BUILDUHC.getNumber());
                    menu.setItem(2, "&6Combo", KitsUnranked.COMBO.getNumber(), Material.RAW_FISH, 3, "&7In game:&a " + KitsUnranked.COMBO.getNumber());
                    menu.setItem(3, "&bOP", KitsUnranked.OP.getNumber(), Material.GOLDEN_APPLE, 1, "&7In game:&a " + KitsUnranked.OP.getNumber());
                    menu.setItem(4, "&aMCSG", KitsUnranked.MCSG.getNumber(), Material.FISHING_ROD, 0, "&7In game:&a " + KitsUnranked.MCSG.getNumber());
                    menu.setItem(5, "&9Archer", KitsUnranked.ARCHER.getNumber(), Material.BOW, 0, "&7In game:&a " + KitsUnranked.ARCHER.getNumber());
                    menu.openMenu(player);
                } else if (type == Material.REDSTONE) {
                    new LocationPrefactory(player).teleportLobby();
                    if (new Unranked(ChatColor.stripColor((String)event.getItem().getItemMeta().getDisplayName().split(": ")[1])).thereIsMatch()) {
                        new Unranked(ChatColor.stripColor((String)event.getItem().getItemMeta().getDisplayName().split(": ")[1])).deleteWaiting();
                    }
                    MatchUtils.SCOREBOARD_WAITING.get(player.getName()).cancel();
                    MatchUtils.SCOREBOARD_WAITING.remove(player.getName());
                    player.sendMessage(Lang.EXIT_FROM_WAITING.toString());
                } else if (type == Material.REDSTONE_TORCH_ON) {
                    MenuManager menu = new MenuManager(3, "Impostazioni");
                    menu.setItem(13, "&cScoreboard", 1, Material.BEACON, 0, "&7Cliccami per abilitare/disablitare", "&7la scoreboard");
                    menu.openMenu(player);
                }
            }
        }
        catch (NullPointerException type) {
            // empty catch block
        }
    }
}

