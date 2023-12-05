package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.events.BuddyRemoveEvent;
import com.playtheatria.buddybonus.events.RequestRemoveEvent;
import com.playtheatria.buddybonus.objects.Buddy;
import com.playtheatria.buddybonus.objects.Request;
import com.playtheatria.buddybonus.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;

public class PlayerQuit implements Listener {

    private BuddyBonus plugin;

    public PlayerQuit(BuddyBonus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Optional<Buddy> optionalBuddy = Utils.getOptionalBuddyFromBuddyList(plugin.getBuddyList(), event.getPlayer().getUniqueId());
        Optional<Request> optionalRequest = Utils.getOptionalRequestFromRequestList(plugin.getRequestList(), event.getPlayer().getUniqueId());

        if (optionalBuddy.isPresent()) {
            BuddyRemoveEvent removeEvent = new BuddyRemoveEvent(optionalBuddy.get());
            Bukkit.getPluginManager().callEvent(removeEvent);
            Bukkit.getConsoleSender().sendMessage("BuddyBonus: removed buddy containing " + event.getPlayer().getName() +  " for logging out.");
        }

        if (optionalRequest.isPresent()) {
            RequestRemoveEvent removeEvent = new RequestRemoveEvent(optionalRequest.get());
            Bukkit.getPluginManager().callEvent(removeEvent);
            Bukkit.getConsoleSender().sendMessage("BuddyBonus: removed request containing " + event.getPlayer().getName() +  " for logging out.");
        }
    }

}
