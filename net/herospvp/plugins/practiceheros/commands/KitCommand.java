package net.herospvp.plugins.practiceheros.commands;

import net.herospvp.plugins.practiceheros.lang.Lang;
import net.herospvp.plugins.practiceheros.managers.KitManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand
implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.isOp()) {
            return false;
        }
        if (strings.length <= 0) {
            commandSender.sendMessage(Lang.USAGE_KIT_MANAGER.toString());
            return false;
        }
        Player player = (Player)commandSender;
        new KitManager(strings[0].toUpperCase(), (Player)commandSender).deserialize();
        player.sendMessage(Lang.KIT_CREATED.toString().replaceAll("%nome%", strings[0].toUpperCase()));
        return true;
    }
}

