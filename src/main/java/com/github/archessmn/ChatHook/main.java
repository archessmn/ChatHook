package com.github.archessmn.ChatHook;

import com.github.archessmn.ChatHook.bot.botmain;
import com.github.archessmn.ChatHook.storage.botinfo;
import net.byteflux.libby.BukkitLibraryManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin{

    botmain api;

    public main(botmain instance) {
        api = instance;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        BukkitLibraryManager manager = new BukkitLibraryManager(this);
        manager.addMavenCentral();
        manager.fromGeneratedResource(this.getResource("AzimDP.json")).forEach(library->{
            try {
                manager.loadLibrary(library);
            }catch(RuntimeException e) {
                getLogger().info("Skipping download of\""+library+"\", it either doesnt exist or has no .jar file");
            }
        });
        this.saveDefaultConfig();
        new botmain(this);
        /*Run on enable*/

        

        //Config
        this.reloadConfig();
        this.saveDefaultConfig();
        this.saveConfig();
        this.getConfig();

        /*Bot Config*/
        botinfo.setup();
        botinfo.get().options().copyDefaults(true);
        botinfo.save();


    }

    public void onDisable() {
        /*Run on disable*/

        /*Disable Discord Bot*/
        if (api != null) {
            api.disconnect();
        }
    }
}
