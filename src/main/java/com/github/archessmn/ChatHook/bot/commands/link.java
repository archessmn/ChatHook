package com.github.archessmn.ChatHook.bot.commands;

import com.github.archessmn.ChatHook.storage.playerdata;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.*;

public class link {
    public static class linkCommand implements MessageCreateListener {
        @Override
        public void onMessageCreate(MessageCreateEvent event) {
            Message message = event.getMessage();
            TextChannel channel = event.getChannel();
            Optional<Server> Oserver = event.getServer();

            if (Oserver.isPresent()) {
                Server server = Oserver.get();
                String[] args = message.getContent().split(" ");
                List<String> Oargs = new ArrayList<String>(Arrays.asList(args));
                Oargs.remove(0);
                args = Oargs.toArray(new String[0]);

                if (args.length == 1) {
                    message.delete();

                    if (Objects.requireNonNull(playerdata.get().getConfigurationSection("toLink")).getKeys(true).contains(args[1])) {
                        String minecraftUUID = playerdata.get().getString("toLink." + args[1]);
                        String discordUUID = message.getAuthor().getIdAsString();
                        playerdata.get().set(minecraftUUID + ".discordId", discordUUID);
                        channel.sendMessage("Linked your discord account to your minecraft account, I hope!");
                    }

                } else {
                    message.delete();
                    channel.sendMessage("Incorrect number of arguments.");
                }

            }
        }
    }
}
