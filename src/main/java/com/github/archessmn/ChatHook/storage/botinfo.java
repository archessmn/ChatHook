package com.github.archessmn.ChatHook.storage;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class botinfo {

    private static File botInfoFile;
    private static FileConfiguration botInfoYml;

    public static void setup() {
        botInfoFile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("ChatHook")).getDataFolder(), "botInfo.yml");

        if (!botInfoFile.exists()) {
            try{
                botInfoFile.createNewFile();
            }catch (IOException e) {
                //oh no
            }
        }
        botInfoYml = YamlConfiguration.loadConfiguration(botInfoFile);
    }

    public static FileConfiguration get() {
        return botInfoYml;
    }

    public static void save() {
        try {
            botInfoYml.save(botInfoFile);
        }catch (IOException e) {
            System.out.println("Couldn't save botInfo yml file!");
        }
    }

    public static void reload() {
        botInfoYml = YamlConfiguration.loadConfiguration(botInfoFile);
    }
}
