package com.github.archessmn.ChatHook.bot.commands;

import com.github.archessmn.ChatHook.storage.roledata;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class role {
    public static class roleCommand implements MessageCreateListener {
        @Override
        public void onMessageCreate(MessageCreateEvent event) {
            Message message = event.getMessage();
            TextChannel channel = event.getChannel();
            Optional<Server> Oserver = event.getServer();

            if (Oserver.isPresent()) {
                Server server = Oserver.get();
                String[] args = message.getContent().split(" ");
                if (args[0].equals("/role")) {
                    List<String> Oargs = new ArrayList<String>(Arrays.asList(args));
                    Oargs.remove(0);
                    args = Oargs.toArray(new String[0]);

                    if (args.length == 4) {
                        List<Role> rolesMentioned = message.getMentionedRoles();
                        if (rolesMentioned.size() == 1) {
                            Role roleMentioned = rolesMentioned.get(0);

                            roledata.get().set(roleMentioned.getIdAsString() + ".name", args[1]);
                            roledata.get().set(roleMentioned.getIdAsString() + ".nameColour", args[2]);
                            roledata.get().set(roleMentioned.getIdAsString() + ".prefix", args[3]);

                            roledata.save();

                            channel.sendMessage("New role set, name: " + roledata.get().getString(roleMentioned.getIdAsString() + ".name") +
                                    ", colour: " + roledata.get().getString(roleMentioned.getIdAsString() + ".nameColour") +
                                    ", prefix: " + roledata.get().getString(roleMentioned.getIdAsString() + ".prefix"));
                            Optional<User> Ouser = message.getUserAuthor();

                            roledata.save();

                            if (Ouser.isPresent()) {
                                User user = Ouser.get();
                                List<Role> userRoles = user.getRoles(server);
                                for (Role sortingRole : userRoles) {
                                    if (userRoles.contains(roleMentioned)) {
                                        channel.sendMessage("You have this role!");
                                        return;
                                    }
                                }
                            }





                        } else {
                            message.delete();
                            channel.sendMessage("Too many mentioned roles in your message.");
                        }
                    } else {
                        channel.sendMessage("Incorrect number of arguments.");
                    }
                }
            }
        }
    }
}
