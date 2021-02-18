package com.github.archessmn.ChatHook.bot;

import com.github.archessmn.ChatHook.bot.commands.*;
import com.github.archessmn.ChatHook.events.onChat;
import com.github.archessmn.ChatHook.main;
import com.github.archessmn.ChatHook.storage.botinfo;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;

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
            getLogger().warning("Disabling Discord Bot");
        }
    }

    public void onConnectToDiscord(DiscordApi api) {
        this.api = api;

        for (String Sserver: botinfo.get().getConfigurationSection("").getKeys(true)) {
            Optional<Server> Oserver = api.getServerById(Sserver);
            if (Oserver.isPresent()) {
                Server server = Oserver.get();

                Optional<TextChannel> Ochannel = api.getTextChannelById(botinfo.get().getString(Sserver + ".chatChannel"));
                if (Ochannel.isPresent()) {
                    TextChannel channel = Ochannel.get();
                    channel.sendMessage(":white_check_mark: **Server Has Somehow Started**");
                }
            }
        }

        new onChat(this.api);

        getLogger().info("Connected as " + api.getYourself().getDiscriminatedName());
        getLogger().info("Invite URL: " + api.createBotInvite());

        api.addListener(new registerBotChannel.registerBotChannelCommand());
        api.addListener(new ping.pingCommand());
        api.addListener(new registerGameChatChannel.registerGameChannelCommand());
        api.addListener(new link.linkCommand());
        api.addListener(new role.roleCommand());

    }

    public DiscordApi getApi() {
        return this.api;
    }
}
