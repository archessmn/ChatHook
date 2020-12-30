package com.github.archessmn.ChatHook.bot;

import com.github.archessmn.ChatHook.bot.commands.link;
import com.github.archessmn.ChatHook.bot.commands.ping;
import com.github.archessmn.ChatHook.bot.commands.registerGameChatChannel;
import com.github.archessmn.ChatHook.main;
import com.github.archessmn.ChatHook.bot.commands.registerBotChannel;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Objects;
import java.util.Optional;

import static org.bukkit.Bukkit.getLogger;

public class botmain {

    private DiscordApi api;

    public static main plugin;

    public botmain(main instance) {
        plugin = instance;
    }

    public void setApi() {
        if (!Objects.equals(plugin.getConfig().getString("bot-token"), "tokenhere")) {
            new DiscordApiBuilder()
                    .setToken(plugin.getConfig().getString("bot-token"))
                    .login()
                    .thenAccept(this::onConnectToDiscord)
                    .exceptionally(error -> {
                        getLogger().warning("Failed to connect to Discord! Disabling plugin!");
                        plugin.getPluginLoader().disablePlugin(plugin);
                        return null;
                    });
        }
    }

    public void disable() {
        if (this.api != null) {
            this.api.disconnect();
            this.api = null;
        }
    }

    public void disconnect() {
        api.disconnect();
        api = null;
    }

    public void onConnectToDiscord(DiscordApi api) {
        this.api = api;

        getLogger().info("Connected as " + api.getYourself().getDiscriminatedName());
        getLogger().info("Invite URL: " + api.createBotInvite());

        api.addListener(new registerBotChannel.registerBotChannelCommand());
        api.addListener(new ping.pingCommand());
        api.addListener(new registerGameChatChannel.registerGameChannelCommand());
        api.addListener(new link.linkCommand());

    }
}
