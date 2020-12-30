package com.github.archessmn.ChatHook.bot.commands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Optional;

public class ping {
    public static class pingCommand implements MessageCreateListener {
        @Override
        public void onMessageCreate(MessageCreateEvent event) {
            Message message = event.getMessage();
            TextChannel channel = event.getChannel();
            Optional<Server> Oserver = event.getServer();

            if (message.getContent() == "/ping") {
                channel.sendMessage("Pong!");
            }
        }
    }
}
