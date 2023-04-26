package me.whiron.scc.configuration;

import me.whiron.scc.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ConfigManager {
    private final Main main;

    public ConfigManager(Main main) {
        this.main = main;
    }

    //commands.yml file
    public FileConfiguration getCommands() {
        if(main.commands == null) {
            reloadCommands();
        }
        return main.commands;
    }

    public void reloadCommands(){
        if(main.commands == null){
            main.commandsFile = new File(main.getDataFolder(),"commands.yml");
        }
        main.commands = YamlConfiguration.loadConfiguration(main.commandsFile);
        Reader defConfigStream;
        defConfigStream = new InputStreamReader(Objects.requireNonNull(main.getResource("me/whiron/scc/configuration/files/commands.yml")), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        main.commands.setDefaults(defConfig);
    }

    public void saveCommands(){
        try{
            main.commands.save(main.commandsFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void registerCommands(){
        main.commandsFile = new File(main.getDataFolder(),"commands.yml");
        if(!main.commandsFile.exists()){
            this.getCommands().options().copyDefaults(true);
            saveCommands();
        }
    }


}
