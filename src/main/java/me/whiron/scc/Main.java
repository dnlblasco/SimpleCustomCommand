package me.whiron.scc;

import me.whiron.scc.configuration.ConfigManager;
import me.whiron.scc.listeners.PlayerCommandPreprocessListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {
    public static boolean placeholderapi = false;

    public FileConfiguration commands = null;
    public File commandsFile = null;

    public ConfigManager cm;

    public void onEnable() {
        placeholderapi = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;

        cm = new ConfigManager(this);

        cm.registerCommands();

        registerListeners();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[SCC] Plugin enabled correctly.");
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(this), this);
    }
}
