package net.herospvp.plugins.practiceheros.managers;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
    private File file;
    private FileConfiguration config;

    public ConfigManager(String nameConfig) {
        this.file = new File("plugins/PracticeHeros/", nameConfig + ".yml");
        this.config = YamlConfiguration.loadConfiguration((File)this.file);
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void set(String path, Object value) {
        this.config.set(path, value);
        this.save();
    }

    public File getFile() {
        return this.file;
    }

    public void save() {
        try {
            this.config.save(this.file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

