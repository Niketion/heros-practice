package net.herospvp.plugins.practiceheros.commands.spawns;

import net.herospvp.plugins.practiceheros.locations.LocationSerialize;
import net.herospvp.plugins.practiceheros.managers.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EditSpawnCommand
implements CommandExecutor {
    private static final ConfigManager SPAWN_CONFIG = new ConfigManager("spawn");

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.isOp()) {
            switch (command.getName()) {
                case "editsetspawn": {
                    new LocationSerialize((Player)commandSender).deserialize(SPAWN_CONFIG, "editspawn");
                    return true;
                }
            }
        }
        return false;
    }
}

