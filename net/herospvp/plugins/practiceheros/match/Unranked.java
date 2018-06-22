package net.herospvp.plugins.practiceheros.match;

import java.util.HashMap;
import net.herospvp.plugins.practiceheros.match.Match;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Unranked
implements Match {
    public static String KIT_WAITING;
    public static String TIME_WAITING;
    private final String nameMatch;
    private static final HashMap<String, String> match;

    public Unranked(String nameMatch) {
        this.nameMatch = nameMatch;
    }

    @Override
    public void createWaitingMatch(Player player) {
        match.put(this.nameMatch, player.getName());
    }

    @Override
    public void deleteWaiting() {
        match.remove(this.nameMatch);
    }

    @Override
    public Player getPlayer() {
        return Bukkit.getPlayerExact((String)match.get(this.nameMatch));
    }

    @Override
    public boolean thereIsMatch() {
        try {
            return !match.get(this.nameMatch).isEmpty();
        }
        catch (NullPointerException excpetion) {
            return false;
        }
    }

    static {
        match = new HashMap();
    }
}

