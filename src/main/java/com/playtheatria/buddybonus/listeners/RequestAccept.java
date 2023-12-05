package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.events.BuddyCreateEvent;
import com.playtheatria.buddybonus.events.RequestAcceptEvent;
import com.playtheatria.buddybonus.events.RequestRemoveEvent;
import com.playtheatria.buddybonus.objects.Request;
import com.playtheatria.buddybonus.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Optional;

public class RequestAccept implements Listener {

    private final BuddyBonus plugin;
    public RequestAccept(BuddyBonus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRequestAccept(RequestAcceptEvent event) {
        Bukkit.getConsoleSender().sendMessage("received a request accept event!");
        Optional<Request> optionalRequest = Utils.getOptionalRequestFromRequestList(plugin.getRequestList(), event.getPlayer_uuid());
        if (optionalRequest.isPresent()) {
            Bukkit.getPluginManager().callEvent(new BuddyCreateEvent(optionalRequest.get()));
            Bukkit.getConsoleSender().sendMessage("firing buddy create event");
            Bukkit.getPluginManager().callEvent(new RequestRemoveEvent(optionalRequest.get()));
            Bukkit.getConsoleSender().sendMessage("removing request since it was accepted");

        }
    }
}
