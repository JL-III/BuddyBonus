package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.enums.DebugType;
import com.playtheatria.buddybonus.events.BuddyCreateEvent;
import com.playtheatria.buddybonus.events.RequestAcceptEvent;
import com.playtheatria.buddybonus.events.RequestRemoveEvent;
import com.playtheatria.buddybonus.objects.Request;
import com.playtheatria.buddybonus.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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
        Optional<Request> optionalRequest = Utils.getOptionalRequestFromRequestList(plugin.getRequestList(), event.getPlayer_uuid());
        if (optionalRequest.isPresent()) {
            Bukkit.getPluginManager().callEvent(new BuddyCreateEvent(optionalRequest.get()));
            plugin.debug("firing buddy create event", DebugType.ACTION);
            Bukkit.getPluginManager().callEvent(new RequestRemoveEvent(optionalRequest.get()));
            plugin.debug("removing request since it was accepted", DebugType.ACTION);
        } else {
            Player player = Bukkit.getPlayer(event.getPlayer_uuid());
            if (player != null) {
                player.sendMessage(ChatColor.GOLD + "There are no pending buddy requests! Tell your buddy to do "
                        + ChatColor.GREEN + " /buddy " + player.getName());
            }
        }
    }
}
