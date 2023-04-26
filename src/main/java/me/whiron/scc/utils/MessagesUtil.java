package me.whiron.scc.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.whiron.scc.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessagesUtil {
    public static String format(Player player, String message) {
        if(player != null) {
            if(Main.placeholderapi) {
                return PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', message));
            }
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
