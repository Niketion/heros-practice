package net.herospvp.plugins.practiceheros.listeners.connection.utils;

import net.herospvp.plugins.practiceheros.lang.Lang;
import net.herospvp.plugins.practiceheros.listeners.inventory.utils.InventoryUtils;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ConnectionUtils {
    public static void hidePlayer(Player hiding, Player from) {
        from.hidePlayer(hiding);
        EntityPlayer nmsFrom = ((CraftPlayer)from).getHandle();
        EntityPlayer nmsHiding = ((CraftPlayer)hiding).getHandle();
        nmsFrom.playerConnection.sendPacket((Packet)PacketPlayOutPlayerInfo.addPlayer((EntityPlayer)nmsHiding));
    }

    public static void joinItem(Player player) {
        player.getInventory().setItem(0, InventoryUtils.itemStackBuilder(Material.DIAMOND_SWORD, "&aRanked " + Lang.RIGHT_CLICK.toString()));
        player.getInventory().setItem(1, InventoryUtils.itemStackBuilder(Material.GOLD_SWORD, "&eUnranked " + Lang.RIGHT_CLICK.toString()));
        player.getInventory().setItem(3, InventoryUtils.itemStackBuilder(Material.BOOK, Lang.EDIT_KIT.toString()));
        player.getInventory().setItem(8, InventoryUtils.itemStackBuilder(Material.REDSTONE_TORCH_ON, Lang.SETTINGS_ITEM.toString()));
        player.updateInventory();
    }
}

