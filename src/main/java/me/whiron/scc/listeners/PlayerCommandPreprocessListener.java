package me.whiron.scc.listeners;

import java.util.List;
import java.util.Objects;

import me.whiron.scc.Main;
import me.whiron.scc.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessListener implements Listener {
    private final Main main;

    public PlayerCommandPreprocessListener(Main main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void OPC(PlayerCommandPreprocessEvent event) {
        for (String key : Objects.requireNonNull(main.cm.getCommands().getConfigurationSection("commands")).getKeys(false)) {
            List<String> command = main.cm.getCommands().getStringList("commands." + key + ".commands");
            List<String> worlds = main.cm.getCommands().getStringList("commands." + key + ".worlds");

            if (messageMatchesCommand(event.getMessage().toLowerCase(), command)) {
                event.setCancelled(true);
                if (playerInWorlds(event.getPlayer(), worlds)) {
                    List<String> runCommands = main.cm.getCommands().getStringList("commands." + key + ".run_commands");
                    executeCommands(event.getPlayer(), runCommands);
                    return;
                }
            }
        }
    }

    private boolean messageMatchesCommand(String message, List<String> command) {
        String[] separatedMessages = message.split(" ");
        if (separatedMessages.length < command.size()) {
            return false;
        }

        for (int i = 0; i < command.size(); i++) {
            if (!command.get(i).equalsIgnoreCase(separatedMessages[i])) {
                return false;
            }
        }

        return true;
    }

    private boolean playerInWorlds(Player player, List<String> worlds) {
        if (worlds.isEmpty()) {
            return true;
        }

        for (String world : worlds) {
            if (player.getWorld().getName().equalsIgnoreCase(world)) {
                return true;
            }
        }

        return false;
    }

    private void executeCommands(Player player, List<String> commands) {
        ConsoleCommandSender consoleCommandSender = Bukkit.getServer().getConsoleSender();
        for (String s : commands) {
            String sendCmd = s.replaceAll("%player%", player.getName());
            if (sendCmd.startsWith("[console]")) {
                if (sendCmd.contains("msg " + player.getName())) {
                    String msg = sendCmd.replace("msg " + player.getName() + " ", "").replace("[console] ", "");
                    player.sendMessage(MessagesUtil.format(player, msg));
                } else {
                    Bukkit.dispatchCommand(consoleCommandSender, sendCmd.replace("[console] ", ""));
                }
            } else {
                player.chat("/" + sendCmd.replace("[player] ", ""));
            }
        }
    }

}
