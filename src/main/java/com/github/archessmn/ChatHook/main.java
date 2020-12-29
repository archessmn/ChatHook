package com.github.archessmn.ChatHook;

import org.bukkit.plugin.java.JavaPlugin;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class main extends JavaPlugin{

    private DiscordApi api;

    @Override
    public void onEnable() {
        /*Run on enable*/
        new DiscordApiBuilder()
                .setToken("NzcxNDgxMjYyMjMzODc4NTY4.X5sv6g.Dhqsc1rLCz6v1DyhvqK2frey84k")
                .login()
                .thenAccept(this::onConnectToDiscord)
                .exceptionally(error -> {
                    getLogger().warning("Failed to connect to Discord! Disabling plugin!");
                    getPluginLoader().disablePlugin(this);
                    return null;
                });
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
