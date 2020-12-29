package com.github.archessmn.ChatHook;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class main extends JavaPlugin{

    private DiscordApi api;

    @Override
    public void onEnable() {
        /*Run on enable*/

        //Config
        this.reloadConfig();
        this.saveDefaultConfig();
        this.saveConfig();
        this.getConfig();

        if (this.getConfig().getString("bot-token") != "tokenhere") {
            new DiscordApiBuilder()
                    .setToken(this.getConfig().getString("bot-token"))
                    .login()
                    .thenAccept(this::onConnectToDiscord)
                    .exceptionally(error -> {
                        getLogger().warning("Failed to connect to Discord! Disabling plugin!");
                        getPluginLoader().disablePlugin(this);
                        return null;
                    });
        }
    }

    public void onDisable() {
        /*Run on disable*/

        /*Disable Discord Bot*/
        if (api != null) {
            api.disconnect();
            api = null;
        }
    }

    private void onConnectToDiscord(DiscordApi api) {

        this.api = api;

        getLogger().info("Connected as " + api.getYourself().getDiscriminatedName());
        getLogger().info("Invite URL: " + api.createBotInvite());

    }
}
