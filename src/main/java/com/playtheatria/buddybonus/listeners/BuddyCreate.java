package com.playtheatria.buddybonus.listeners;

import com.playtheatria.buddybonus.BuddyBonus;
import com.playtheatria.buddybonus.events.BuddyCreateEvent;
import com.playtheatria.buddybonus.objects.Buddy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
            player_one.sendMessage(ChatColor.GOLD + "You are now buddies with " + ChatColor.GREEN + player_two.getName() + ChatColor.GOLD + "!");
            player_two.sendMessage(ChatColor.GOLD + "You are now buddies with " + ChatColor.GREEN + player_one.getName() + ChatColor.GOLD + "!");
            plugin.getBuddyList().add(buddy);
        } else {
            plugin.debug("Something went wrong with buddy creation, let your loving owner know.");
        }
    }
}
