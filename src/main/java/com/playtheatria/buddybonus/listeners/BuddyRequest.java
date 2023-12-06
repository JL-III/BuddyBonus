package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.events.BuddyRequestEvent;
import com.playtheatria.buddybonus.objects.Buddy;
import com.playtheatria.buddybonus.objects.Request;
import com.playtheatria.buddybonus.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Optional;

public class BuddyRequest implements Listener {
    private BuddyBonus plugin;
    public BuddyRequest(BuddyBonus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBuddyRequest(BuddyRequestEvent event) {
        Player target = Bukkit.getPlayer(event.getPlayer_target());
        Player initiate = Bukkit.getPlayer(event.getPlayer_initiate());

        assert target != null;
        assert initiate != null;

        Optional<Buddy> optionalBuddy = Utils.getOptionalBuddyFromBuddyList(plugin.getBuddyList(), initiate.getUniqueId());
        Optional<Buddy> optionalBuddyTarget = Utils.getOptionalBuddyFromBuddyList(plugin.getBuddyList(), target.getUniqueId());
        Optional<Request> optionalRequest = Utils.getOptionalRequestFromRequestList(plugin.getRequestList(), initiate.getUniqueId());
        Optional<Request> optionalRequestTarget = Utils.getOptionalRequestFromRequestList(plugin.getRequestList(), target.getUniqueId());

        if (optionalBuddy.isPresent()) {
            initiate.sendMessage("You are already buddies with someone else, free room up for a new bud with /buddy remove");
        }
        if (optionalBuddyTarget.isPresent()) {
            initiate.sendMessage("That player already has a buddy! Ask them to upgrade their buddy with /buddy remove and then /buddy " + initiate.getName());
        }
        if (optionalRequest.isPresent()) {
            //TODO!!!
            // this needs to take in the config settings when we add it - also the math is wrong lol
            initiate.sendMessage("You already have already sent a buddy request! Wait " + (optionalRequest.get().time_stamp() - System.currentTimeMillis() * 20) + " seconds before requesting a new one!");
        }
        if (optionalRequestTarget.isPresent()) {
            initiate.sendMessage(target.getName() + " has a pending buddy request, please wait until their current request has expired.");
        }
        if (optionalBuddy.isEmpty() && optionalBuddyTarget.isEmpty() && optionalRequest.isEmpty() && optionalRequestTarget.isEmpty()) {
            plugin.getRequestList().add(new Request(event.getPlayer_initiate(), event.getPlayer_target(), System.currentTimeMillis()));
            initiate.sendMessage("You've invited " + target.getName() + " to be your buddy! Hope they accept...");
            target.sendMessage(
                    ChatColor.GREEN + initiate.getName() + ChatColor.GOLD + " has sent you a buddy request! Use "
                    + ChatColor.GREEN + "/buddy accept" + ChatColor.GOLD + " within " + ChatColor.YELLOW + "5"
                            + ChatColor.GOLD + " seconds to accept!");
            plugin.debug("created a new request for " + initiate.getName() + " and " + target.getName());
        }
    }
}
