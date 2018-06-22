package net.herospvp.plugins.practiceheros.match;

import org.bukkit.entity.Player;

public interface Match {
    public void createWaitingMatch(Player var1);

    public void deleteWaiting();

    public Player getPlayer();

    public boolean thereIsMatch();
}

