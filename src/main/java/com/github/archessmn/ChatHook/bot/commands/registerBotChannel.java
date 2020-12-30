package com.github.archessmn.ChatHook.bot.commands;

import com.github.archessmn.ChatHook.storage.botinfo;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Optional;

public class registerBotChannel {
    public static class registerBotChannelCommand implements MessageCreateListener {
        @Override
        public void onMessageCreate(MessageCreateEvent event) {
            Message message = event.getMessage();
            TextChannel channel = event.getChannel();
            Optional<Server> Oserver = event.getServer();

            if (message.getContent().equals("/registerbotchannel")) {
                if (message.getAuthor().isServerAdmin()) {
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
                    channel.sendMessage("Only the owner of this server can run this command.");
                }
            }
        }
    }
}