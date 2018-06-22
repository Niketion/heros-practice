package net.herospvp.plugins.practiceheros.listeners;

import net.herospvp.plugins.practiceheros.PracticeHeros;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEvent
implements Listener {
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (!PracticeHeros.getInstance().chat.getPlayerPrefix(event.getPlayer()).equals("")) {
            event.setFormat(ChatColor.translateAlternateColorCodes((char)'&', (String)PracticeHeros.getInstance().chat.getPlayerPrefix(event.getPlayer())) + " " + (Object)ChatColor.YELLOW + event.getPlayer().getName() + (Object)ChatColor.WHITE + ": " + event.getMessage());
        } else if (!PracticeHeros.getInstance().chat.getGroupPrefix(event.getPlayer().getWorld(), PracticeHeros.getInstance().chat.getPrimaryGroup(event.getPlayer())).equals("")) {
            event.setFormat(ChatColor.translateAlternateColorCodes((char)'&', (String)PracticeHeros.getInstance().chat.getGroupPrefix(event.getPlayer().getWorld(), PracticeHeros.getInstance().chat.getPrimaryGroup(event.getPlayer()))) + " " + (Object)ChatColor.YELLOW + event.getPlayer().getName() + (Object)ChatColor.WHITE + ": " + event.getMessage());
        } else {
            event.setFormat((Object)ChatColor.YELLOW + event.getPlayer().getName() + (Object)ChatColor.WHITE + ": " + event.getMessage());
        }
    }
}

