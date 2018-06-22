package net.herospvp.plugins.practiceheros.locations;

import net.herospvp.plugins.practiceheros.lang.Lang;
import net.herospvp.plugins.practiceheros.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class LocationSerialize {
    private Location location;
    private Player player;

    public LocationSerialize(Player player) {
        this.location = player.getLocation();
        this.player = player;
    }

    public LocationSerialize(Location location) {
        this.location = location;
    }

    public LocationSerialize() {
    }

    public void deserialize(ConfigManager configManager, String locationName) {
        if (this.player != null) {
            this.player.sendMessage(Lang.ZONE_SET.toString().replaceAll("%numero%", locationName.toUpperCase()));
        }
        configManager.set(locationName, this.location.getWorld().getName() + ":" + this.location.getX() + ":" + this.location.getY() + ":" + this.location.getZ() + ":" + this.location.getYaw() + ":" + this.location.getPitch());
        configManager.save();
    }

    public Location serialize(ConfigManager configManager, String locationName) {
        String[] string = configManager.getConfig().getString(locationName).split(":");
        return new Location(Bukkit.getWorld((String)string[0]), Double.valueOf(string[1]).doubleValue(), Double.valueOf(string[2]).doubleValue(), Double.valueOf(string[3]).doubleValue(), Float.valueOf(string[4]).floatValue(), Float.valueOf(string[5]).floatValue());
    }
}

