package com.github.archessmn.ChatHook.commands;

import com.github.archessmn.ChatHook.storage.playerdata;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import javax.security.auth.login.Configuration;
import java.util.Objects;
import java.util.Random;

public class link implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args) {
        Player player = (Player) commandSender;
        if (playerdata.get().getString(String.valueOf(player.getUniqueId()) + ".discordId") == null) {
            for (String s : playerdata.get().getConfigurationSection("toLink").getKeys(false)) {
                if (Objects.equals(playerdata.get().getString("toLink." + s), String.valueOf(player.getUniqueId()))) {
                    player.sendMessage("§7[§2§lI§7]§7Your link ID is " + s);
                    playerdata.save();
                    return true;
                }
            }
            String linkID = generateLinkID();
            commandSender.sendMessage(linkID);
            playerdata.get().set("toLink." + linkID, String.valueOf(player.getUniqueId()));
            commandSender.sendMessage("§7[§2§lI§7]§7Generated a unique linking id: §6" + linkID + "§7. Run /link <id> in the discord chat channel to link your accounts.");
        } else {
            commandSender.sendMessage("§7[§2§lX§7]You are already linked to an account.");
        }


        playerdata.save();
        return true;
    }

    public static String generateLinkID() {
        String randomLinkID = null;

        Random r = new Random();

        String characters = "1234567890xyz";
        for (int i = 0; i < 15; i++) {
            System.out.println(characters.charAt(r.nextInt(characters.length())));
            randomLinkID = randomLinkID + characters.charAt(r.nextInt(characters.length()));
        }
        randomLinkID = randomLinkID.replaceAll("null", "");
        return randomLinkID;
    }
}
