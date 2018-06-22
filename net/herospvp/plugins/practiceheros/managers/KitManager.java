package net.herospvp.plugins.practiceheros.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.herospvp.plugins.practiceheros.managers.ConfigManager;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.MaterialData;

public class KitManager {
    private Player player;
    private ConfigManager config;
    private String item;

    public KitManager(String string) {
        this.config = new ConfigManager("kits/default/" + string);
        this.item = "item";
    }

    public KitManager(String namePlayer, String kitName) {
        this.config = new ConfigManager("kits/" + namePlayer);
        this.item = kitName;
        this.player = Bukkit.getPlayerExact((String)namePlayer);
    }

    public KitManager(String string, Player player) {
        this.config = new ConfigManager("kits/default/" + string);
        this.player = player;
        this.item = "item";
    }

    public void deserialize() {
        List list = this.config.getConfig().getStringList(this.item);
        List listEnchant = this.config.getConfig().getStringList("enchant");
        if (list == null) {
            this.config.getConfig().set(this.item, new ArrayList());
        }
        if (!list.isEmpty()) {
            list.clear();
        }
        if (listEnchant == null) {
            this.config.getConfig().set("enchant", new ArrayList());
        }
        if (!listEnchant.isEmpty()) {
            listEnchant.clear();
        }
        for (int i = 0; i <= 35; ++i) {
            try {
                ItemStack itemStack = this.player.getInventory().getItem(i);
                for (Enchantment enchantment : itemStack.getEnchantments().keySet()) {
                    int level = (Integer)itemStack.getEnchantments().get((Object)enchantment);
                    listEnchant.add("" + i + ":" + enchantment.getName() + ":" + level);
                }
                list.add("" + i + ":" + (Object)itemStack.getType() + ":" + itemStack.getAmount() + ":" + itemStack.getData().getData());
                continue;
            }
            catch (NullPointerException itemStack) {
                // empty catch block
            }
        }
        for (ItemStack itemStack : this.player.getInventory().getArmorContents()) {
            if (itemStack != null) {
                int level;
                list.add("ARMOR:" + (Object)itemStack.getType());
                String typeArmor = itemStack.getType().toString();
                Set keys = itemStack.getEnchantments().keySet();
                String helmet = "HELMET";
                String chestplate = "CHESTPLATE";
                String leggings = "LEGGINGS";
                String boots = "BOOTS";
                if (typeArmor.contains(helmet)) {
                    for (Object enchantment : keys) {
                        level = (Integer)itemStack.getEnchantments().get(enchantment);
                        listEnchant.add(helmet + ":" + ((Enchantment)enchantment).getName() + ":" + level + ":ARMOR");
                    }
                    continue;
                }
                if (typeArmor.contains(chestplate)) {
                    for (Object enchantment : keys) {
                        level = (Integer)itemStack.getEnchantments().get(enchantment);
                        listEnchant.add(chestplate + ":" + ((Enchantment)enchantment).getName() + ":" + level + ":ARMOR");
                    }
                    continue;
                }
                if (typeArmor.contains(leggings)) {
                    for (Object enchantment : keys) {
                        level = (Integer)itemStack.getEnchantments().get(enchantment);
                        listEnchant.add(leggings + ":" + ((Enchantment)enchantment).getName() + ":" + level + ":ARMOR");
                    }
                    continue;
                }
                if (!typeArmor.contains(boots)) continue;
                for (Object enchantment : keys) {
                    level = (Integer)itemStack.getEnchantments().get(enchantment);
                    listEnchant.add(boots + ":" + ((Enchantment)enchantment).getName() + ":" + level);
                }
                continue;
            }
            list.add("ARMOR:AIR");
        }
        this.config.set(this.item, list);
        this.config.set("enchant", listEnchant);
    }

    public void serialize(Player player) {
        PlayerInventory inventory = player.getInventory();
        inventory.clear();
        for (String string : this.config.getConfig().getStringList(this.item)) {
            String[] itemStack;
            ItemStack itemStackEnchanted;
            if (NumberUtils.isNumber((String)string.split(":")[0])) {
                itemStack = string.split(":");
                itemStackEnchanted = new ItemStack(Material.valueOf((String)itemStack[1]), Integer.valueOf(itemStack[2]).intValue(), (short)Integer.valueOf(itemStack[3]).intValue());
                for (String enchants : this.config.getConfig().getStringList("enchant")) {
                    int slotEnchant;
                    if (enchants == null || !NumberUtils.isNumber((String)enchants.split(":")[0]) || (slotEnchant = Integer.parseInt(enchants.split(":")[0])) != Integer.valueOf(itemStack[0])) continue;
                    itemStackEnchanted.addUnsafeEnchantment(Enchantment.getByName((String)enchants.split(":")[1]), Integer.valueOf(enchants.split(":")[2]).intValue());
                }
                inventory.setItem(Integer.valueOf(itemStack[0]).intValue(), itemStackEnchanted);
                continue;
            }
            itemStack = string.split(":");
            itemStackEnchanted = new ItemStack(Material.valueOf((String)itemStack[1]));
            if (string.contains("BOOTS")) {
                for (String enchants : this.config.getConfig().getStringList("enchant")) {
                    if (!enchants.split(":")[0].contains("BOOTS")) continue;
                    itemStackEnchanted.addUnsafeEnchantment(Enchantment.getByName((String)enchants.split(":")[1]), Integer.valueOf(enchants.split(":")[2]).intValue());
                }
                inventory.setBoots(itemStackEnchanted);
                continue;
            }
            if (string.contains("LEGGINGS")) {
                for (String enchants : this.config.getConfig().getStringList("enchant")) {
                    if (!enchants.split(":")[0].contains("LEGGINGS")) continue;
                    itemStackEnchanted.addUnsafeEnchantment(Enchantment.getByName((String)enchants.split(":")[1]), Integer.valueOf(enchants.split(":")[2]).intValue());
                }
                inventory.setLeggings(itemStackEnchanted);
                continue;
            }
            if (string.contains("CHESTPLATE")) {
                for (String enchants : this.config.getConfig().getStringList("enchant")) {
                    if (!enchants.split(":")[0].contains("CHESTPLATE")) continue;
                    itemStackEnchanted.addUnsafeEnchantment(Enchantment.getByName((String)enchants.split(":")[1]), Integer.valueOf(enchants.split(":")[2]).intValue());
                }
                inventory.setChestplate(itemStackEnchanted);
                continue;
            }
            if (!string.contains("HELMET")) continue;
            for (String enchants : this.config.getConfig().getStringList("enchant")) {
                if (!enchants.split(":")[0].contains("HELMET")) continue;
                itemStackEnchanted.addUnsafeEnchantment(Enchantment.getByName((String)enchants.split(":")[1]), Integer.valueOf(enchants.split(":")[2]).intValue());
            }
            inventory.setHelmet(itemStackEnchanted);
        }
        player.updateInventory();
    }
}

