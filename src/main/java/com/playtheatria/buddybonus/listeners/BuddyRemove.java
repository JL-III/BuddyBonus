package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.events.BuddyRemoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BuddyRemove implements Listener {

    @EventHandler
    public void onBuddyRemove(BuddyRemoveEvent event) {
        Bukkit.getConsoleSender().sendMessage("received remove buddy event!");
    }
}
