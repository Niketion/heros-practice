package net.herospvp.plugins.practiceheros.commands;

import java.util.Set;
import net.herospvp.plugins.practiceheros.lang.Lang;
import net.herospvp.plugins.practiceheros.locations.LocationSerialize;
import net.herospvp.plugins.practiceheros.managers.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PracticeCommand
implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.isOp()) {
            commandSender.sendMessage((Object)ChatColor.GRAY + "PracticeHeros by Niketion");
            return false;
        }
        if (strings.length <= 0) {
            commandSender.sendMessage(Lang.USAGE.toString().split("\n"));
            return false;
        }
        switch (strings[0]) {
            case "create": {
                if (strings.length > 1) {
                    if (new ConfigManager("arena").getConfig().isConfigurationSection("arene." + strings[1].toUpperCase())) {
                        commandSender.sendMessage(Lang.ARENA_ALREADY_SET.toString());
                        return false;
                    }
                    if (strings.length > 2 && strings[2].equalsIgnoreCase("Build")) {
                        new ConfigManager("arena").set("arene." + strings[1].toUpperCase() + ".creator", commandSender.getName());
                        new ConfigManager("arena").set("arene." + strings[1].toUpperCase() + ".type", "build");
                        commandSender.sendMessage(Lang.ARENA_CREATED.toString().replaceAll("%nome%", strings[1].toUpperCase()));
                        return true;
                    }
                    new ConfigManager("arena").set("arene." + strings[1].toUpperCase() + ".creator", commandSender.getName());
                    commandSender.sendMessage(Lang.ARENA_CREATED.toString().replaceAll("%nome%", strings[1].toUpperCase()));
                    return true;
                }
                commandSender.sendMessage(Lang.USAGE.toString().split("\n"));
                return false;
            }
            case "set": {
                if (strings.length > 2) {
                    if (strings[1].equals("1") || strings[1].equals("2")) {
                        if (new ConfigManager("arena").getConfig().isConfigurationSection("arene." + strings[2].toUpperCase())) {
                            new LocationSerialize((Player)commandSender).deserialize(new ConfigManager("arena"), "arene." + strings[2].toUpperCase() + "." + strings[1]);
                            new ConfigManager("arena").set("arene." + strings[1].toUpperCase() + ".open", true);
                            return true;
                        }
                        return false;
                    }
                    return false;
                }
            }
            case "warp": {
                if (strings.length > 1) {
                    ((Player)commandSender).teleport(new LocationSerialize((Player)commandSender).serialize(new ConfigManager("arena"), "arene." + strings[1] + ".1"));
                } else {
                    commandSender.sendMessage("Arene: " + (Object)ChatColor.GREEN + new ConfigManager("arena").getConfig().getConfigurationSection("arene").getKeys(false));
                }
                return false;
            }
        }
        return true;
    }
}

