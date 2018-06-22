package net.herospvp.plugins.practiceheros.managers;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuManager {
    private Inventory inventory;
    private static ArrayList<String> menuName = new ArrayList();

    public MenuManager(int rows, String title) {
        if (!menuName.contains(title)) {
            menuName.add(title);
        }
        this.inventory = Bukkit.createInventory((InventoryHolder)null, (int)(rows * 9), (String)title);
    }

    public /* varargs */ void setItem(int slot, String displayName, int amount, Material material, int data, String ... lore) {
        ItemStack itemStack = new ItemStack(material, amount, (short)data);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)displayName));
        ArrayList<String> list = new ArrayList<String>();
        for (String string : lore) {
            list.add(ChatColor.translateAlternateColorCodes((char)'&', (String)string));
        }
        itemMeta.setLore(list);
        itemStack.setItemMeta(itemMeta);
        this.inventory.setItem(slot, itemStack);
    }

    public void setItem(int slot, ItemStack itemStack) {
        this.inventory.setItem(slot, itemStack);
    }

    public void setItem(int slot, int amount, Material material, int data) {
        ItemStack itemStack = new ItemStack(material, amount, (short)data);
        this.inventory.setItem(slot, itemStack);
    }

    public void setItem(int slot, int amount, ItemStack itemStack) {
        ItemStack itemStackA = new ItemStack(itemStack.getType(), amount);
        this.inventory.setItem(slot, itemStackA);
    }

    public void setItem(int slot, int amount, Material material) {
        ItemStack itemStack = new ItemStack(material, amount);
        this.inventory.setItem(slot, itemStack);
    }

    public void openMenu(Player player) {
        player.openInventory(this.inventory);
    }

    public Inventory getMenu() {
        return this.inventory;
    }

    public static boolean isMenu(Inventory inventory) {
        return menuName.contains(inventory.getTitle());
    }
}

