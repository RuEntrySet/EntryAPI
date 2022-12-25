package ru.entryset.api.configuration;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ConfigurationMap {

    /**
     * Возвращает string из конфига
     *
     * @return string
     */
    String getString(String source);

    /**
     * Возвращает int из конфига
     *
     * @return int
     */
    int getInt(String source);

    /**
     * Возвращает double из конфига
     *
     * @return double
     */
    double getDouble(String source);

    /**
     * Возвращает boolean из конфига
     *
     * @return boolean
     */
    boolean getBoolean(String source);

    /**
     * Возвращает List<String> из конфига
     *
     * @return List<String>
     */
    List<String> getStringList(String source);
    /**
     * Возвращает List<String> из конфига
     *
     * @return List<Boolean>
     */
    List<Boolean> getBooleanList(String source);

    /**
     * Возвращает List<Integer> из конфига
     *
     * @return List<Integer>
     */
    List<Integer> getIntegerList(String source);


    /**
     * Возвращает List<Long> из конфига
     *
     * @return List<Long>
     */
    List<Long> getLongList(String source);


    /**
     * Возвращает ConfigurationSection из конфига
     *
     * @return ConfigurationSection
     */
    ConfigurationSection getConfigurationSection(String source);

    /**
     * Возвращает ItemStack из конфига
     *
     * @return ItemStack
     */
    ItemStack getItemStack(String source);

    /**
     * Возвращает Location из конфига
     *
     * @return Location
     */
    Location getLocation(String source);

    /**
     * Устанавливает в конфиг объект
     *
     * @return Location
     */
    void set(String source, Object object);

}
