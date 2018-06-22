package net.herospvp.plugins.practiceheros;

import net.herospvp.plugins.practiceheros.commands.KitCommand;
import net.herospvp.plugins.practiceheros.commands.PracticeCommand;
import net.herospvp.plugins.practiceheros.commands.spawns.EditSpawnCommand;
import net.herospvp.plugins.practiceheros.commands.spawns.SpawnCommand;
import net.herospvp.plugins.practiceheros.listeners.*;
import net.herospvp.plugins.practiceheros.listeners.connection.GeneralConnectionEvent;
import net.herospvp.plugins.practiceheros.listeners.connection.JoinEvent;
import net.herospvp.plugins.practiceheros.listeners.inventory.ChestOpenCloseEvent;
import net.herospvp.plugins.practiceheros.listeners.inventory.GeneralPlayerInventoryEvent;
import net.herospvp.plugins.practiceheros.listeners.inventory.InventoryPlayerClickEvent;
import net.herospvp.plugins.practiceheros.listeners.inventory.PlayerInteractInventoryEvent;
import net.herospvp.plugins.practiceheros.listeners.inventory.utils.Editor;
import net.herospvp.plugins.practiceheros.managers.ConfigManager;
import net.herospvp.plugins.practiceheros.match.listeners.UnrankedListeners;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class PracticeHeros
extends JavaPlugin {
    public Chat chat = null;
    private static PracticeHeros instance;

    public static PracticeHeros getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        this.getServer().getConsoleSender().sendMessage((Object)ChatColor.YELLOW + "[PRACTICE] Apro le arene");
        for (int i = 1; i < new ConfigManager("arena").getConfig().getConfigurationSection("arene").getKeys(false).size(); ++i) {
            if (new ConfigManager("arena").getConfig().getBoolean("arene." + i + ".open")) continue;
            new ConfigManager("arena").set("arene." + i + ".open", true);
        }
        this.getServer().getConsoleSender().sendMessage((Object)ChatColor.YELLOW + "[PRACTICE] Controllo i blocchi");
        new ConfigManager("noedit/build").set("build", null);
        for (String blocks : new ConfigManager("noedit/build").getConfig().getStringList("onEnable")) {
            Bukkit.getWorld((String)"Arene").getBlockAt(Integer.valueOf(blocks.split(":")[1]).intValue(),
                    Integer.valueOf(blocks.split(":")[2]).intValue(), Integer.valueOf(blocks.split(":")[3]).intValue()).setType(Material.AIR);
        }
        new ConfigManager("noedit/build").set("onEnable", null);
        PluginManager pluginManager = this.getServer().getPluginManager();
        this.getServer().getConsoleSender().sendMessage((Object)ChatColor.YELLOW + "[PRACTICE] Registro gli eventi");
        pluginManager.registerEvents((Listener)new GeneralConnectionEvent(), (Plugin)this);
        pluginManager.registerEvents((Listener)new JoinEvent(), (Plugin)this);
        pluginManager.registerEvents((Listener)new DoorOpenEvent(), (Plugin)this);
        pluginManager.registerEvents((Listener)new GeneralPlayerInventoryEvent(), (Plugin)this);
        pluginManager.registerEvents((Listener)new InventoryPlayerClickEvent(), (Plugin)this);
        pluginManager.registerEvents((Listener)new PlayerInteractInventoryEvent(), (Plugin)this);
        pluginManager.registerEvents((Listener)new ChestOpenCloseEvent(), (Plugin)this);
        pluginManager.registerEvents((Listener)new UnrankedListeners(), (Plugin)this);
        pluginManager.registerEvents((Listener)new PlayerInMatchDeathEvent(), (Plugin)this);
        pluginManager.registerEvents((Listener)new PlayerFreezedEvent(), (Plugin)this);
        pluginManager.registerEvents((Listener)new PlayerChatEvent(), (Plugin)this);
        pluginManager.registerEvents((Listener)new PlayerBreakPlaceEvent(), (Plugin)this);
        pluginManager.registerEvents((Listener)new PlayerDamageEvent(), (Plugin)this);
        pluginManager.registerEvents((Listener)new PlayerTeleportLobbyEvent(), (Plugin)this);
        pluginManager.registerEvents((Listener)new PlayerOutMapEvent(), (Plugin)this);
        this.getServer().getConsoleSender().sendMessage((Object)ChatColor.YELLOW + "[PRACTICE] Registro i comandi");
        this.getCommand("practice").setExecutor((CommandExecutor)new PracticeCommand());
        this.getCommand("spawn").setExecutor((CommandExecutor)new SpawnCommand());
        this.getCommand("setspawn").setExecutor((CommandExecutor)new SpawnCommand());
        this.getCommand("createkit").setExecutor((CommandExecutor)new KitCommand());
        this.getCommand("editsetspawn").setExecutor((CommandExecutor)new EditSpawnCommand());
        this.getServer().getConsoleSender().sendMessage((Object)ChatColor.YELLOW + "[PRACTICE] Registro il config");
        this.saveDefaultConfig();
        this.getServer().getConsoleSender().sendMessage((Object)ChatColor.YELLOW + "[PRACTICE] Imposto la chat");
        this.setupChat();
        this.getServer().getConsoleSender().sendMessage((Object)ChatColor.YELLOW + "[PRACTICE] Done.");
    }

    public void onDisable() {
        for (Player players : this.getServer().getOnlinePlayers()) {
            if (!Editor.isEditorZone(players)) continue;
            Editor.forceRemove(players);
        }
    }

    private void setupChat() {
        RegisteredServiceProvider chatProvider = this.getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider != null) {
            this.chat = (Chat)chatProvider.getProvider();
        }
    }
}

