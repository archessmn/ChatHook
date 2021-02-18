package com.github.archessmn.ChatHook;

import com.github.archessmn.ChatHook.bot.botmain;
import com.github.archessmn.ChatHook.commands.link;
import com.github.archessmn.ChatHook.events.onChat;
import com.github.archessmn.ChatHook.events.onJoin;
import com.github.archessmn.ChatHook.storage.botinfo;
import com.github.archessmn.ChatHook.storage.playerdata;
import com.github.archessmn.ChatHook.storage.roledata;
import net.byteflux.libby.BukkitLibraryManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.javacord.api.DiscordApi;

import java.util.Objects;

public class main extends JavaPlugin{

    private DiscordApi botapi;

    public static botmain bot;

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
        new botmain(this).setApi();
        /*Run on enable*/
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new onChat(this), this);
        pm.registerEvents(new onJoin(this), this);


        Objects.requireNonNull(this.getCommand("link")).setExecutor(new link());


        //Config
        this.reloadConfig();
        this.saveDefaultConfig();
        this.saveConfig();
        this.getConfig();

        /*Bot Config*/
        botinfo.setup();
        botinfo.get().options().copyDefaults(true);
        botinfo.save();

        /*Player Data*/
        playerdata.setup();
        playerdata.get().options().copyDefaults(true);
        playerdata.get().addDefault("toLink", null);
        playerdata.save();

        /*Role Data*/
        roledata.setup();
        roledata.get().options().copyDefaults(true);
        playerdata.save();


    }

    public static botmain api;

    public void onDisable() {
        /*Run on disable*/

        new botmain(this).disable();
    }
}
