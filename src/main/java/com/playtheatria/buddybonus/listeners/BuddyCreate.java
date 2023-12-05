package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.events.BuddyCreateEvent;
import com.playtheatria.buddybonus.objects.Buddy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BuddyCreate implements Listener {

    private BuddyBonus plugin;

    public BuddyCreate(BuddyBonus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBuddyCreateEvent(BuddyCreateEvent event) {
        Buddy buddy = new Buddy(event.getRequest().player_one_UUID(), event.getRequest().player_two_UUID());
        Player player_one = Bukkit.getPlayer(buddy.player_one_UUID());
        Player player_two = Bukkit.getPlayer(buddy.player_two_UUID());

        if (player_one != null && player_two != null) {
            player_one.sendMessage("You are now buddies with " + player_two.getName());
            player_two.sendMessage("You are now buddies with " + player_one.getName());
            plugin.getBuddyList().add(buddy);
        } else {
            Bukkit.getConsoleSender().sendMessage("Something went wrong with buddy creation, let your loving owner know.");
        }


    }
}
