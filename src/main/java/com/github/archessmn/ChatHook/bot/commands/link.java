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
                if (args[0].equals("/link")) {
                    List<String> Oargs = new ArrayList<String>(Arrays.asList(args));
                    Oargs.remove(0);
                    args = Oargs.toArray(new String[0]);

                    if (args.length == 1) {
                        message.delete();

                        for (String s : playerdata.get().getConfigurationSection("toLink").getKeys(false)) {
                            if (s.equals(args[0])) {
                                String minecraftUUID = playerdata.get().getString("toLink." + args[0]);
                                String discordUUID = message.getAuthor().getIdAsString();
                                playerdata.get().set(minecraftUUID + ".discordId", discordUUID);
                                playerdata.get().set("toLink." + args[0], null);
                                channel.sendMessage("Linked your discord account to your minecraft account, I hope!");
                                playerdata.save();
                                return;
                            }
                        }
                        channel.sendMessage("Could not find a link ID matching your input, please make sure you copy the ID from the server.");

                    } else {
                        message.delete();
                        channel.sendMessage("Incorrect number of arguments.");
                    }
                }
            }
        }
    }
}
