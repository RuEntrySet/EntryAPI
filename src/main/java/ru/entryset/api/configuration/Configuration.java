package ru.entryset.api.configuration;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Configuration implements ConfigurationMap {

    private final JavaPlugin instance;

    private final UUID uuid;

    private String fileName;

    private YamlConfiguration file;

    public Configuration(JavaPlugin plugin, String fileName){
        this.instance = plugin;
        setFileName(fileName);
        setFile(getFile(fileName));
        this.uuid = UUID.randomUUID();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFile(YamlConfiguration file) {
        if(file == null){
            return;
        }
        this.file = file;
    }

    public YamlConfiguration getConfiguration() {
        return file;
    }

    public JavaPlugin getInstance(){
        return this.instance;
    }

    public static HashMap<UUID, YamlConfiguration> cache = new HashMap<>();

    public YamlConfiguration getFile(String fileName) {
        YamlConfiguration configuration = cache.get(this.uuid);
        if(configuration == null){
            File file = new File(getInstance().getDataFolder(), fileName);
            if (!file.exists()) {
                getInstance().saveResource(fileName, false);
            }
            configuration = YamlConfiguration.loadConfiguration(file);
            cache.put(this.uuid, configuration);
        }
        return configuration;
    }

    @Override
    public String getString(String source) {
        return getConfiguration().getString(source);
    }

    @Override
    public int getInt(String source) {
        return getConfiguration().getInt(source);
    }

    @Override
    public double getDouble(String source) {
        return getConfiguration().getDouble(source);
    }

    @Override
    public boolean getBoolean(String source) {
        return getConfiguration().getBoolean(source);
    }

    @Override
    public List<String> getStringList(String source) {
        return getConfiguration().getStringList(source);
    }

    @Override
    public List<Boolean> getBooleanList(String source) {
        return getConfiguration().getBooleanList(source);
    }

    @Override
    public List<Integer> getIntegerList(String source) {
        return getConfiguration().getIntegerList(source);
    }

    @Override
    public List<Long> getLongList(String source) {
        return getConfiguration().getLongList(source);
    }

    @Override
    public ConfigurationSection getConfigurationSection(String source) {
        return getConfiguration().getConfigurationSection(source);
    }

    @Override
    public ItemStack getItemStack(String source) {
        return getConfiguration().getItemStack(source);
    }

    @Override
    public Location getLocation(String source) {
        return getConfiguration().getLocation(source);
    }

    @Override
    public void set(String source, Object object) {
        getConfiguration().set(source, object);
        setFile(getFile(getFileName()));
    }
}
