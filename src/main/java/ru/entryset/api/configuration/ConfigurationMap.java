package ru.entryset.api.configuration;

import com.sun.istack.internal.NotNull;
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
    @NotNull
    String getString(String source);

    /**
     * Возвращает int из конфига
     *
     * @return int
     */
    @NotNull
    int getInt(String source);

    /**
     * Возвращает double из конфига
     *
     * @return double
     */
    @NotNull
    double getDouble(String source);

    /**
     * Возвращает boolean из конфига
     *
     * @return boolean
     */
    @NotNull
    boolean getBoolean(String source);

    /**
     * Возвращает List<String> из конфига
     *
     * @return List<String>
     */
    @NotNull
    List<String> getStringList(String source);
    /**
     * Возвращает List<String> из конфига
     *
     * @return List<Boolean>
     */
    @NotNull
    List<Boolean> getBooleanList(String source);

    /**
     * Возвращает List<Integer> из конфига
     *
     * @return List<Integer>
     */
    @NotNull
    List<Integer> getIntegerList(String source);


    /**
     * Возвращает List<Long> из конфига
     *
     * @return List<Long>
     */
    @NotNull
    List<Long> getLongList(String source);


    /**
     * Возвращает ConfigurationSection из конфига
     *
     * @return ConfigurationSection
     */
    @NotNull
    ConfigurationSection getConfigurationSection(String source);

    /**
     * Возвращает ItemStack из конфига
     *
     * @return ItemStack
     */
    @NotNull
    ItemStack getItemStack(String source);

    /**
     * Возвращает Location из конфига
     *
     * @return Location
     */
    @NotNull
    Location getLocation(String source);

    /**
     * Устанавливает в конфиг объект
     *
     * @return Location
     */
    @NotNull
    void set(String source, Object object);

}
