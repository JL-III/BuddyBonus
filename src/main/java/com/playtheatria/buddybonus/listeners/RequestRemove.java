package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.events.RequestRemoveEvent;
import com.playtheatria.buddybonus.objects.Request;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class RequestRemove implements Listener {

    private final BuddyBonus plugin;

    public RequestRemove(BuddyBonus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRequestRemove(RequestRemoveEvent event) {
        plugin.getRequestList().remove(event.getRequest());
    }

}
