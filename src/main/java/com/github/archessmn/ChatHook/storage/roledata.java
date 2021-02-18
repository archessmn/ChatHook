package com.github.archessmn.ChatHook.storage;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class roledata {

    private static File roleDataFile;
    private static FileConfiguration roleDataYml;

    public static void setup() {
        roleDataFile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("ChatHook")).getDataFolder(), "roleData.yml");

        if (!roleDataFile.exists()) {
            try{
                roleDataFile.createNewFile();
            }catch (IOException e) {
                //oh no
            }
        }
        roleDataYml = YamlConfiguration.loadConfiguration(roleDataFile);
    }

    public static FileConfiguration get() {
        return roleDataYml;
    }

    public static void save() {
        try {
            roleDataYml.save(roleDataFile);
        }catch (IOException e) {
            System.out.println("Couldn't save roleData yml file!");
        }
    }

    public static void reload() {
        roleDataYml = YamlConfiguration.loadConfiguration(roleDataFile);
    }
}
