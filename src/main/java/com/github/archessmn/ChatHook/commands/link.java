package com.github.archessmn.ChatHook.commands;

import com.github.archessmn.ChatHook.storage.playerdata;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Random;

public class link implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args) {
        Player player = (Player) commandSender;
        if (playerdata.get().getString(String.valueOf(player.getUniqueId()) + ".discordId") == null) {
            if (!Objects.requireNonNull(playerdata.get().getConfigurationSection("toLink.")).getKeys(false).contains(String.valueOf(player.getUniqueId()))) {
                String linkID = generateLinkID();
                commandSender.sendMessage(linkID);
                playerdata.get().set("toLink." + linkID, String.valueOf(player.getUniqueId()));
                commandSender.sendMessage("§7[§2§lI§7]§7Generated a unique linking id: §6" + linkID + "§7. Run /link <id> in the discord chat channel to link your accounts.");
            }
        } else {
            commandSender.sendMessage("§7[§2§lX§7]You are already linked to an account.");
        }



        return true;
    }

    public static String generateLinkID() {
        String randomLinkID = null;

        Random r = new Random();

        String characters = "123xyz";
        for (int i = 0; i < 10; i++) {
            System.out.println(characters.charAt(r.nextInt(characters.length())));
            randomLinkID = randomLinkID + characters.charAt(r.nextInt(characters.length()));
        }
        return randomLinkID;
    }
}
