package net.herospvp.plugins.practiceheros.commands.spawns;

import net.herospvp.plugins.practiceheros.locations.LocationPrefactory;
import net.herospvp.plugins.practiceheros.locations.LocationSerialize;
import net.herospvp.plugins.practiceheros.managers.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand
implements CommandExecutor {
    private static final ConfigManager SPAWN_CONFIG = new ConfigManager("spawn");

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("setspawn")) {
            if (commandSender.isOp()) {
                new LocationSerialize((Player)commandSender).deserialize(SPAWN_CONFIG, "spawn");
                return true;
            }
            return false;
        }
        if (command.getName().equalsIgnoreCase("spawn")) {
            new LocationPrefactory((Player)commandSender).teleportLobby();
            return true;
        }
        return false;
    }
}

