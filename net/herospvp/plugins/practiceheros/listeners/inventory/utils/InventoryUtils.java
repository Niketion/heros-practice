package net.herospvp.plugins.practiceheros.listeners.inventory.utils;

import java.io.File;
import java.util.List;
import net.herospvp.plugins.practiceheros.managers.ConfigManager;
import net.herospvp.plugins.practiceheros.managers.MenuManager;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryUtils {
    private Player player;

    public InventoryUtils(Player player) {
        this.player = player;
    }

    public void menuEditor() {
        this.clearInventory();
        MenuManager menu = new MenuManager(5, "Kits");
        File file = null;
        File folder = new File("plugins/PracticeHeros/kits/default/");
        File[] listOfFiles = folder.listFiles();
        for (int x = 0; x < listOfFiles.length; ++x) {
            if (x != this.player.getLevel()) continue;
            file = listOfFiles[x];
            break;
        }
        ConfigManager config = new ConfigManager("kits/default/" + file.getName().replaceAll(".yml", ""));
        int f = 44;
        for (String string : config.getConfig().getStringList("item")) {
            ItemStack itemStackEnchanted;
            String[] itemStack;
            if (NumberUtils.isNumber((String)string.split(":")[0])) {
                itemStack = string.split(":");
                itemStackEnchanted = new ItemStack(Material.valueOf((String)itemStack[1]), Integer.parseInt(itemStack[2]), (short)Integer.parseInt(itemStack[3]));
                for (String enchants : config.getConfig().getStringList("enchant")) {
                    int slotEnchant;
                    if (enchants == null || !NumberUtils.isNumber((String)enchants.split(":")[0]) || (slotEnchant = Integer.parseInt(enchants.split(":")[0])) != Integer.valueOf(itemStack[0])) continue;
                    itemStackEnchanted.addUnsafeEnchantment(Enchantment.getByName((String)enchants.split(":")[1]), Integer.valueOf(enchants.split(":")[2]).intValue());
                }
                menu.setItem(Integer.parseInt(itemStack[0]), itemStackEnchanted);
                continue;
            }
            itemStack = string.split(":");
            itemStackEnchanted = new ItemStack(Material.valueOf((String)itemStack[1]));
            if (string.contains("BOOTS")) {
                for (String enchants : config.getConfig().getStringList("enchant")) {
                    if (!enchants.split(":")[0].contains("BOOTS")) continue;
                    itemStackEnchanted.addUnsafeEnchantment(Enchantment.getByName((String)enchants.split(":")[1]), Integer.valueOf(enchants.split(":")[2]).intValue());
                }
                menu.setItem(f, 1, itemStackEnchanted);
                --f;
                continue;
            }
            if (string.contains("LEGGINGS")) {
                for (String enchants : config.getConfig().getStringList("enchant")) {
                    if (!enchants.split(":")[0].contains("LEGGINGS")) continue;
                    itemStackEnchanted.addUnsafeEnchantment(Enchantment.getByName((String)enchants.split(":")[1]), Integer.valueOf(enchants.split(":")[2]).intValue());
                }
                menu.setItem(f, 1, itemStackEnchanted);
                --f;
                continue;
            }
            if (string.contains("CHESTPLATE")) {
                for (String enchants : config.getConfig().getStringList("enchant")) {
                    if (!enchants.split(":")[0].contains("CHESTPLATE")) continue;
                    itemStackEnchanted.addUnsafeEnchantment(Enchantment.getByName((String)enchants.split(":")[1]), Integer.valueOf(enchants.split(":")[2]).intValue());
                }
                menu.setItem(f, 1, itemStackEnchanted);
                --f;
                continue;
            }
            if (!string.contains("HELMET")) continue;
            for (String enchants : config.getConfig().getStringList("enchant")) {
                if (!enchants.split(":")[0].contains("HELMET")) continue;
                itemStackEnchanted.addUnsafeEnchantment(Enchantment.getByName((String)enchants.split(":")[1]), Integer.valueOf(enchants.split(":")[2]).intValue());
            }
            menu.setItem(f, 1, itemStackEnchanted);
            --f;
        }
        menu.openMenu(this.player);
    }

    public void clearInventory() {
        PlayerInventory inventory = this.player.getInventory();
        inventory.clear();
        inventory.setBoots(new ItemStack(Material.AIR));
        inventory.setChestplate(new ItemStack(Material.AIR));
        inventory.setLeggings(new ItemStack(Material.AIR));
        inventory.setHelmet(new ItemStack(Material.AIR));
    }

    public static ItemStack itemStackBuilder(Material material, String displayName) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)displayName));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}

