package com.github.archessmn.ChatHook.bot;

import com.github.archessmn.ChatHook.main;
import com.github.archessmn.ChatHook.storage.botinfo;
import org.bukkit.Bukkit;
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

    main plugin;

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

        api.addListener(new botmain.registerBotChannel());
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

    public static class registerBotChannel implements MessageCreateListener {
        @Override
        public void onMessageCreate(MessageCreateEvent event) {
            Message message = event.getMessage();
            TextChannel channel = event.getChannel();
            Optional<Server> Oserver = event.getServer();

            if (message.getContent().equals("/registerbotchannel") && message.getAuthor().isServerAdmin()) {
                if (Oserver.isPresent()) {
                    Server server = Oserver.get();
                    botinfo.get().set(server.getIdAsString() + ".commandChannel", channel.getId());
                    message.delete();
                    channel.sendMessage("Set the channel for server `" + server.getName() + "` to " + channel + ".");
                } else {
                    message.delete();
                    channel.sendMessage("Cannot run this command in a DM channel.");
                }
            } else {
                message.delete();
                channel.sendMessage("Only the owner of this server can run this command.");
            }
        }
    }

    public static class pingCommand implements MessageCreateListener {
        @Override
        public void onMessageCreate(MessageCreateEvent event) {
            Message message = event.getMessage();
            TextChannel channel = event.getChannel();
            Optional<Server> Oserver = event.getServer();

            channel.sendMessage(message.getContent());
        }
    }

    public void onConnectToDiscord(DiscordApi api) {

        this.api = api;

        getLogger().info("Connected as " + api.getYourself().getDiscriminatedName());
        getLogger().info("Invite URL: " + api.createBotInvite());

    }
}
