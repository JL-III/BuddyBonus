package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.enums.DebugType;
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
            // player logs out - we load them into the audit list
            if (!plugin.getBuddyAuditList().containsKey(optionalBuddy.get())) {
                plugin.getBuddyAuditList().put(optionalBuddy.get(), System.currentTimeMillis());
                plugin.debug("BuddyBonus: sent buddy to audit list for " + event.getPlayer().getName() + " for logging out.", DebugType.ACTION);
            } else {
                plugin.debug("BuddyBonus: buddy for " + event.getPlayer().getName() + " is already in audit list, continuing to use old entry.", DebugType.INFO);
            }

        }

        if (optionalRequest.isPresent()) {
            Bukkit.getPluginManager().callEvent(new RequestRemoveEvent(optionalRequest.get()));
            plugin.debug("BuddyBonus: removed request containing " + event.getPlayer().getName() + " for logging out.", DebugType.ACTION);
        }
    }

}
