package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.events.BuddyRequestEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BuddyRequest implements Listener {

    @EventHandler
    public void onBuddyRequest(BuddyRequestEvent event) {
        Player target = Bukkit.getPlayer(event.getPlayer_target());

        assert target != null;
        Bukkit.getConsoleSender().sendMessage("received buddy request event for: " + target.getName());
    }
}
