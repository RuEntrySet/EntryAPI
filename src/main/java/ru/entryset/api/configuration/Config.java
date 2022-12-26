package ru.entryset.api.configuration;

import org.bukkit.plugin.java.JavaPlugin;
import ru.entryset.api.database.mysql.MysqlDatabase;
import ru.entryset.api.sync.redis.Redis;

public class Config extends Configuration {

    public Config(JavaPlugin plugin, String fileName) {
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

    public Redis getRedis(String source) {
        return new Redis(
                getString(source + ".host"),
                getInt(source + ".port"),
                getString(source + ".password")
        );
    }

    public String getSettings(String source){
        return getString("settings." + source);
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
