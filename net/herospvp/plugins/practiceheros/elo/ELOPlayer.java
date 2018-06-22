package net.herospvp.plugins.practiceheros.elo;

import net.herospvp.plugins.practiceheros.managers.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ELOPlayer {
    private Player player;
    private String type;

    public ELOPlayer(Player player, String type) {
        this.player = player;
        this.type = type;
    }

    public int getElo() {
        if (new ConfigManager("elo/" + this.player.getName()).getConfig().getInt(this.type) != 0) {
            return new ConfigManager("elo/" + this.player.getName()).getConfig().getInt(this.type);
        }
        this.setElo(1500);
        return 1500;
    }

    public void setElo(int number) {
        new ConfigManager("elo/" + this.player.getName()).set(this.type, number);
    }

    public void addElo(int number) {
        new ConfigManager("elo/" + this.player.getName()).set(this.type, this.getElo() + number);
    }

    public void removeElo(int number) {
        new ConfigManager("elo/" + this.player.getName()).set(this.type, this.getElo() - number);
    }

    public boolean hasElo(int number) {
        return this.getElo() >= number;
    }
}

