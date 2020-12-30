package com.github.archessmn.ChatHook.storage;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class playerdata {

    private static File playerDataFile;
    private static FileConfiguration playerDataYml;

    public static void setup() {
        playerDataFile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("ChatHook")).getDataFolder(), "playerData.yml");

        if (!playerDataFile.exists()) {
            try{
                playerDataFile.createNewFile();
            }catch (IOException e) {
                //oh no
            }
        }
        playerDataYml = YamlConfiguration.loadConfiguration(playerDataFile);
    }

    public static FileConfiguration get() {
        return playerDataYml;
    }

    public static void save() {
        try {
            playerDataYml.save(playerDataFile);
        }catch (IOException e) {
            System.out.println("Couldn't save playerData yml file!");
        }
    }

    public static void reload() {
        playerDataYml = YamlConfiguration.loadConfiguration(playerDataFile);
    }
}
