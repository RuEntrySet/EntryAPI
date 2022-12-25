package ru.entryset.api.configuration;

import org.bukkit.plugin.java.JavaPlugin;
import ru.entryset.api.database.mysql.MysqlDatabase;

public class BukkitConfig extends ConfigurationFile {

    public BukkitConfig(JavaPlugin plugin, String fileName) {
        super(plugin, fileName);
    }

    public MysqlDatabase getMysqlDatabase(String source) {
        return new MysqlDatabase(
                getString(source + ".host"),
                getInt(source + ".port"),
                getString(source + ".user"),
                getString(source + ".password"),
                getString(source + ".database")
        );
    }

    public String getMessage(String source){
        return getString("messages." + source);
    }

    public String getPrefix(){
        return getString("messages.prefix");
    }

    public String getPermission(String source){
        return getString("permissions." + source);
    }


}
