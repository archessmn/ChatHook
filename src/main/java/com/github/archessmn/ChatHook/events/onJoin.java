package com.github.archessmn.ChatHook.events;

import com.github.archessmn.ChatHook.main;
import com.github.archessmn.ChatHook.storage.playerdata;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onJoin implements Listener {

    main plugin;

    public onJoin (main instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent joinEvent) {
        Player player = joinEvent.getPlayer();
        String uuid = String.valueOf(player.getUniqueId());

        /* Player Data Defaults */
        playerdata.get().addDefault(uuid + ".name", player.getName());
        playerdata.get().addDefault(uuid + ".discordId", null);

    }
}
