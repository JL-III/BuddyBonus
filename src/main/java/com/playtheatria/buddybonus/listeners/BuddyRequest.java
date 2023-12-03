package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.events.BuddyRequestEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BuddyRequest implements Listener {

    @EventHandler
    public void onBuddyRequest(BuddyRequestEvent event) {
        Bukkit.getConsoleSender().sendMessage("received buddy request event");
    }
}
