package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.events.BuddyRemoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BuddyRemove implements Listener {

    private BuddyBonus plugin;

    public BuddyRemove(BuddyBonus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBuddyRemove(BuddyRemoveEvent event) {
        plugin.debug("received remove buddy event!");
        plugin.getBuddyList().remove(event.getBuddy());
    }
}
