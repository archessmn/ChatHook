package com.github.archessmn.ChatHook.events;

import com.github.archessmn.ChatHook.bot.botmain;
import com.github.archessmn.ChatHook.main;
import com.github.archessmn.ChatHook.storage.botinfo;
import com.github.archessmn.ChatHook.storage.playerdata;
import com.github.archessmn.ChatHook.storage.roledata;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class onChat implements Listener {

    DiscordApi api;
    main plugin;
    private main main;

    public onChat (main instance) {
        plugin = instance;
    }

    public onChat(DiscordApi instance) {
        api = instance;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent chatEvent) {
        
        botmain botClass = new botmain(main);

        DiscordApi api = botClass.getApi();


        Player player = chatEvent.getPlayer();
        String uuid = String.valueOf(player.getUniqueId());

        if (playerdata.get().getString(uuid + ".discordId") != null) {
            String userId = playerdata.get().getString(uuid + ".discordId");
            player.sendMessage(userId);
            if (api != null) {
                Bukkit.getLogger().info(String.valueOf(api));
            } else {
                Bukkit.getLogger().warning("api is null");
            }

            Optional<User> Ouser = api.getCachedUserById(Objects.requireNonNull(userId.replaceAll("'", "")));

            if (Ouser.isPresent()) {
                User user = Ouser.get();
                Optional<Server> Oserver = api.getServerById(plugin.getConfig().getString("serverForChat"));
                if (Oserver.isPresent()) {
                    Server server = Oserver.get();
                    Optional<TextChannel> Ochannel = api.getTextChannelById(botinfo.get().getString(server.getIdAsString() + ".chatChannel"));
                    if (Ochannel.isPresent()) {
                        TextChannel channel = Ochannel.get();
                        List<Role> userRoles = user.getRoles(server);
                        for (Role searchRole : userRoles) {
                            String roleId = searchRole.getIdAsString();
                            if (roledata.get().contains(roleId)) {
                                String rolePrefixMcColoured = roledata.get().getString(roleId + ".prefix");
                                String roleColour = roledata.get().getString(roleId + ".nameColour");

                                assert rolePrefixMcColoured != null;
                                String rolePrefix = rolePrefixMcColoured.replaceAll("§[0-9a-z]", "");


                                channel.sendMessage(rolePrefix + " " + player.getName() + ": " + chatEvent.getMessage());

                                chatEvent.setFormat(rolePrefixMcColoured + roleColour + player.getName() + "§r§7: " + chatEvent.getMessage());
                            }
                        }
                    }
                }
            }
        }
    }
}
