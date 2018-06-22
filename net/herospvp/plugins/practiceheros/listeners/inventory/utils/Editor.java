package net.herospvp.plugins.practiceheros.listeners.inventory.utils;

import java.util.HashMap;
import net.herospvp.plugins.practiceheros.lang.Lang;
import net.herospvp.plugins.practiceheros.listeners.inventory.utils.InventoryUtils;
import net.herospvp.plugins.practiceheros.locations.LocationPrefactory;
import org.bukkit.entity.Player;

public class Editor {
    private static HashMap<String, String> editor = new HashMap();

    public static void addEditor(Player player, String nameKit) {
        editor.put(player.getName(), nameKit);
    }

    public static void removeEditor(Player player) {
        editor.remove(player.getName());
    }

    public static void forceRemove(Player player) {
        if (Editor.isEditorZone(player)) {
            Editor.removeEditor(player);
        }
        player.sendMessage(Lang.EXIT_FROM_EDITOR.toString());
        new InventoryUtils(player).clearInventory();
        new LocationPrefactory(player).teleportLobby();
    }

    public static boolean isEditorZone(Player player) {
        return editor.get(player.getName()) != null;
    }

    public static String getValue(Player player) {
        if (Editor.isEditorZone(player)) {
            return editor.get(player.getName());
        }
        return null;
    }

    @Deprecated
    public HashMap<String, String> getEditor() {
        return editor;
    }
}

