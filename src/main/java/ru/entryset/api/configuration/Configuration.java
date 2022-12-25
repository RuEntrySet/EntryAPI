package ru.entryset.api.configuration;

import com.sun.istack.internal.NotNull;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class Configuration implements ConfigurationMap {

    private final JavaPlugin instance;

    private String fileName;

    private YamlConfiguration file;

    public Configuration(JavaPlugin plugin, String fileName){
        this.instance = plugin;
        setFileName(fileName);
        setFile(getFile(fileName));
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

    public static HashMap<String, YamlConfiguration> cache = new HashMap<>();

    public YamlConfiguration getFile(String fileName) {
        YamlConfiguration configuration = cache.get(fileName);
        if(configuration == null){
            File file = new File(getInstance().getDataFolder(), fileName);
            if (!file.exists()) {
                getInstance().saveResource(fileName, false);
            }
            configuration = YamlConfiguration.loadConfiguration(file);
            cache.put(fileName, configuration);
        }
        return configuration;
    }

    @Override
    @NotNull
    public String getString(String source) {
        return getConfiguration().getString(source);
    }

    @Override
    @NotNull
    public int getInt(String source) {
        return getConfiguration().getInt(source);
    }

    @Override
    @NotNull
    public double getDouble(String source) {
        return getConfiguration().getDouble(source);
    }

    @Override
    @NotNull
    public boolean getBoolean(String source) {
        return getConfiguration().getBoolean(source);
    }

    @Override
    @NotNull
    public List<String> getStringList(String source) {
        return getConfiguration().getStringList(source);
    }

    @Override
    @NotNull
    public List<Boolean> getBooleanList(String source) {
        return getConfiguration().getBooleanList(source);
    }

    @NotNull
    @Override
    public List<Integer> getIntegerList(String source) {
        return getConfiguration().getIntegerList(source);
    }

    @Override
    @NotNull
    public List<Long> getLongList(String source) {
        return getConfiguration().getLongList(source);
    }

    @Override
    @NotNull
    public ConfigurationSection getConfigurationSection(String source) {
        return getConfiguration().getConfigurationSection(source);
    }

    @Override
    @NotNull
    public ItemStack getItemStack(String source) {
        return getConfiguration().getItemStack(source);
    }

    @Override
    @NotNull
    public Location getLocation(String source) {
        return getConfiguration().getLocation(source);
    }

    @Override
    @NotNull
    public void set(String source, Object object) {
        getConfiguration().set(source, object);
        setFile(getFile(getFileName()));
    }
}
