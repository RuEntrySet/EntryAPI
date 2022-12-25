package ru.entryset.api.tools;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Logger {

	private JavaPlugin plugin;

	public Logger(JavaPlugin plugin){
		setPlugin(plugin);
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}

	public void setPlugin(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public void info(String text) {
		Bukkit.getConsoleSender().sendMessage(Messager.color("&a(" + getPlugin().getName() + "/INFO) " + text));
	}

	public void warn(String text) {
		Bukkit.getConsoleSender().sendMessage(Messager.color("&6(" + getPlugin().getName() + "/WARN) " + text));
	}

	public void error(String text) {
		Bukkit.getConsoleSender().sendMessage(Messager.color("&c(" + getPlugin().getName() + "/ERROR) " + text));
	}


	public void enable(String text) {
		Bukkit.getConsoleSender().sendMessage(Messager.color("&a[&f" + getPlugin().getName() + "&a] " + text + "&r"));
	}

} 
