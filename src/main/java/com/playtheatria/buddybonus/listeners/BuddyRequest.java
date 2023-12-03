package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.events.BuddyRequestEvent;
import com.playtheatria.buddybonus.objects.Buddy;
import com.playtheatria.buddybonus.utils.BuddyUtils;
import org.bukkit.Bukkit;
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
        Bukkit.getConsoleSender().sendMessage("received buddy request event for: " + target.getName());
        Optional<Buddy> optionalBuddy = BuddyUtils.getOptionalBuddyFromBuddyList(plugin.getBuddyList(), target.getUniqueId());
        if (optionalBuddy.isPresent()) {
            initiate.sendMessage("That player is already a buddy! Ask them to remove their buddy with /buddy remove");
        } else {
            plugin.getBuddyList().add(new Buddy(event.getPlayer_initiate(), event.getPlayer_target()));
            Bukkit.getConsoleSender().sendMessage("Created a new buddy for " + initiate.getName() + " and " + target.getName());
        }
    }
}
