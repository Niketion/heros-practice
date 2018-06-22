package net.herospvp.plugins.practiceheros.locations;

import net.herospvp.plugins.practiceheros.lang.Lang;
import net.herospvp.plugins.practiceheros.listeners.connection.utils.ConnectionUtils;
import net.herospvp.plugins.practiceheros.listeners.inventory.utils.InventoryUtils;
import net.herospvp.plugins.practiceheros.locations.LocationSerialize;
import net.herospvp.plugins.practiceheros.managers.ConfigManager;
import net.herospvp.plugins.practiceheros.managers.scoreboard.ScoreboardManager;
import net.herospvp.plugins.practiceheros.managers.scoreboard.ScoreboardPrefactory;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationPrefactory {
    private Player player;

    public LocationPrefactory(Player player) {
        this.player = player;
    }

    public void teleportEditSpawn() {
        this.player.teleport(new LocationSerialize().serialize(new ConfigManager("spawn"), "editspawn"));
        this.player.sendMessage(Lang.TELEPORT_SPAWN_EDIT.toString());
    }

    public void teleportLobby() {
        new InventoryUtils(this.player).clearInventory();
        new ScoreboardManager("join", "&6Practice", ScoreboardPrefactory.LOBBY_SCOREBOARD(this.player)).scoreboard(this.player);
        ConnectionUtils.joinItem(this.player);
        this.player.teleport(new LocationSerialize().serialize(new ConfigManager("spawn"), "spawn"));
    }

    public void teleportSpawn() {
        this.player.teleport(new LocationSerialize().serialize(new ConfigManager("spawn"), "spawn"));
    }
}

