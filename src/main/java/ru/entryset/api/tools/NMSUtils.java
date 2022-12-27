package ru.entryset.api.tools;

import org.bukkit.Bukkit;

public class NMSUtils {

    public static Class<?> getNMS(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<?> getOBC(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
